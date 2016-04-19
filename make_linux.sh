#!/bin/bash

# strict error handling
set -o pipefail  # trace ERR through pipes
set -o errtrace  # trace ERR through 'time command' and other functions
set -o nounset   # set -u : exit the script if you try to use an uninitialised variable
set -o errexit   # set -e : exit the script if any statement returns a non-true return value

currentDir=`pwd`
echo "We now build all the examples one by one in directory '$currentDir'."

cd "$currentDir/helloWorld"
"$currentDir/helloWorld/make_linux.sh"

cd "$currentDir/sockets"
"$currentDir/sockets/make_linux.sh"

cd "$currentDir/javaServlets"
"$currentDir/javaServlets/make_linux.sh"

cd "$currentDir/javaServerPages"
"$currentDir/javaServerPages/make_linux.sh"

cd "$currentDir/javaRMI"
"$currentDir/javaRMI/make_linux.sh"

cd "$currentDir/xml"
"$currentDir/xml/make_linux.sh"

cd "$currentDir/webServices"
"$currentDir/webServices/make_linux.sh"

cd "$currentDir/jsonRPC"
"$currentDir/jsonRPC/make_linux.sh"

cd "$currentDir/mpi"
"$currentDir/mpi/make_linux.sh"


echo "Successfully finished building the examples in directory'$currentDir'."