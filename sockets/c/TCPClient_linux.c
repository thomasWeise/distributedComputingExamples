#include <stdio.h>      //compile: gcc TCPClient_linux.c -o TCPClient_linux
#include <sys/socket.h> //Warning: This program does not perform any error handling.
#include <arpa/inet.h>  //In any real program, you need to handle errors.
#include <string.h>
#include <unistd.h>

int main(int argc, char *argv[])  {
  int  client;  struct sockaddr_in address;   char data;

  client = socket(PF_INET, SOCK_STREAM, IPPROTO_TCP); //Allocate TCP Socket

  memset(&address, 0, sizeof(address)); //clear socket address
  address.sin_family      = AF_INET; //IPv4 address
  address.sin_addr.s_addr = inet_addr("127.0.0.1");//set to (loopback) IP address
  address.sin_port        = htons(9999);  //make port in network byte order

  connect(client, (struct sockaddr *)&address, sizeof(address)); //(*@\clientBox{1+2)}@*)

  data = 2;
  send(client, &data, 1, 0); //(*@\clientBox{3)}@*)

  close(client);  //(*@\clientBox{4)}@*)
  return 0;
  }
