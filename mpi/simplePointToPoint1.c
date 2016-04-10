#include <mpi.h>
#include <stdio.h>

int main(int argc, char **argv) {
  int size, rank, s_msg, r_msg, next, prev;
  MPI_Status status;

  MPI_Init(&argc, &argv);
  MPI_Comm_size(MPI_COMM_WORLD, &size);
  MPI_Comm_rank(MPI_COMM_WORLD, &rank);

  next  = ((rank + 1) % size);
  prev  = ((rank + size - 1) % size);
  s_msg = ((size * rank) + next);

  if((rank % 2) == 0) {
    MPI_Send(&s_msg, 1, MPI_INT, next, 42, MPI_COMM_WORLD);
    MPI_Recv(&r_msg, 1, MPI_INT, prev, 42, MPI_COMM_WORLD, &status);
  } else {
    MPI_Recv(&r_msg, 1, MPI_INT, prev, 42, MPI_COMM_WORLD, &status);
    MPI_Send(&s_msg, 1, MPI_INT, next, 42, MPI_COMM_WORLD);
  }

  printf("id: %d, next: %d, prev: %d, send: %d, recv: %d\n", rank, next, prev, s_msg, r_msg);

  MPI_Finalize();
  return 0;
}