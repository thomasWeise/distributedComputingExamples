#include <mpi.h>
#include <stdio.h>
#include <string.h>

int main(int argc, char *argv[]) {
  char message[20];
  int rank;
  MPI_Status status;

  MPI_Init(&argc, &argv);
  MPI_Comm_rank(MPI_COMM_WORLD, &rank);

  if (rank == 0) {
    strcpy(message, "Hello, there");
    MPI_Send(message, strlen(message)+1, MPI_CHAR, 1, 42, MPI_COMM_WORLD);
    printf("sent: \"%s\"\n", message);
  } else {
    MPI_Recv(message, 20, MPI_CHAR, 0, 42, MPI_COMM_WORLD, &status);
    printf("received: \"%s\"\n", message);
  }

  MPI_Finalize();
  return 0;
}