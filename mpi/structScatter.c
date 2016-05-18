#include <stdio.h>
#include <stdlib.h>
#include <mpi.h>

typedef struct {  int     myIntA; // an integer (usually 4 bytes)
                  int     myIntB; // another integer (usually 4 bytes)
                  short   myShort; // a short integer (usually 2 bytes)
                  double  myDouble; // a double-precision floating point number  (usually 8 bytes)
                  char    myChar; // a char = 1 single byte  (usually 1 byte)
                  float   myFloat; // a single-precision floating point number  (usually 4 bytes)
                                   } myStruct; // name of new data type = myStruct


int main(int argc, char *argv[]) {
  int                 size, rank, cur, i, total, steps;
  myStruct            data, *send;
  MPI_Datatype        subtypes[6];   //the data types of the elements in myStruct
  int                 subblocks[6];  //how often they occur in a row
  MPI_Aint            suboffsets[6]; //their address displacements
  MPI_Datatype        myStructType;  // the new data type

  MPI_Init(&argc, &argv); // initialize MPI
  MPI_Comm_rank(MPI_COMM_WORLD, &rank); // get own rank/ID

  // create a new datatype description for the MPI job structure
  i               = 0; // index of first record is 0
  subtypes[i]     = MPI_INT;    //myIntA and myIntB
  subblocks[i]    = 2;          //because there are 2 ints
  suboffsets[i]   = (((MPI_Aint)(&data.myIntA)) - ((MPI_Aint)(&data))); //offset from start

  subtypes[++i]   = MPI_SHORT;  //second record (++i): myShort
  subblocks[i]    = 1; // there is one short
  suboffsets[i]   = (((MPI_Aint)(&data.myShort)) - ((MPI_Aint)(&data))); //offset from start

  subtypes[++i]   = MPI_DOUBLE; //third record (++i): myDouble
  subblocks[i]    = 1; // there is one double
  suboffsets[i]   = (((MPI_Aint)(&data.myDouble)) - ((MPI_Aint)(&data))); //offset from start

  subtypes[++i]   = MPI_CHAR;   //fourth record (++i): myChar
  subblocks[i]    = 1; // one char
  suboffsets[i]   = (((MPI_Aint)(&data.myChar)) - ((MPI_Aint)(&data))); //offset from start

  subtypes[++i]   = MPI_FLOAT;  //fifth record (++i): myFloat
  subblocks[i]    = 1; // there is one float
  suboffsets[i]   = (((MPI_Aint)(&data.myFloat)) - ((MPI_Aint)(&data))); //offset from start

  // register data type structure
  MPI_Type_create_struct(i, subblocks, suboffsets, subtypes, &myStructType);
  MPI_Type_commit(&myStructType); // and commit it: it can now be used

  if(rank == 0) { // if we are root
    MPI_Comm_size(MPI_COMM_WORLD, &size); // get number of processes

    send = (myStruct*)malloc(sizeof(myStruct) * size); // allocate memory
    for(i = size; (--i) >= 0; ) {
      send[i].myIntA    = rank;
      send[i].myIntB    = i;
      send[i].myShort   = (rank * rank) % size;
      send[i].myDouble  = *((double*)("Hi you!"));
      send[i].myChar    = 'V';
      send[i].myFloat   = (i / (float)size);
    }
  }

  MPI_Scatter(send, 1, myStructType, &data, 1, myStructType, 0, MPI_COMM_WORLD);

  printf("%d: received: myIntA=%d, myIntB=%d, myShort=%d, myDouble=\"%s\", myChar='%c', myFloat=%f.\n",
         rank, data.myIntA, data.myIntB, data.myShort, (char*)&data.myDouble, data.myChar, data.myFloat);

  MPI_Type_free(&myStructType);
  MPI_Finalize();
  return 0;
}