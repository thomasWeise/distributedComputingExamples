#include <mpi.h>   // import MPI header
#include <stdio.h> // import for printf

int main(int argc, char **argv) {
  int size, rank;

  MPI_Init(&argc, &argv); // initialize MPI

  MPI_Comm_size(MPI_COMM_WORLD, &size); // get number of program instances
  MPI_Comm_rank(MPI_COMM_WORLD, &rank); // get own ID/address

  // often, an MPI application has a master and some slaves
  // master distributes tasks and combine partial results to final results
  // slaves receive partial task, compute partial result, and send to master
  if(rank == 0) { // the instance with rank=0 is often chosen as master
    printf("Hi from Master\n");
  } else {        // the others are often slaves 
    printf("Just Slave %d out of %d\n", rank, size);
  }

  MPI_Finalize(); // finalize = shut down MPI
  return 0;
}