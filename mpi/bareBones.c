#include <mpi.h> // import MPI header

int main(int argc, char **argv) {
  MPI_Init(&argc, &argv); // initialize MPI
  MPI_Finalize();         // shut down MPI
  return 0;
}