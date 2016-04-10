#include <mpi.h>
#include <stdlib.h>
#include <stdio.h>
#include <time.h>

int main(int argc, char **argv) {
  int           i, size, rank;            double     x, y;
  long long int root[2], worker[2];       MPI_Status status;

  MPI_Init(&argc, &argv);                           //initialize MPI
  MPI_Comm_size(MPI_COMM_WORLD, &size);             //get the number of processes in the global communicator
  MPI_Comm_rank(MPI_COMM_WORLD, &rank);             //get the rank of this process within the global communicator
  root[0] = root[1] = worker[0] = worker[1] = 0LL;  //clear the data buffer

  if(rank == 0) {                                   //check if we are root
    for(i = size; (--i) > 0; ) {                    //receive data from the workers
      MPI_Recv(&worker[0], 2, MPI_LONG_LONG_INT, i, 42, MPI_COMM_WORLD, &status); //do receive
      root[0] += worker[0];                         //get the received sample size (number of points)
      root[1] += worker[1];                         //get the number of samples (points) inside the unit circle
      printf("worker %d sends estimate %G (based on %lld samples), total estimate now is %G (based on %lld samples).\n", i,
                   ((4.0 * worker[1]) / worker[0]), worker[0], ((4.0 * root[1]) / root[0]), root[0]);
      fflush(stdout);                               //flush the standard out
    }
  } else {                                          //ok, we are a worker
    srand(time(NULL));
    for(worker[0] = 1; worker[0] < (rank * 100000000LL); worker[0]++) { //make 100 000 000 samples
      x = (rand() / ((double)RAND_MAX));            //random x-coordinate in [0,1]
      y = (rand() / ((double)RAND_MAX));            //random y-coordinate in [0,1]
      if( ((x*x) + (y*y)) <= 1.0 ) {                //did it fall into the inner circle?
        worker[1]++;                                //yes, it did - increase counter
      }
    }
    MPI_Send(&worker[0], 2, MPI_LONG_LONG_INT, 0, 42, MPI_COMM_WORLD); //send worker result synchronously
  }

  MPI_Finalize();                                   //finish the MPI stuff
  return 0;
}

