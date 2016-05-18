#include <mpi.h>    // import MPI header
#include <stdio.h>  // needed for printf
#include <stdlib.h> // for rand and RAND_MAX
#include <string.h> // for memset
#include <time.h>   // for srand(time(NULL));

int main(int argc, char **argv) {
  int           i, size, rank;     double     x, y;
  long long int *data, worker[2];  MPI_Status status;

  MPI_Init(&argc, &argv);                           //initialize MPI
  MPI_Comm_size(MPI_COMM_WORLD, &size);             //get the number of processes in the global communicator
  MPI_Comm_rank(MPI_COMM_WORLD, &rank);             //get the rank of this process within the global communicator

  if(rank != 0) {
    worker[0] = worker[1] = 0LL;                    //the local worker array
    srand(time(NULL));
    for(worker[0] = 1; worker[0] < (rank * 100000000LL); worker[0]++) { //make 100 000 000 samples
      x = (rand() / ((double)RAND_MAX));            //random x-coordinate in [0,1]
      y = (rand() / ((double)RAND_MAX));            //random y-coordinate in [0,1]
      if( ((x*x) + (y*y)) <= 1.0 ) {                //did it fall into the inner circle?
        worker[1]++;                                //yes, it did - increase counter
      }
    }
  }

  data = (long long int*)malloc(sizeof(long long int) * size * 2); //allocate data (ok, waste some memory in the workers)
  memset(data, 0, (sizeof(data[0]) * 2 * size));    //clear the data buffer
  MPI_Gather(worker, 2, MPI_LONG_LONG_INT, data, 2, MPI_LONG_LONG_INT, 0, MPI_COMM_WORLD);  //gather results

  if(rank == 0) {                                   //root now evaluates results
    for(i = size; (--i) > 0; ) {                    //receive data from the workers
      data[0] += data[2*i];                         //get the received sample size (number of points)
      data[1] += data[2*i+1];                       //get the number of samples (points) inside the unit circle
      printf("worker %d sends estimate %G (based on %lld samples), total estimate now is %G (based on %lld samples).\n", i,
            ((4.0 * data[2*i + 1]) / data[2*i]), data[2*i], ((4.0 * data[1]) / data[0]), data[0]);
      }
  }
  MPI_Finalize();                                   //finish the MPI stuff
  return 0;
}

