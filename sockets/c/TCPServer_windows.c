#include <stdio.h>//compile: gcc TCPServer_windows.c -o TCPServer_windows.exe -lws2_32
#include <winsock.h> //Warning: This program does not perform any error handling.

int main(int argc, char *argv[])  {
  int                server, j, client, addrSize;
  struct sockaddr_in serverAddr, clientAddr;
  WSADATA            wsaData;
  char               data;

  memset(&serverAddr, 0, sizeof(serverAddr));
  serverAddr.sin_family      = AF_INET; //IPv4 address
  serverAddr.sin_addr.s_addr = htonl(INADDR_ANY); //don't care network interface
  serverAddr.sin_port        = htons(9999); //bind to port 9999
  addrSize                   = sizeof(clientAddr);

  WSAStartup(MAKEWORD(2, 0), &wsaData);               //Initialize WinSock
  server = socket(PF_INET, SOCK_STREAM, IPPROTO_TCP); //Allocate TCP socket
  bind(server, (struct sockaddr *) &serverAddr, sizeof(serverAddr));//(*@\serverBox{1)}@*)
  listen(server, 5);  //(*@\serverBox{2)}@*)

  for (j = 5; (--j) >= 0;)  {
    client = accept(server, (struct sockaddr *) &clientAddr, &addrSize); //(*@\serverBox{3)}@*)
    printf("New connection from %s\n", inet_ntoa(clientAddr.sin_addr));
    // now receive 1 byte of data to client, flags=0
    if(recv(client, &data, 1, 0) == 1) { printf("%d\n", data);  } //(*@\serverBox{4} + \clientBox{3})@*)
    closesocket(client); //(*@\clientBox{4)}@*)
  }
  closesocket(server);  //(*@\serverBox{5)}@*)
  WSACleanup(); //Finalize WinSock
}