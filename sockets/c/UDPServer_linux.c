#include <stdio.h>      //compile: gcc UDPServer_linux.c -o UDPServer_linux
#include <string.h>     //Warning: This program does not perform any error handling.
#include <sys/socket.h> //In any real program, you need to handle errors.
#include <arpa/inet.h>
#include <unistd.h>

int main(int argc, char *argv[])  {
  int                server, j;
  socklen_t          addrSize;
  struct sockaddr_in serverAddr, clientAddr;
  char               data;

  memset(&serverAddr, 0, sizeof(serverAddr));    //Clear address struct
  serverAddr.sin_family      = AF_INET;          //IPv4 address
  serverAddr.sin_addr.s_addr = htonl(INADDR_ANY);//don't care network interface
  serverAddr.sin_port        = htons(9998);      //serve at port 9998
  addrSize                   = sizeof(clientAddr);

  server = socket(PF_INET, SOCK_DGRAM, IPPROTO_UDP); //Allocate UDP socket
  bind(server, (struct sockaddr *) &serverAddr, sizeof(serverAddr)); //(*@\serverBox{1)}@*)

  for (j = 5; (--j) >= 0;)  { // then receive 1 byte package data and get client address, with flags=0
    recvfrom(server, &data, 1, 0, (struct sockaddr *) &clientAddr, &addrSize); //(*@\serverBox{2)}@*)
    printf("New message %d from %s\n", data, inet_ntoa(clientAddr.sin_addr));  //(*@\serverBox{3)}@*)
  }
  close(server); //(*@\serverBox{5)}@*)
} 
