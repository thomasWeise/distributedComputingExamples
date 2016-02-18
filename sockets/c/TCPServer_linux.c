#include <stdio.h>      //compile: gcc TCPServer_linux.c -o TCPServer_linux
#include <sys/socket.h> //Warning: This program does not perform any error handling.
#include <netinet/in.h> //In any real program, you need to handle errors.
#include <arpa/inet.h>
#include <string.h>
#include <unistd.h>

int main(int argc, char *argv[])  {
  int                server, j, client;
  socklen_t          addrSize;
  struct sockaddr_in serverAddr, clientAddr;
  char               data;

  memset(&serverAddr, 0, sizeof(serverAddr));//clear socket address
  serverAddr.sin_family      = AF_INET;      //IPv4 address
  serverAddr.sin_addr.s_addr = htonl(INADDR_ANY);//don't care network interface
  serverAddr.sin_port        = htons(9999); //bind to port 9999
  addrSize                   = sizeof(clientAddr);

  server = socket(PF_INET, SOCK_STREAM, IPPROTO_TCP); //Allocate TCP socket
  bind(server, (struct sockaddr *) &serverAddr, sizeof(serverAddr)); //(*@\serverBox{1)}@*)
  listen(server, 5);  //(*@\serverBox{2)}@*)

  for (j = 5; (--j) >= 0;)  {
    client = accept(server, (struct sockaddr *) &clientAddr, &addrSize); //(*@\serverBox{3)}@*)
    printf("New connection from %s\n", inet_ntoa(clientAddr.sin_addr));

    if(recv(client, &data, 1, 0) == 1) {   printf("%d\n", data);  } //(*@\serverBox{4} + \clientBox{3})@*)
    close(client); //(*@\clientBox{4)}@*)
  }
  close(server); //(*@\serverBox{5)}@*)
}
