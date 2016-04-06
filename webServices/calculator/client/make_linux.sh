#!/bin/bash

# strict error handling
set -o pipefail  # trace ERR through pipes
set -o errtrace  # trace ERR through 'time command' and other functions
set -o nounset   # set -u : exit the script if you try to use an uninitialised variable
set -o errexit   # set -e : exit the script if any statement returns a non-true return value

echo "We now build the calculator service client."

rm -rf target
rm -f src/main/java/warehouseClient/CalculatorCallbackHandler.java
rm -f src/main/java/warehouseClient/CalculatorStub.java
mvn clean compile package
rm -rf target/generated-sources
rm -rf target/maven-archiver
rm -rf target/maven-status
rm -rf target/calculatorClient.jar

echo "Successfully finished building the calculator service client."
