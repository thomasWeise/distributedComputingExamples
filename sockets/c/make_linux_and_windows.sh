#!/bin/bash

# strict error handling
set -o pipefail  # trace ERR through pipes
set -o errtrace  # trace ERR through 'time command' and other functions
set -o nounset   # set -u : exit the script if you try to use an uninitialised variable
set -o errexit   # set -e : exit the script if any statement returns a non-true return value

echo "Building socket examples in C for Linux."

rm -f TCPClient_linux
rm -f TCPServer_linux
rm -f UDPClient_linux
rm -f UDPServer_linux
rm -f TCPClient_windows.exe
rm -f TCPServer_windows.exe
rm -f UDPClient_windows.exe
rm -f UDPServer_windows.exe

gcc TCPClient_linux.c -o TCPClient_linux
gcc TCPServer_linux.c -o TCPServer_linux
gcc UDPClient_linux.c -o UDPClient_linux
gcc UDPServer_linux.c -o UDPServer_linux

#cross-compiling requires 'sudo apt-get install gcc gcc-mingw-w64-i686'
i686-w64-mingw32-gcc TCPClient_windows.c -o TCPClient_windows.exe -lws2_32
i686-w64-mingw32-gcc TCPServer_windows.c -o TCPServer_windows.exe -lws2_32
i686-w64-mingw32-gcc UDPClient_windows.c -o UDPClient_windows.exe -lws2_32
i686-w64-mingw32-gcc UDPServer_windows.c -o UDPServer_windows.exe -lws2_32

echo "Finished building socket examples in C for Linux."