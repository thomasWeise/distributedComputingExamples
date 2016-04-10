#include <mpi.h>
#include <stdio.h>

int main(int argc, char *argv[]) {
  int           rank, size, prev, next;
  char          buffer[30], buffer2[30];
  MPI_Request   request, request2;
  MPI_Status    status;

  MPI_Init(&argc,&argv);
  MPI_Comm_size(MPI_COMM_WORLD, &size);
  MPI_Comm_rank(MPI_COMM_WORLD, &rank);

  next = ((rank + 1) % size);
  prev = ((rank + size - 1) % size);

  MPI_Irecv(buffer, 30, MPI_CHAR, prev, 42, MPI_COMM_WORLD, &request);

  sprintf(buffer2, "Non-blocking from %d!", rank);
  MPI_Isend(buffer2, 30, MPI_CHAR, next, 42, MPI_COMM_WORLD, &request2);

  MPI_Wait(&request, &status);
  printf("%d received \"%s\"\n", rank, buffer);

  MPI_Wait(&request2, &status);

  MPI_Finalize();
  return 0;
}