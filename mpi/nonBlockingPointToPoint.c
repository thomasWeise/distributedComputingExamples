#include <mpi.h>    // import MPI header
#include <stdio.h>  // needed for printf

int main(int argc, char *argv[]) {
  int           rank, size, prev, next;
  char          receiveBuffer[30], sendBuffer[30];
  MPI_Request   receiveRequest, sendRequest;
  MPI_Status    status;

  MPI_Init(&argc,&argv); // initialize MPI
  MPI_Comm_size(MPI_COMM_WORLD, &size); // get own rank / ID
  MPI_Comm_rank(MPI_COMM_WORLD, &rank); // get total number of processes

  next = ((rank + 1) % size); // get rank of process to receive from
  // _initiate_ receive operation, but do not wait for its completion
  MPI_Irecv(receiveBuffer, 30, MPI_CHAR, prev, 42, MPI_COMM_WORLD, &receiveRequest);

  prev = ((rank + size - 1) % size); // get rank of process to send to
  sprintf(sendBuffer, "Non-blocking from %d!", rank);
  // _initiate_ send operation, but do not wait for its completion
  MPI_Isend(sendBuffer, 30, MPI_CHAR, next, 42, MPI_COMM_WORLD, &sendRequest);

  MPI_Wait(&receiveRequest, &status); // wait for receive to complete
  printf("%d received \"%s\"\n", rank, receiveBuffer); // print received msg

  MPI_Wait(&sendRequest, &status); // wait for send to complete

  MPI_Finalize(); // shut down MPI
  return 0;
}