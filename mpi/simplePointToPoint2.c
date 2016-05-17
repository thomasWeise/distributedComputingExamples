#include <mpi.h>    // import MPI header
#include <stdio.h>  // needed for printf
#include <string.h> // needed for strlen

int main(int argc, char *argv[]) {
  char message[20];  // char array big enough to hold message
  int rank;          // own rank
  MPI_Status status; // status variable

  MPI_Init(&argc, &argv); // initialize mpi
  MPI_Comm_rank(MPI_COMM_WORLD, &rank); // get own rank

  if (rank == 0) { // if we have rank 0...
    strcpy(message, "Hello, there"); /// ...create and send message to rank 1
    MPI_Send(message, strlen(message)+1, MPI_CHAR, 1, 42, MPI_COMM_WORLD);
    printf("sent: \"%s\"\n", message); // print the message that was sent
  } else { // if we are rank 1, receive message coming from rank 0
    MPI_Recv(message, 20, MPI_CHAR, 0, 42, MPI_COMM_WORLD, &status);
    printf("received: \"%s\"\n", message); // print message
  }

  MPI_Finalize(); // shut down MPI
  return 0;
}