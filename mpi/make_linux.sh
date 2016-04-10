#!/bin/bash

# strict error handling
set -o pipefail  # trace ERR through pipes
set -o errtrace  # trace ERR through 'time command' and other functions
set -o nounset   # set -u : exit the script if you try to use an uninitialised variable
set -o errexit   # set -e : exit the script if any statement returns a non-true return value

echo "Building MPI examples in C for Linux."

rm -f bareBones
rm -f basicInfo
rm -f simplePointToPoint
rm -f simplePointToPoint2
rm -f piPointToPoint
rm -f deadlock
rm -f nonBlockingPointToPoint
rm -f piNonBlockingPointToPoint
rm -f broadcast
rm -f gatherScatterBareBones
rm -f gatherScatterPrimes
rm -f piGatherScatter
rm -f reducePrimes
rm -f structTest
rm -f structScatter

mpicc bareBones.c -o bareBones
mpicc basicInfo.c -o basicInfo
mpicc simplePointToPoint1.c -o simplePointToPoint1
mpicc simplePointToPoint2.c -o simplePointToPoint2
mpicc piPointToPoint.c -o piPointToPoint
mpicc deadlock.c -o deadlock
mpicc nonBlockingPointToPoint.c -o nonBlockingPointToPoint
mpicc piNonBlockingPointToPoint.c -o piNonBlockingPointToPoint
mpicc broadcast.c -o broadcast
mpicc gatherScatterBareBones.c -o gatherScatterBareBones
mpicc gatherScatterPrimes.c -o gatherScatterPrimes -lm
mpicc piGatherScatter.c -o piGatherScatter
mpicc reducePrimes.c -o reducePrimes -lm
mpicc structTest.c -o structTest
mpicc structScatter.c -o structScatter

echo "Finished building MPI examples in C for Linux."