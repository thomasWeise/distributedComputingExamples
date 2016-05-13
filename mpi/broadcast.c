#include <mpi.h>
#include <stdio.h>

int main(int argc, char *argv[]) {
  char message[60];
  int rank, size;
  MPI_Status status;

  MPI_Init(&argc, &argv);
  MPI_Comm_rank(MPI_COMM_WORLD, &rank);
  MPI_Comm_size(MPI_COMM_WORLD, &size);

  if (rank == 0) {
    sprintf(message, "Message from root (rank %d).", rank);
  }

  MPI_Bcast(message, 60, MPI_CHAR, 0, MPI_COMM_WORLD);
  printf("The message sent/received at node %d is \"%s\"\n", rank, message);

  MPI_Finalize();
  return 0;
}
