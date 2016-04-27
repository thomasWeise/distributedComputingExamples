#!/bin/bash

# strict error handling
set -o pipefail  # trace ERR through pipes
set -o errtrace  # trace ERR through 'time command' and other functions
set -o nounset   # set -u : exit the script if you try to use an uninitialised variable
set -o errexit   # set -e : exit the script if any statement returns a non-true return value

echo "We now build the warehouse example web service."

rm -rf target
mvn clean compile axis2-aar:aar
rm -rf target/aar
rm -rf target/generated-sources
rm -rf target/maven-status

echo "Successfully built the warehouse server. Should we also deploy it?"

axisRepository=${axisRepository:-}
if [[ -z "$axisRepository" ]]
then
echo "No."
else
echo "Yes: deploy to '$axisRepository'."
cp target/warehouseServer.aar  "$axisRepository"
echo "Now waiting for axis2 to load and run our service."

cd /tmp/
set +o errexit 
while true; do
    sleep 5
    wget http://localhost:8080/axis2/services/WarehouseService?wsdl
    if [ $? -eq 0 ]; then
        break
    fi
done
set -o errexit 
sleep 5
echo "Warehouse web service has successfully been deployed."
fi

echo "Successfully finished building the warehouse web service."
