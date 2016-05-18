#include <mpi.h>    // import MPI header
#include <stdio.h>  // needed for printf
#include <string.h> // needed for strlen

int main(int argc, char **argv) {
  int         rank, size, prev, next;
  MPI_Status  status;
  char        messageIn[20], messageOut[20];

  MPI_Init(&argc, &argv); // initialize MPI
  MPI_Comm_rank(MPI_COMM_WORLD, &rank); // get own rank/ID
  MPI_Comm_size(MPI_COMM_WORLD, &size); // get total number of processes

  prev = ((size + rank - 1) % size); // get rank of process to receive from, wrap at 0
  MPI_Recv(messageIn, 20, MPI_CHAR, prev, 0, MPI_COMM_WORLD, &status); // receive msg
  printf("Process %d received message %s from process %d.\n", rank, messageIn, prev);

  next = ((rank + 1) % size); // get rank of process to send message to
  strcpy(messageOut, "Important message!"); // construct message
  printf("Process %d is sending message %s to process %d.\n", rank, messageOut, next);
  MPI_Send(messageOut, 20, MPI_CHAR, next, 0, MPI_COMM_WORLD); // send message

  MPI_Finalize(); // shut down MPI
  return 0;
}