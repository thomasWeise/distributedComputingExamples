# Message Passing Interface (MPI)

The Message Passing Interface ([MPI](https://en.wikipedia.org/wiki/Message_Passing_Interface)) is a standard framework for highly-efficient communication in distributed systems.

## 1. Examples

The following examples are included in this folder.

### 1.1. Bare Bones

A simple MPI test program which does nothing except of initializing and disposing the MPI sub system. Launch 1 instance.

1. [bareBones.c](http://github.com/thomasWeise/distributedComputingExamples/tree/master/mpi/bareBones.c)

Build: `mpicc bareBones.c -o bareBones`

### 1.2. Basic Info

A simple MPI test program which does nothing except of initializing and disposing the MPI sub system and printing the size of the current communicator and the rank of the current process in it. Launch any number of instances.

1. [basicInfo.c](http://github.com/thomasWeise/distributedComputingExamples/tree/master/mpi/basicInfo.c)

Build: `mpicc basicInfo.c -o basicInfo`

### 1.3. Simple Point-to-Point Communication

A simple MPI program which performs some simple point-to-point communication: Each process with an even rank sends a message to the process with the next-higher rank and wants to receive a message from the process with the next-lower rank. For the odd-ranked processes, it is the other way around. Launch 2 instances, or 2n instances.

1. [simplePointToPoint.c](http://github.com/thomasWeise/distributedComputingExamples/tree/master/mpi/simplePointToPoint.c)

Build: `mpicc simplePointToPoint1.c -o simplePointToPoint1`

### 1.4. Simple Point-to-Point Communication 2

A simple MPI program which performs some simple point-to-point communication: The process with rank 0 sends a string to the process with rank 1 who receives it. Launch two instances.

1. [simplePointToPoint2.c](http://github.com/thomasWeise/distributedComputingExamples/tree/master/mpi/simplePointToPoint2.c)

Build: `mpicc simplePointToPoint2.c -o simplePointToPoint2`

## 1.5. Estimate Pi with Point-to-Point Communication

This program tries to estimate Pi in the same way as done in our Java [client](http://github.com/thomasWeise/distributedComputingExamples/tree/master/sockets/java/src/PiClient.java)/[server](http://github.com/thomasWeise/distributedComputingExamples/tree/master/sockets/java/src/PiServer.java) example for [sockets](http://github.com/thomasWeise/distributedComputingExamples/tree/master/sockets/java/) - just with MPI. Launch 4 or 5 instances. See also examples 1.8 and 1.12.

1. [piPointToPoint.c](http://github.com/thomasWeise/distributedComputingExamples/tree/master/mpi/piPointToPoint.c)

Build: `mpicc piPointToPoint.c -o piPointToPoint`

### 1.6. Deadlock Error

This program compiles but will enter a deadlock if you run it. The reason is that the processes wait for each other in a cycle. Launch two instances to see how they hang.

1. [deadlock.c](http://github.com/thomasWeise/distributedComputingExamples/tree/master/mpi/deadlock.c)

Build: `mpicc deadlock.c -o deadlock`

### 1.7. Non-Blocking Point-to-Point Communication

This program is very similar to the previous one which caused a deadlock. However, we now use non-blocking point-to-point communication. This means that we can initiate a message receive action and then send a message and then wait for the receive to complete. The deadlock disappears. Launch 2 instances.

1. [nonBlockingPointToPoint.c](http://github.com/thomasWeise/distributedComputingExamples/tree/master/mpi/nonBlockingPointToPoint.c)

Build: `mpicc nonBlockingPointToPoint.c -o nonBlockingPointToPoint`

### 1.8. Estimate Pi with Non-Blocking Point-to-Point Communication

Like example 1.5, we try to estimate Pi with point-to-point communication. However, now we perform an asynchronous computation and use non-block point-to-point communication. Launch 4 or 5 instances. See also examples 1.5 and 1.12.

1. [piNonBlockingPointToPoint.c](http://github.com/thomasWeise/distributedComputingExamples/tree/master/mpi/piNonBlockingPointToPoint.c)

Build: `mpicc piNonBlockingPointToPoint.c -o piNonBlockingPointToPoint`

### 1.9. Broadcast

The root node will broadcast a message to everyone. Launch 5 instances.

1. [broadcast.c](http://github.com/thomasWeise/distributedComputingExamples/tree/master/mpi/broadcast.c)

Build: `mpicc broadcast.c -o broadcast`

### 1.10. Gather-Scatter: The Bare Bones

This example shows the bare bones of a gather-scatter based communication.

1. [gatherScatterBareBones.c](http://github.com/thomasWeise/distributedComputingExamples/tree/master/mpi/gatherScatterBareBones.c)

Build: `mpicc gatherScatterBareBones.c -o gatherScatterBareBones`

### 1.11. Gather-Scatter: Count Primes

We use a gather-scatter based communication to count the prime numbers amongst the first 1024 numbers. The number range is divided among all workers. See also example 14, launch 4 instances.

1. [gatherScatterPrimes.c](http://github.com/thomasWeise/distributedComputingExamples/tree/master/mpi/gatherScatterPrimes.c)

Build: `mpicc gatherScatterPrimes.c -o gatherScatterPrimes -lm`

### 1.12. Gather-Scatter: Estimate Pi

This example again tries to estimate Pi, but this time we use gather-scatter based communication. Launch 4 or 5 instances. See also examples 1.5 and 1.8.

1. [piGatherScatter.c](http://github.com/thomasWeise/distributedComputingExamples/tree/master/mpi/piGatherScatter.c)

Build: `mpicc piGatherScatter.c -o piGatherScatter`

### 1.13. Reduce: Count Primes

Like in example 1.11, we want to count the number of primes amongst the first 1024 natural numbers. This time we use `reduce` in the communication. Launch 4 instances

1. [reducePrimes.c](http://github.com/thomasWeise/distributedComputingExamples/tree/master/mpi/reducePrimes.c)

Build: `mpicc reducePrimes.c -o reducePrimes -lm`

### 1.14. Memory Layout of a Struct

This example does no communication at all, but it prints the memory layout of a `struct`. This shows that the compiler may align fields in many ways and we cannot compute on where a field of a `struct` but need to use proper addressing. 

1. [structTest.c](http://github.com/thomasWeise/distributedComputingExamples/tree/master/mpi/structTest.c)

Build: `mpicc structTest.c -o structTest`

### 1.15. Struct with Scatter

We define a `struct` datatype for MPI and then send such `struct`s via scatter. Launch 4 instances.  

1. [structScatter.c](http://github.com/thomasWeise/distributedComputingExamples/tree/master/mpi/structScatter.c)

Build: `mpicc structScatter.c -o structScatter`

## 2. Building and Execution

In order to build and run our examples, you need to have a [C compiler](https://en.wikipedia.org/wiki/List_of_compilers#C_compilers) and an [MPI implementation](https://en.wikipedia.org/wiki/Message_Passing_Interface#Implementations). Here we use [GCC](https://en.wikipedia.org/wiki/GNU_Compiler_Collection) and [MPICH](https://en.wikipedia.org/wiki/MPICH).

### 2.1. Installation

#### 2.1.1. Linux

Under Linux, GCC is usually already installed, otherwise you can do `sudo apt-get install gcc`.
MPICH can be installed using `sudo apt-get install mpich libmpich-dev`.

In some environments, such as [travis ci](https://github.com/travis-ci/apt-package-whitelist/issues/406), it is not possible to install MPICH directly. Here you can do it manually, by executing the following lines in your terminal:

    currentDir=`pwd`
    mpichVersion=3.2
    cd /tmp/
    wget --no-check-certificate -q http://www.mpich.org/static/downloads/$mpichVersion/mpich-$mpichVersion.tar.gz
    tar -xzf mpich-$mpichVersion.tar.gz
    cd mpich-$mpichVersion
    mkdir build && cd build
    sudo ../configure CC=$CC CXX=$CXX --disable-fortran --disable-romio
    sudo make -j2
    sudo make install 
    cd "$currentDir"
    
#### 2.1.2. Windows
    
Until a two or so years ago (at the time of this writing), you could use [MPICH](https://en.wikipedia.org/wiki/MPICH) under Windows. Unfortunately, it seems that MPICH supported Windows only [until version `1.4.1p`](http://stackoverflow.com/questions/21153750) and it seems there is no MPICH for Windows anymore, at the time of this writing. We could use `MS-MPI` instead, but getting this to work seems to be a a hassle, see

1. [https://github.com/scisoft/autocmake/issues/85](https://github.com/scisoft/autocmake/issues/85) (for FORTRAN)
2. [http://stackoverflow.com/questions/32200131](http://stackoverflow.com/questions/32200131) (for FORTRAN)
3. [http://stackoverflow.com/questions/19755272](http://stackoverflow.com/questions/19755272) (for FORTRAN)
4. [http://stackoverflow.com/questions/21153750](http://stackoverflow.com/questions/21153750)
5. [https://social.microsoft.com/Forums/en-US/245dcda4-7699-494f-bbe1-b76eb19e53da/linking-msmpi-with-mingw-gfortran?forum=windowshpcmpi](https://social.microsoft.com/Forums/en-US/245dcda4-7699-494f-bbe1-b76eb19e53da/linking-msmpi-with-mingw-gfortran?forum=windowshpcmpi) (FORTRAN again).

For now, I list how you can get to MS-MPI from the MPICH size. *I have not yet found out how to get it to run with MinGW (let alone for crosscompilation from Linux to Windows).* _This summary below is thus, basically, useless._

1. To download MPICH visit [http://www.mpich.org/downloads/](http://www.mpich.org/downloads/)
2. Naturally, you would select a Windows distribution of MPICH - scroll down the page until point `Microsoft Windows` at the very bottom.
3. Click the link. At the time of this writing, this is version [1.0.3](http://msdn.microsoft.com/en-us/library/bb524831%28v=vs.85%29.aspx).
4. Interestingly, this link will lead you to [Microsoft](https://msdn.microsoft.com/en-us/library/bb524831(v=vs.85).aspx): scroll to "MS-MPI downloads" and click. At the time of this writing, this is [MS-MPI v7](http://go.microsoft.com/FWLink/p/?LinkID=389556).
5. You arrive at (yet another) [download page](https://www.microsoft.com/en-us/download/details.aspx?id=49926) which also provides installation instructions. Oddly enough, on the page, there is neither a button nor any link for doing the downlad as the time of this writing.
6. Well, I found [http://www.microsoft.com/en-us/download/details.aspx?id=47259](http://www.microsoft.com/en-us/download/details.aspx?id=47259) from where you can seemingly download version 6 of MS-MPI. Not as good as version 7, but it will do.
7. When clicking the download button on that page, you get to (arghh!!!) [another download](http://www.microsoft.com/en-us/download/confirmation.aspx?id=47259). There I choose to [download `MSMpiSetup.exe`](https://download.microsoft.com/download/6/4/A/64A7852A-A8C3-476D-908C-30501F761DF3/MSMpiSetup.exe).
8. After downloading `MSMpiSetup.exe`, right-click it an choose `Run as Administrator`.
9. In the opening install screen, click `Next`, accept the license agreement by checking the check box and click `Next` again.
10. Leave the installation path as is (`C:\Program Files\Microsoft MPI\`) and click `Next` again. Then click `Install` and after the process completes, click `Finish`.
    
### 2.2. Building

#### 2.2.1. Linux

Under Linux, you can now compile each example using `mpicc` which becomes available after the above installation. For instance, you would compile example 1.5. as `mpicc piPointToPoint.c -o piPointToPoint`. For some examples (such as 1.11 and 1.13), you need to add the parameter [`-lm`](http://www.stackoverflow.com/questions/10447791/), as they need to be linked against the math library.

#### 2.2.2. Windows

As said before, currently not supported by this README.md, sorry.

### 2.3. Execution

### 2.3.1. Linux

After compiling, you can now execute the programs using `mpirun`. For the example 1.5 above, you would do `mpirun -n 4 ./piPointToPoint`.

### 2.3.2. Windows

As said before, currently not supported by this README.md, sorry.