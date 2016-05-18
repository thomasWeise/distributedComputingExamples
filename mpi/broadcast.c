#include <mpi.h>   // import MPI header
#include <stdio.h> // import for printf

int main(int argc, char *argv[]) {
  char message[60]; // space allocated for the message
  int rank;         // variable for process id

  MPI_Init(&argc, &argv); // initialize MPI
  MPI_Comm_rank(MPI_COMM_WORLD, &rank); // get own rank

  if (rank == 0) { // create message if process is "root" (rank = 0)
    sprintf(message, "Message from root (rank %d).", rank);
  }

  // broadcast: send message to all if rank==0, otherwise receive
  MPI_Bcast(message, 60, MPI_CHAR, 0, MPI_COMM_WORLD);
  printf("The message sent/received at node %d is \"%s\"\n", rank, message);

  MPI_Finalize(); // shutdown MPI
  return 0;
}
