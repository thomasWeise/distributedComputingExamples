#include <mpi.h>
#include <stdio.h>
#include <math.h>

#define DATA_SIZE 1024

int main(int argc, char *argv[]) {
  int send[DATA_SIZE], recv[DATA_SIZE];
  int rank, size, count, root, res;
  MPI_Status status;

  MPI_Init(&argc, &argv);
  MPI_Comm_rank(MPI_COMM_WORLD, &rank);
  MPI_Comm_size(MPI_COMM_WORLD, &size);

  if(rank == 0) { //If root: Generate data to be distributed.
  }

  //Send data to all nodes. here: an integer array of length "count".
  count = (DATA_SIZE / size);
  MPI_Scatter(send, count, MPI_INT, recv, count, MPI_INT, 0, MPI_COMM_WORLD);

  // Each node now processes its share of data and sends the results (here: int "res") to root.
  MPI_Gather(&res, 1, MPI_INT, recv, 1, MPI_INT, 0, MPI_COMM_WORLD);

  if(rank == 0) { //If root: process the received data.
  }

  MPI_Finalize();
  return 0;
}