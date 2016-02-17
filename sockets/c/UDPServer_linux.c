#include <stdio.h>
#include <string.h>
#include <sys/socket.h>
#include <arpa/inet.h>
#include <unistd.h>

int main(int argc, char *argv[])  {  //build: gcc TCPClient_linux.c -o TCPClient_linux
  int                server, j;
  socklen_t          addrSize;
  struct sockaddr_in serverAddr, clientAddr;
  char               data;

  memset(&serverAddr, 0, sizeof(serverAddr));
  serverAddr.sin_family      = AF_INET;
  serverAddr.sin_addr.s_addr = htonl(INADDR_ANY);
  serverAddr.sin_port        = htons(9998);
  addrSize                   = sizeof(clientAddr);

  server = socket(AF_INET, SOCK_DGRAM, IPPROTO_UDP);                           //Allocate UDP socket
  bind(server, (struct sockaddr *) &serverAddr, sizeof(serverAddr));           //(*@\serverBox{1)}@*)

  for (j = 5; (--j) >= 0;)  {
    recvfrom(server, &data, 1, 0, (struct sockaddr *) &clientAddr, &addrSize); //(*@\serverBox{2)}@*)
    printf("New message %d from %s\n", data, inet_ntoa(clientAddr.sin_addr));  //(*@\serverBox{3)}@*)
  }
  close(server);                                                               //(*@\serverBox{5)}@*)
} 
