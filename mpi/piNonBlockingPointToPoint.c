#include <mpi.h>
#include <stdlib.h>
#include <string.h>
#include <time.h>
#include <stdio.h>

int main(int argc, char **argv) {
  int           i, j, size, rank;  double     x, y;
  long long int *data;             MPI_Status status;
  MPI_Request   *req;

  MPI_Init(&argc, &argv);                           //initialize MPI
  MPI_Comm_size(MPI_COMM_WORLD, &size);             //get the number of processes in the global communicator
  MPI_Comm_rank(MPI_COMM_WORLD, &rank);             //get the rank of this process within the global communicator

  data = (long long int*)malloc(sizeof(long long int) * size * 2);//allocate data (ok, waste some memory in the workers)
  memset(data, 0, (sizeof(sizeof(long long int)) * 2 * size));    //clear the data buffer

  if(rank == 0) {                                   //check if we are root
    req = (MPI_Request*)malloc(sizeof(MPI_Request) * size); //allocate request list
    for(i = size; (--i) > 0; ) {                    //initiate receives from the workers
      MPI_Irecv(&data[2*i], 2, MPI_LONG_LONG_INT, i, 42, MPI_COMM_WORLD, &req[i]);
    }

    for(i = size-2; i >= 0; i--) {                  //for each unfulfilled receive request
      MPI_Waitany(size-1, &req[1], &j, &status);    //now wait until something has been received from any worker
      j++;
      data[0] += data[2*j];                         //get the received sample size (number of points)
      data[1] += data[2*j + 1];                     //get the number of samples (points) inside the unit circle
      printf("worker %d sends estimate %G (based on %lld samples), total estimate now is %G (based on %lld samples).\n", j,
                   ((4.0 * data[2*j + 1]) / data[2*j]), data[2*j], ((4.0 * data[1]) / data[0]), data[0]);
      fflush(stdout);                               //flush the standard out
    }
  } else {                                          //ok, we are a worker
    srand(time(NULL));
    for(data[0] = 1; data[0] < (rank * 100000000LL); data[0]++) { //make 100 000 000 samples
      x = (rand() / ((double)RAND_MAX));            //random x-coordinate in [0,1]
      y = (rand() / ((double)RAND_MAX));            //random y-coordinate in [0,1]
      if( ((x*x) + (y*y)) <= 1.0 ) {                //did it fall into the inner circle?
        data[1]++;                                  //yes, it did - increase counter
      }
    }
    MPI_Send(&data[0], 2, MPI_LONG_LONG_INT, 0, 42, MPI_COMM_WORLD); //send worker result synchronously
  }

  MPI_Finalize();                                   //finish the MPI stuff
  return 0;
}

