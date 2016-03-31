#!/bin/bash

# strict error handling
set -o pipefail  # trace ERR through pipes
set -o errtrace  # trace ERR through 'time command' and other functions
set -o nounset   # set -u : exit the script if you try to use an uninitialised variable
set -o errexit   # set -e : exit the script if any statement returns a non-true return value

currentDir=`pwd`
echo "We now build the Java-Servlet based Proxy."

rm -rf target
mvn clean compile package
rm -rf target/generated-sources
rm -rf target/maven-archiver
rm -rf target/maven-status
rm -rf target/proxy.jar
rm -rf target/proxy-0.0.8-full.jar

echo "Successfully finished building the Java-Servlet based Proxy."