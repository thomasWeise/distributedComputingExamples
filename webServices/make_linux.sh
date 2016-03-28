#!/bin/bash

# strict error handling
set -o pipefail  # trace ERR through pipes
set -o errtrace  # trace ERR through 'time command' and other functions
set -o nounset   # set -u : exit the script if you try to use an uninitialised variable
set -o errexit   # set -e : exit the script if any statement returns a non-true return value

currentDir=`pwd`
echo "We now build all the Web Service examples in directory '$currentDir'."

axisVersion="1.7.1"
echo "For this purpose, we need to download, install, and run axis2 $axisVersion in a temporary directory."
tempDir=$(mktemp -d)
echo "Created temporary folder '$tempDir'."
cd "$tempDir"

echo "Now downloading axis2 $axisVersion."
wget -q http://www-eu.apache.org/dist/axis/axis2/java/core/$axisVersion/axis2-$axisVersion-bin.zip
echo "Finished downloading axis2 $axisVersion, now unpacking it."
unzip -q "axis2-$axisVersion-bin.zip"
cd "$tempDir/axis2-$axisVersion/bin/"

echo "We need to detect JAVA_HOME for running axis2."
JAVA_HOME=${JAVA_HOME:-}
if [[ -z "$JAVA_HOME" ]]
then
  JAVA_HOME=$(dirname $(dirname $(readlink -f $(which javac))))
  echo "JAVA_HOME was not specified, we detect it as '$JAVA_HOME'."
fi
export JAVA_HOME="$JAVA_HOME"

echo "Now executing axis2 $axisVersion in the background."
"$tempDir/axis2-$axisVersion/bin/axis2server.sh" &
axisProcess="$!"
echo "Axis2 $axisVersion is now running as background process $axisProcess."

export axisRepository="$tempDir/axis2-$axisVersion/repository/services"
echo "The path to the axis2 $axisVersion service repository is '$axisRepository'."

echo "Now waiting for axis2 server to be up and running."
set +o errexit
while true; do
    sleep 5
    wget http://localhost:8080/axis2/services/
    if [ $? -eq 0 ]; then
        break
    fi
done
set -o errexit
sleep 5
echo "Axis2 server is up and running."

echo "We can now build the examples."

cd "$currentDir"
cd "$currentDir/warehouse"
"$currentDir/warehouse/make_linux.sh"

echo "Finished building the examples."

echo "Now killing axis2 $axisVersion process $axisProcess and sub-processes."
# taken from http://stackoverflow.com/questions/392022
# but modified to avoid hanging
function killtree {
  echo "Now sending STOP to process '$1'."
  kill -STOP "$1" &
  sleep 5
  ps -e -o pid= -o ppid= | while read -r pid ppid
                           do
                             [[ $ppid = $1 ]] || continue
                             killtree "$pid"  || true # Skip over failures
                           done
  echo "Now sending CONT to process '$1'."
  kill -CONT "$1" &
  sleep 5         
  echo "Now sending TERM to process '$1'."
  kill -TERM "$1" || true
}
killtree "$axisProcess"

echo "Finished killing axis2 $axisVersion process $axisProcess."
echo "Now deleting temporary axis installation from directory '$tempDir'."
cd "$currentDir"
rm -rf "$tempDir"

echo "Successfully finished building all the Web Service examples."