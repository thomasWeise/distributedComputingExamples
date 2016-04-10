#include <stdio.h>
#include <stdlib.h>
#include <mpi.h>

typedef struct {  int     myIntA;
                  int     myIntB;
                  short   myShort;
                  double  myDouble;
                  char    myChar;
                  float   myFloat;  } myStruct;

int main(int argc, char *argv[]) {
  int                 size, rank, cur, i, total, steps;
  myStruct            data, *send;
  MPI_Datatype        subtypes[6];   //the data types of the elements in myStruct
  int                 subblocks[6];  //how often they occur in a row
  MPI_Aint            suboffsets[6]; //their address displacements
  MPI_Datatype        myStructType;

  //initializing communication
  MPI_Init(&argc, &argv);
  MPI_Comm_rank(MPI_COMM_WORLD, &rank);

  // create a new datatype description for the MPI job structure
  i               = 0;
  subtypes[i]     = MPI_INT;    //myIntA + myIntB
  subblocks[i]    = 2;          //because there are 2 ints
  suboffsets[i++] = (((MPI_Aint)(&data.myIntA)) - ((MPI_Aint)(&data)));

  subtypes[i]     = MPI_SHORT;  //myShort
  subblocks[i]    = 1;
  suboffsets[i++] = (((MPI_Aint)(&data.myShort)) - ((MPI_Aint)(&data)));

  subtypes[i]     = MPI_DOUBLE; //myDouble
  subblocks[i]    = 1;
  suboffsets[i++] = (((MPI_Aint)(&data.myDouble)) - ((MPI_Aint)(&data)));

  subtypes[i]     = MPI_CHAR;   //myChar
  subblocks[i]    = 1;
  suboffsets[i++] = (((MPI_Aint)(&data.myChar)) - ((MPI_Aint)(&data)));

  subtypes[i]     = MPI_FLOAT;  //myFloat
  subblocks[i]    = 1;
  suboffsets[i++] = (((MPI_Aint)(&data.myFloat)) - ((MPI_Aint)(&data)));

  MPI_Type_create_struct(i, subblocks, suboffsets, subtypes, &myStructType);
  MPI_Type_commit(&myStructType);

  if(rank == 0) {
    MPI_Comm_size(MPI_COMM_WORLD, &size);

    send = (myStruct*)malloc(sizeof(myStruct) * size);
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