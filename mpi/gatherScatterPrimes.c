#include <mpi.h>    // import MPI header
#include <stdio.h>  // needed for printf
#include <math.h>   // needed for sqrt

#define DATA_SIZE 1024 // let's count the primes among the first 1024 numbers

int main(int argc, char *argv[]) {
  int send[DATA_SIZE], recv[DATA_SIZE];
  int rank, size, count, root, res, i, j;
  MPI_Status status;

  MPI_Init(&argc, &argv); // initialize MPI
  MPI_Comm_rank(MPI_COMM_WORLD, &rank); // get own rank/ID
  MPI_Comm_size(MPI_COMM_WORLD, &size); // get total number of processes

  if(rank == 0) { //generate data (i.e., the first DATA_SIZE natural numbers) if root
    for(i = DATA_SIZE; (--i)>=0; ) { send[i] = (i + 1); }
  }

  count = (DATA_SIZE / size); // divide the data among _all_ processes
  // scatter: if rank=0, send data (and get own share); otherwise: receive data
  MPI_Scatter(send, count, MPI_INT, recv, count, MPI_INT, 0, MPI_COMM_WORLD);

  // each node now processes its share of the numbers
  res = count; //here: count how many prime numbers are contained in the array
  for(i = count; (--i) >= 0; ) { //j: test all odd numbers 1<j<sqrt(j)|1
    for(j = ((int)(sqrt(recv[i]))|1); j>1; j -= 2) {
      if((recv[i] % j) == 0) { // if a number can be divided by j
        res--; // it cannot be a prime number, reduce number of primes
        break; } // break inner loop to test next number
    }
  }
  printf("Process %d discovered %d primes in the numbers from %d to %d.\n", rank, res, recv[0], recv[count-1]);

  // gather: all processes send data to root, only root receives data
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