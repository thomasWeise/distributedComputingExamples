# Examples for Web Services

Here you can find examples for using [Web Services](https://en.wikipedia.org/wiki/Web_service). Web services are the basic foundation of many distributed enterprise computing systems and [service-oriented architectures](https://en.wikipedia.org/wiki/Service-oriented_architecture). Since they use the [XML](http://github.com/thomasWeise/distributedComputingExamples/tree/master/xml/)-based [SOAP](https://en.wikipedia.org/wiki/SOAP) protocol usually over [HTTP](https://en.wikipedia.org/wiki/Hypertext_Transfer_Protocol), they can be implemented as Java Servlets](http://github.com/thomasWeise/distributedComputingExamples/tree/master/javaServlets/). How to find and use a given web service is often specified in [WSDL](https://en.wikipedia.org/wiki/Web_Services_Description_Language).

## 1. Examples

### 1.1. Warehouse Web Service

The warehouse web service example shows how we can construct a simple web service managing the number of certain stocks in a warehouse. It basically maintains a hashmap storing the number of available items for an given element. The web service offers a method for getting the number of items of a given element type and one method for changing it. The [class](http://github.com/thomasWeise/distributedComputingExamples/tree/master/webServices/examples/warehouse/server/src/main/java/warehouseServer/Warehouse.java) implementing web service itself is a plain Java object without any annotation or fancy stuff. Based on the annotations in the [`services.xml`](http://github.com/thomasWeise/distributedComputingExamples/tree/master/webServices/examples/warehouse/server/src/main/resources/META-INF/services.xml) file, Axis2 knows how to expose the public methods of this object as service.

1. [`Warehouse`](http://github.com/thomasWeise/distributedComputingExamples/tree/master/webServices/examples/warehouse/server/src/main/java/warehouseServer/Warehouse.java)
1. [`services.xml`](http://github.com/thomasWeise/distributedComputingExamples/tree/master/webServices/examples/warehouse/server/src/main/resources/META-INF/services.xml) 