#include <stdio.h> // for printf

typedef struct {  int     myIntA; // an integer (usually 4 bytes)
                  int     myIntB; // another integer (usually 4 bytes)
                  short   myShort; // a short integer (usually 2 bytes)
                  double  myDouble; // a double-precision floating point number  (usually 8 bytes)
                  char    myChar; // a char = 1 single byte  (usually 1 byte)
                  float   myFloat; // a single-precision floating point number  (usually 4 bytes)
                                   } myStruct; // name of new data type = myStruct

#define LU(expression) ((long unsigned int)(expression)) // macro for type cast

int main(int argc, char *argv[]) {
  myStruct ds; // the data structure

  printf("Struct is %lu bytes large.\n", LU(sizeof(ds)));
  printf("Field \"myIntA\"   begins at offset %lu and is %lu bytes large.\n", LU(LU(&ds.myIntA)   - LU(&ds)), LU(sizeof(ds.myIntA)));
  printf("Field \"myIntB\"   begins at offset %lu and is %lu bytes large.\n", LU(LU(&ds.myIntB)   - LU(&ds)), LU(sizeof(ds.myIntB)));
  printf("Field \"myShort\"  begins at offset %lu and is %lu bytes large.\n", LU(LU(&ds.myShort)  - LU(&ds)), LU(sizeof(ds.myShort)));
  printf("Field \"myDouble\" begins at offset %lu and is %lu bytes large.\n", LU(LU(&ds.myDouble) - LU(&ds)), LU(sizeof(ds.myDouble)));
  printf("Field \"myChar\"   begins at offset %lu and is %lu bytes large.\n", LU(LU(&ds.myChar)   - LU(&ds)), LU(sizeof(ds.myChar)));
  printf("Field \"myFloat\"  begins at offset %lu and is %lu bytes large.\n", LU(LU(&ds.myFloat)  - LU(&ds)), LU(sizeof(ds.myFloat)));
  printf("Effectively used data size is %lu.\n", LU(sizeof(ds.myIntA) + sizeof(ds.myIntB) + sizeof(ds.myShort) + sizeof(ds.myDouble) +
                                                    sizeof(ds.myChar) + sizeof(ds.myFloat)));
  return 0;
}