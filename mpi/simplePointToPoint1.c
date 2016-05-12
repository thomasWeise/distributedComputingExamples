#include <mpi.h>   // import MPI header
#include <stdio.h> // needed for printf

int main(int argc, char **argv) {
  int size, rank, s_msg, r_msg, next, prev;
  MPI_Status status;

  MPI_Init(&argc, &argv); // initialize MPI
  MPI_Comm_size(MPI_COMM_WORLD, &size); // get number of program instances
  MPI_Comm_rank(MPI_COMM_WORLD, &rank); // get own ID/address

  next  = ((rank + 1) % size);  // next higher id, wrap from size-1 to 0
  prev  = ((rank + size - 1) % size); // next lower id, wrap from 0 to size-1
  s_msg = ((size * rank) + next); // the example message, just some number

  if((rank % 2) == 0) { // even rank: message to next, receive from prev
    MPI_Send(&s_msg, 1, MPI_INT, next, 42, MPI_COMM_WORLD);
    MPI_Recv(&r_msg, 1, MPI_INT, prev, 42, MPI_COMM_WORLD, &status);
  } else { // otherwise: receive from rev, send to next
    MPI_Recv(&r_msg, 1, MPI_INT, prev, 42, MPI_COMM_WORLD, &status);
    MPI_Send(&s_msg, 1, MPI_INT, next, 42, MPI_COMM_WORLD);
  }

  printf("id: %d, next: %d, prev: %d, send: %d, recv: %d\n", rank, next, prev, s_msg, r_msg);

  MPI_Finalize(); // shut down MPI
  return 0;
}