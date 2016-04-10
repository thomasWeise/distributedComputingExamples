#include <stdio.h>

typedef struct {  int     myIntA;
                  int     myIntB;
                  short   myShort;
                  double  myDouble;
                  char    myChar;
                  float   myFloat;  } myStruct;

int main(int argc, char *argv[]) {
  myStruct ds;

  printf("Struct is %lu bytes large.\n", sizeof(ds));
  printf("Field \"myIntA\"   begins at offset %lu and is %lu bytes large.\n", (((long)&ds.myIntA)   - ((long)&ds)), sizeof(ds.myIntA));
  printf("Field \"myIntB\"   begins at offset %lu and is %lu bytes large.\n", (((long)&ds.myIntB)   - ((long)&ds)), sizeof(ds.myIntB));
  printf("Field \"myShort\"  begins at offset %lu and is %lu bytes large.\n", (((long)&ds.myShort)  - ((long)&ds)), sizeof(ds.myShort));
  printf("Field \"myDouble\" begins at offset %lu and is %lu bytes large.\n", (((long)&ds.myDouble) - ((long)&ds)), sizeof(ds.myDouble));
  printf("Field \"myChar\"   begins at offset %lu and is %lu bytes large.\n", (((long)&ds.myChar)   - ((long)&ds)), sizeof(ds.myChar));
  printf("Field \"myFloat\"  begins at offset %lu and is %lu bytes large.\n", (((long)&ds.myFloat)  - ((long)&ds)), sizeof(ds.myFloat));
  printf("Effectively used data size is %lu.\n", (sizeof(ds.myIntA) + sizeof(ds.myIntB) + sizeof(ds.myShort) + sizeof(ds.myDouble) +
                                                 sizeof(ds.myChar) + sizeof(ds.myFloat)));
  return 0;
}