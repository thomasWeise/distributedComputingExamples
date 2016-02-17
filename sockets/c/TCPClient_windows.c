#include <stdio.h>
#include <winsock.h>

int main(int argc, char *argv[])  {
  int         client;             struct sockaddr_in address;
  WSADATA     wsaData;            char               data;

  WSAStartup(MAKEWORD(2, 0), &wsaData); //Initialize WinSock
  client = socket(PF_INET, SOCK_STREAM, IPPROTO_TCP); //Allocate TCP Socket

  memset(&address, 0, sizeof(address)); //clear socket address
  address.sin_family      = AF_INET;  //IPv4 address
  address.sin_addr.s_addr = inet_addr("127.0.0.1");//create (loopback) IP address
  address.sin_port        = htons(9999); //make port in network byte order

  connect(client, (struct sockaddr *)&address, sizeof(address)); //(*@\clientBox{1+2)}@*)

  data = 2;
  send(client, &data, 1, 0);  //(*@\clientBox{3)}@*)

  closesocket(client);  //(*@\clientBox{4)}@*)
  WSACleanup();  //Finalize WinSock
  return 0;
  }