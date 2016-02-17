#!/bin/bash

# strict error handling
set -o pipefail  # trace ERR through pipes
set -o errtrace  # trace ERR through 'time command' and other functions
set -o nounset   # set -u : exit the script if you try to use an uninitialised variable
set -o errexit   # set -e : exit the script if any statement returns a non-true return value

gcc TCPClient_linux.c -o TCPClient_linux
gcc TCPServer_linux.c -o TCPServer_linux
gcc UDPClient_linux.c -o UDPClient_linux
gcc UDPServer_linux.c -o UDPServer_linux