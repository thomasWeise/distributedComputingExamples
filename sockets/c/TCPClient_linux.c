#include <stdio.h>
#include <sys/socket.h>
#include <arpa/inet.h>
#include <string.h>
#include <unistd.h>

int main(int argc, char *argv[])  {    //build: gcc TCPClient_linux.cpp -o TCPClient_linux
  int  client;  struct sockaddr_in address;   char data;

  client = socket(PF_INET, SOCK_STREAM, IPPROTO_TCP);     //Allocate TCP Socket

  memset(&address, 0, sizeof(address));                   //build socket address
  address.sin_family      = AF_INET;                      //AF_INET = TCP
  address.sin_addr.s_addr = inet_addr("127.0.0.1");       //create IP address
  address.sin_port        = htons(9999);                  //make port in network byte order

  connect(client, (struct sockaddr *)&address, sizeof(address)); //(*@\clientBox{1+2)}@*)

  data = 2;
  send(client, &data, 1, 0);                              //(*@\clientBox{3)}@*)

  close(client);                                          //(*@\clientBox{4)}@*)

  return 0;
  }
