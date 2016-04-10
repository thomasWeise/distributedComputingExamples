#include <mpi.h>
#include <stdio.h>
#include <math.h>

#define DATA_SIZE 1024

int main(int argc, char *argv[]) {
  int send[DATA_SIZE], recv[DATA_SIZE];
  int rank, size, count, root, res, i, j;
  MPI_Status status;

  MPI_Init(&argc, &argv);
  MPI_Comm_rank(MPI_COMM_WORLD, &rank);
  MPI_Comm_size(MPI_COMM_WORLD, &size);

  if(rank == 0) { //generate data if root
    for(i = DATA_SIZE; (--i)>=0; ) { send[i] = (i + 1); }
  }

  count = (DATA_SIZE / size);
  MPI_Scatter(send, count, MPI_INT, recv, count, MPI_INT, 0, MPI_COMM_WORLD);

  // each node now processes its share of data
  res = count; //here: count how many prime numbers are contained in the array
  outer: for(i = count; (--i) >= 0; ) {
    for(j = ((int)(sqrt(recv[i]))|1); j>1; j--) {
      if((recv[i] % j) == 0) {
        res--;
        break; }
    }
  }
  printf("Process %d discovered %d primes in the numbers from %d to %d.\n", rank, res, recv[0], recv[count-1]);

  MPI_Gather(&res, 1, MPI_INT, recv, 1, MPI_INT, 0, MPI_COMM_WORLD);

  if(rank == 0) { //if root, process the received data
    res = 0;
    for(i = size; (--i) >= 0; ) { //add up the prime number counts
      res += recv[i];
    }
    printf("The total number of primes in the first %d natural numbers is %d.\n", (count*size), res);
  }

  MPI_Finalize();
  return 0;
}