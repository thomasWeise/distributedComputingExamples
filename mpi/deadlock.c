#include <mpi.h>    // import MPI header
#include <stdio.h>  // needed for printf
#include <string.h> // needed for strlen

int main(int argc, char **argv) {
  int         rank, size, prev, next;
  MPI_Status  status;
  char        message[20];

  MPI_Init(&argc, &argv);
  MPI_Comm_rank(MPI_COMM_WORLD, &rank);
  MPI_Comm_size(MPI_COMM_WORLD, &size);

  prev = ((size + rank - 1) % size);
  next = ((rank + 1) % size);
  strcpy(message, "Important message!");

  MPI_Recv(message, 20, MPI_CHAR, prev, 0, MPI_COMM_WORLD, &status);
  printf("Process %d received message %s from process %d.\n", rank, message, prev);
  printf("Process %d is sending message %s to process %d.\n", rank, message, next);
  MPI_Send(message, 20, MPI_CHAR, next, 0, MPI_COMM_WORLD);

  MPI_Finalize();
  return 0;
}