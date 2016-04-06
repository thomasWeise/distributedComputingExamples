# Examples for Web Services

Here you can find examples for using [Web Services](https://en.wikipedia.org/wiki/Web_service). Web services are the basic foundation of many distributed enterprise computing systems and [service-oriented architectures](https://en.wikipedia.org/wiki/Service-oriented_architecture). Since they use the [XML](http://github.com/thomasWeise/distributedComputingExamples/tree/master/xml/)-based [SOAP](https://en.wikipedia.org/wiki/SOAP) protocol usually over [HTTP](https://en.wikipedia.org/wiki/Hypertext_Transfer_Protocol), they can be implemented as Java Servlets](http://github.com/thomasWeise/distributedComputingExamples/tree/master/javaServlets/). How to find and use a given web service is often specified in [WSDL](https://en.wikipedia.org/wiki/Web_Services_Description_Language).

## 1. Examples

### 1.1. Warehouse Web Service

The warehouse web service example shows how we can construct a simple web service managing the number of certain stocks in a warehouse. It basically maintains a [`HashMap`](http://docs.oracle.com/javase/7/docs/api/java/util/HashMap.html) storing the number of available items of a given type. The web service offers a method for getting the number of items of a given type and one method for changing it. The [class](http://github.com/thomasWeise/distributedComputingExamples/tree/master/webServices/examples/warehouse/server/src/main/java/warehouseServer/Warehouse.java) implementing web service itself is a plain Java object without any annotation or fancy stuff. Based on the annotations in the [`services.xml`](http://github.com/thomasWeise/distributedComputingExamples/tree/master/webServices/examples/warehouse/server/src/main/resources/META-INF/services.xml) file, [Axis2](http://axis.apache.org/axis2/java/core/) knows how to expose the public methods of this object as service.

1. [`Warehouse.java`](http://github.com/thomasWeise/distributedComputingExamples/tree/master/webServices/examples/warehouse/server/src/main/java/warehouseServer/Warehouse.java)
1. [`services.xml`](http://github.com/thomasWeise/distributedComputingExamples/tree/master/webServices/examples/warehouse/server/src/main/resources/META-INF/services.xml) 

You need to compile (via [Maven](http://maven.apache.org/)) and deploy (to [Axis2](http://axis.apache.org/axis2/java/core/)) this web service before you can compile the warehouse web service client, the next example below.

### 1.2. Warehouse Web Service Client

The example clients of the warehouse web service introduced above: There are two example clients, [one that gets](http://github.com/thomasWeise/distributedComputingExamples/tree/master/webServices/examples/warehouse/client/src/main/java/warehouseClient/GetStockTest.java) the number of "cars" in the warehouse and [one that adds 100 "cars"](http://github.com/thomasWeise/distributedComputingExamples/tree/master/webServices/examples/warehouse/client/src/main/java/warehouseClient/ChangeStockTest.java) to the stock. Both of them are compiled into the same `jar` archive.

In order to get the current number of "cars" in the warehouse, you would then invoke

    java -cp warehouseClient-full.jar warehouseClient.GetStockTest

In order to add 100 "cars" into the warehouse, you would then invoke

    java -cp warehouseClient-full.jar warehouseClient.ChangeStockTest

Obviously, instead of the hard-coded "cars" stock type and number 100, one could use our service for managing arbitrary numbers of arbitrary stock.

In order to build the warehouse service client, you need to have the warehouse web service running on `localhost` and providing its WSDL [http://localhost:8080/axis2/services/WarehouseService?wsdl](http://localhost:8080/axis2/services/WarehouseService?wsdl) (as [Axis2](http://axis.apache.org/axis2/java/core/) does in its default configuration). The reason is that our [Maven](http://maven.apache.org/) [build](http://github.com/thomasWeise/distributedComputingExamples/tree/master/webServices/examples/warehouse/client/pom.xml) will use that WSDL to generate two classes, `WarehouseServiceCallbackHandler.java` and `WarehouseServiceStub.java` (which are not part of this repository) during the build process. These classes are needed by the client and without them it won't compile. Hence you will not be able to compile the warehouse service client before compiling and deploying the warehouse web service.

1. [`GetStockTest.java`](http://github.com/thomasWeise/distributedComputingExamples/tree/master/webServices/examples/warehouse/client/src/main/java/warehouseClient/GetStockTest.java)
2. [`ChangeStockTest.java`](http://github.com/thomasWeise/distributedComputingExamples/tree/master/webServices/examples/warehouse/client/src/main/java/warehouseClient/ChangeStockTest.java)

### 1.3. Text Client

Here we make a client for a web service out there in the internet: The [Text Casing Service](http://www.dataaccess.com/webservicesserver/textcasing.wso) by the [Data Access Corporation](http://www.dataaccess.com/). This free web service can receive a string and transform its [case](https://en.wikipedia.org/wiki/Letter_case), e.g., change all characters to upper case, to lower case, or to title case. The [client](http://github.com/thomasWeise/distributedComputingExamples/tree/master/webServices/examples/textClient/src/main/java/textClient/TestClient.java) performs the latter: It sends a fixed string to the web service in order to have it transformed to title case. It prints the result.

Building this example is not much different from building the previous one: Our [Maven](http://maven.apache.org/) [build](http://github.com/thomasWeise/distributedComputingExamples/tree/master/webServices/examples/warehouse/client/pom.xml) will first download the [WSDL](http://www.dataaccess.com/webservicesserver/textcasing.wso) of the web service and use it to generate two classes, `TextCasingCallbackHandler.java` and `TextCasingStub.java`. These classes are needed by the client (and without them it cannot compile). Hence, before you run the Maven build, you will see errors in the Eclipse project.

1. [`TestClient.java`](http://github.com/thomasWeise/distributedComputingExamples/tree/master/webServices/examples/textClient/src/main/java/textClient/TestClient.java)
