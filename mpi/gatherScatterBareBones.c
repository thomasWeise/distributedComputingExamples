#include <mpi.h>    // import MPI header
#include <stdio.h>  // needed for printf

#define DATA_SIZE 1024 // the data size

int main(int argc, char *argv[]) {
  int send[DATA_SIZE], recv[DATA_SIZE];
  int rank, size, count, root, res;
  MPI_Status status;

  MPI_Init(&argc, &argv); // initialize MPI
  MPI_Comm_rank(MPI_COMM_WORLD, &rank); // get own rank/ID
  MPI_Comm_size(MPI_COMM_WORLD, &size); // get total number of processes

  if(rank == 0) { //If root: Generate data to be distributed.
  }

  //Send data to all nodes. here: an integer array of length "count".
  count = (DATA_SIZE / size); // each receive gets chunk of same size
  // scatter: if rank=0, send data (and get own share); otherwise: receive data
  MPI_Scatter(send, count, MPI_INT, recv, count, MPI_INT, 0, MPI_COMM_WORLD);

  // Each node processes its share of data and sends the result (here: int "res") to root.
  MPI_Gather(&res, 1, MPI_INT, recv, 1, MPI_INT, 0, MPI_COMM_WORLD);

  if(rank == 0) { //If root: process the received data.
  }

  MPI_Finalize(); // shut down MPI
  return 0;
}