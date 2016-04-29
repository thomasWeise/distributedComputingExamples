# Examples for JSON RPC

[JSON RPC](https://en.wikipedia.org/wiki/JSON-RPC) is a remote procedure call ([RPC](https://en.wikipedia.org/wiki/Remote_procedure_call)) approach (specified [here](http://json-rpc.org/)) where the exchanged data structures are encoded in the JavaScript Object Notation ([JSON](https://en.wikipedia.org/wiki/JSON)). The data is exchanged via either [HTTP](https://en.wikipedia.org/wiki/Hypertext_Transfer_Protocol) or [TCP](https://en.wikipedia.org/wiki/Transmission_Control_Protocol).

JSON RCPs are an alternative to [web services](https://en.wikipedia.org/wiki/Web_service) ([examples](http://github.com/thomasWeise/distributedComputingExamples/tree/master/webServices/)), but are more light-weight (as the data structures smaller and simpler than [SOAP](https://en.wikipedia.org/wiki/SOAP)/[XML](https://en.wikipedia.org/wiki/XML), [examples for XML processing](http://github.com/thomasWeise/distributedComputingExamples/tree/master/xml/java)).

If we compare JSON RPC implemented with [briandilley](https://github.com/briandilley)'s [jsonrpc4j](https://github.com/briandilley/jsonrpc4j) framework to web services implemented with [Axis2](http://axis.apache.org/axis2/java/core/) and [Java RMI](https://en.wikipedia.org/wiki/Java_remote_method_invocation) ([examples](http://github.com/thomasWeise/distributedComputingExamples/tree/master/javaRMI/)), then we find that

1. It has many positive features of web services (which Java RMI lacks), namely is human-readable and can be transported over HTTP.
2. The implementation is more light-weight: The protocol data units are smaller, the codebase seems to be smaller.
3. No code needs to be generated and we can specify services as interfaces that we can use on both the client and server side - exactly as we do in Java RMI.
4. JSON RPC services can either be deployed into a servlet container or compiled into a fat jar, which is quite nice.

So JSON RPCs are quite a nice technology.

## 1. Examples

### 1.1. (Complex Number) Calculator RPC Server

The complex number [calculator](http://github.com/thomasWeise/distributedComputingExamples/tree/master/jsonRPC/examples/calculator/server/src/main/java/calculator/Calculator.java) JSON RPC server offers methods to add, subtract, multiply, and divide [complex numbers](https://en.wikipedia.org/wiki/Complex_number). This example is very similar to the Complex Number Calculator [Web Service](http://github.com/thomasWeise/distributedComputingExamples/tree/master/webServices/) example server.

We introduce a new [data type](http://github.com/thomasWeise/distributedComputingExamples/tree/master/jsonRPC/examples/calculator/server/src/main/java/calculator/Complex.java) for representing a complex number in form of a [JavaBean](https://en.wikipedia.org/wiki/JavaBeans).

1. [`Complex.java`](http://github.com/thomasWeise/distributedComputingExamples/tree/master/jsonRPC/examples/calculator/server/src/main/java/calculator/Complex.java)
1. [`CalculatorInterface.java`](http://github.com/thomasWeise/distributedComputingExamples/tree/master/jsonRPC/examples/calculator/server/src/main/java/calculator/CalculatorInterface.java)
1. [`Calculator.java`](http://github.com/thomasWeise/distributedComputingExamples/tree/master/jsonRPC/examples/calculator/server/src/main/java/calculator/Calculator.java)
1. [`CalculatorServlet.java`](http://github.com/thomasWeise/distributedComputingExamples/tree/master/jsonRPC/examples/calculator/server/src/main/java/calculator/CalculatorServlet.java)
1. [`web.xml`](http://github.com/thomasWeise/distributedComputingExamples/tree/master/jsonRPC/examples/calculator/server/src/main/webapp/WEB-INF/web.xml) 

You need to compile (via [Maven](http://maven.apache.org/)) and deploy to a [servlet container](https://en.wikipedia.org/wiki/Web_container).

### 1.2. (Complex Number) Calculator Web Service Client

The example client of the calculator JSON RPC introduced above creates two complex numbers `3+11i` and `5+7i`, multiplies then with each other, and prints the result. The multiplication is, of course, done by the web service. This example is very similar to the Complex Number Calculator [Web Service](http://github.com/thomasWeise/distributedComputingExamples/tree/master/webServices/) example client.

The compiled jar archive is a stand-alone executable which you can run via `java -jar calculatorJSONClient-full.jar`.

1. [`Complex.java`](http://github.com/thomasWeise/distributedComputingExamples/tree/master/jsonRPC/examples/calculator/client/src/main/java/calculator/Complex.java)
1. [`CalculatorInterface.java`](http://github.com/thomasWeise/distributedComputingExamples/tree/master/jsonRPC/examples/calculator/client/src/main/java/calculator/CalculatorInterface.java)
1. [`TestClient.java`](http://github.com/thomasWeise/distributedComputingExamples/tree/master/jsonRPC/examples/calculator/client/src/main/java/calculator/TestClient.java)


### 1.3. Warehouse JSON RPC Service

The warehouse RPC service example shows how we can construct a stand-alone JSON RPC service managing the number of certain stocks in a warehouse. It basically maintains a [`HashMap`](http://docs.oracle.com/javase/7/docs/api/java/util/HashMap.html) storing the number of available items of a given type. This example is very similar to the Warehouse [Web Service](http://github.com/thomasWeise/distributedComputingExamples/tree/master/webServices/) example server.

The RPC server offers a method for getting the number of items of a given type and one method for changing it. The [Warehouse class](http://github.com/thomasWeise/distributedComputingExamples/tree/master/jsonRPC/examples/warehouse/server/src/main/java/warehouseServer/Warehouse.java) implementing [service interface](http://github.com/thomasWeise/distributedComputingExamples/tree/master/jsonRPC/examples/warehouse/server/src/main/java/warehouseServer/WarehouseInterface.java) itself is a plain Java object without any annotation or fancy stuff. 

1. [`WarehouseInterface.java`](http://github.com/thomasWeise/distributedComputingExamples/tree/master/webServices/examples/jsonRPC/server/src/main/java/warehouseServer/WarehouseInterface.java)
1. [`Warehouse.java`](http://github.com/thomasWeise/distributedComputingExamples/tree/master/webServices/examples/jsonRPC/server/src/main/java/warehouseServer/Warehouse.java)
1. [`WarehouseServlet.java`](http://github.com/thomasWeise/distributedComputingExamples/tree/master/webServices/examples/jsonRPC/server/src/main/java/warehouseServer/WarehouseServlet.java)
1. [`Main.java`](http://github.com/thomasWeise/distributedComputingExamples/tree/master/webServices/examples/jsonRPC/server/src/main/java/warehouseServer/Main.java)
1. [`web.xml`](http://github.com/thomasWeise/distributedComputingExamples/tree/master/webServices/examples/jsonRPC/server/src/main/resources/webapp/WEB-INF/web.xml) 

You need to compile this example with [Maven](http://maven.apache.org/)) to build a stand-alone `jar` archive. This archive does not need to be deployed anywhere, since it already contains the (Jetty) servlet container. You will obtain the fat `jar` file `warehouseJSONServer-full.jar` which you can run as `java -jar warehouseJSONServer-full.jar` from the command line. 

### 1.4. Warehouse JSON RPC Client

The example client of the warehouse JSON RPC service introduced above: It checks the warehouse for the number of "cars" and "cats", changes these numbers, and checks back the results. The client is compiled into a fat jar named `warehouseJSONClient-full.jar` containing all the required libraries. You can directly execute it via `java -jar warehouseJSONClient-full.jar` from the command line.

1. [`WarehouseInterface.java`](http://github.com/thomasWeise/distributedComputingExamples/tree/master/jsonRPC/examples/warehouse/client/src/main/java/warehouseClient/WarehouseInterface.java)
2. [`WarehouseTest.java`](http://github.com/thomasWeise/distributedComputingExamples/tree/master/jsonRPC/examples/warehouse/client/src/main/java/warehouseClient/WarehouseTest.java)


## 2. Building and Deployment

### 2.1. Import Project into Eclipse

If you import the projects from this folder in [Eclipse](http://www.eclipse.org), it may first show you a lot of errors. (I recommend using Eclipse Mars or later.) This is a Maven project, so you should "update" it first in Eclipse by

1. Make sure that you can see the `package view` on the left-hand side of the Eclipse window.
2. Right-click on the project (e.g., `calculatorJSONClient`) in the `package view`.
3. In the opening pop-up menu, left-click on `Maven`.
4. In the opening sub-menu, left-click on `Update Project...`.
5. In the opening window...
  1. Make sure the project (e.g., `calculatorJSONClient`) is selected.
  2. Make sure that `Update project configuration from pom.xml` is selected.
  3. You can also select `Clean projects`.
  4. Click `OK`.
6. Now the structure of the project in the `package view` should slightly change, the project will be re-compiled, and the errors should disappear.


### 2.2. Build Projects in Eclipse

In this example set, we have three kinds of projects: deployable RPC servers (which need to be deployed into a [servlet container](https://en.wikipedia.org/wiki/Web_container)), stand-alone servers (which can be packaged into executable `jar`s), and clients.

#### 2.2.1. Deployable Servers

Now you can build the deployable server projects, i.e., generate a [`war`](https://en.wikipedia.org/wiki/WAR_file_format_%28Sun%29) files that you can deploy in a [servlet container](https://en.wikipedia.org/wiki/Web_container). For building this `war`, take the following steps:

1. Make sure that you can see the `package view` on the left-hand side of the Eclipse window.
2. Right-click on the project (e.g., `calculatorJSONServer`) in the `package view`.
3. In the opening pop-up menu, choose `Run As`.
4. In the opening sub-menu choose `Run Configurations...`.
5. In the opening window, choose `Maven Build`
6. In the new window `Run Configurations` / `Create, manage, and run configurations`, choose `Maven Build` in the small white pane on the left side.
7. Click `New launch configuration` (the first symbol from the left on top of the small white pane).
8. Write a useful name for this configuration in the `Name` field. You can use this configuration again later.
9. In the tab `Main` enter the `Base directory` of the project, this is the folder called `calculatorJSONServer` containing the Eclipse/Maven project.
10. Under `Goals`, enter `clean compile war:war`. This will build a `war` archive.
11. Click `Apply`
12. Click `Run`
13. The build will start, you will see its status output in the console window.
14. The folder `target` will contain a `war` file, e.g., `calculatorJSONServer.war` after the build. This is the deployable archive with our application.
15. After building the `war` archive, you can now deploy it.


##### 2.2.2. Building a Stand-Alone Server

The stand-alone server projects, such as the Warehouse Server, are built into a single `jar` archive containing not just the servlet but also the servlet container. These archives do not need to be deployed and can directly be executed from the command line using `java -jar ...`. If you want to, e.g., build the `warehouse/server` example, you would take the following steps:

1. Make sure that you can see the `package view` on the left-hand side of the Eclipse window.
2. Right-click on the project (`warehouseJSONServer`) in the `package view`.
3. In the opening pop-up menu, choose `Run As`.
4. In the opening sub-menu choose `Run Configurations...`.
5. In the opening window, choose `Maven Build`
6. In the new window `Run Configurations` / `Create, manage, and run configurations`, choose `Maven Build` in the small white pane on the left side.
7. Click `New launch configuration` (the first symbol from the left on top of the small white pane).
8. Write a useful name for this configuration in the `Name` field. You can use this configuration again later.
9. In the tab `Main` enter the `Base directory` of the project, this is the folder called `jsonRPC/warehouse/server` containing the Eclipse/Maven project.
10. Under `Goals`, enter `clean compile package`. This will build a `jar` archive.
11. Click `Apply`
12. Click `Run`
13. The build will start, you will see its status output in the console window.
14. The folder `target` will contain the files `warehouseJSONServer.jar` and `warehouseJSONServer-full.jar` after the build. The first one requires several libraries in the classpath, the latter one is stand-alone, the one we are after.

If you are in the `target` folder with your command line/terminal, you can now run the client which uses the web service by doing
  
    java -jar warehouseJSONServer-full.jar

This will start up the servlet containing and the RPC servlet as well.


##### 2.2.3. Building a Client (for using a JSON RPC Service) in Eclipse

Now you can build the client projects, i.e., generate the normal [`jar`](https://en.wikipedia.org/wiki/JAR_%28file_format%29) which you can directly execute via `java -jar ...`. If you want to, e.g., build the `calculator/client` example, you would take the following steps:

1. Make sure that you can see the `package view` on the left-hand side of the Eclipse window.
2. Right-click on the project (`calculatorJSONClient`) in the `package view`.
3. In the opening pop-up menu, choose `Run As`.
4. In the opening sub-menu choose `Run Configurations...`.
5. In the opening window, choose `Maven Build`
6. In the new window `Run Configurations` / `Create, manage, and run configurations`, choose `Maven Build` in the small white pane on the left side.
7. Click `New launch configuration` (the first symbol from the left on top of the small white pane).
8. Write a useful name for this configuration in the `Name` field. You can use this configuration again later.
9. In the tab `Main` enter the `Base directory` of the project, this is the folder called `jsonRPC/calculator/client` containing the Eclipse/Maven project.
10. Under `Goals`, enter `clean compile package`. This will build a `jar` archive.
11. Click `Apply`
12. Click `Run`
13. The build will start, you will see its status output in the console window.
14. The folder `target` will contain the files `calculatorJSONClient.jar` and `calculatorJSONClient-full.jar` after the build. The first one requires several libraries in the classpath, the latter one is stand-alone, the one we are after.

If you are in the `target` folder with your command line/terminal, you can now run the client which uses the web service by doing
  
    java -jar calculatorJSONClient-full.jar

Of course, this will work only if the corresponding JSON RPC service is actually running...


### 2.3. Building under Linux without Eclipse

Under Linux, you can also simply run `make_linux.sh` in this project's folder to build the examples without Eclipse, given that you have Maven installed. This will work for any of the examples in this folder.

### 2.4. Deploy

Only the deployable server examples need to be deployed.

### 2.4.1. Deploy to GlassFish

[GlassFish](https://glassfish.java.net/) is a reference implementation of a Java EE 7 application server.

##### 2.4.1.1. Install GlassFish

The following steps are for Linux, but under Windows it will be pretty much the same.

1. Go to the GlassFish website [https://glassfish.java.net/](https://glassfish.java.net/).
2. Select [download](https://glassfish.java.net/download.html).
3. Download the Java EE 7 Web Profile, at the time of this writing, this is [GlassFish 4.1.1](http://download.java.net/glassfish/4.1.1/release/glassfish-4.1.1-web.zip)
4. Unpack the downloaded archive into a folder of your liking (let's call this folder `F`).
5. Open a terminal console and go (`cd`) into folder `F`.
6. In the folder `F` (where you unpackaged the archive), go to sub-folder `glassfish4/bin/`, i.e., to `F/glassfish4/bin/`.
7. Type `./asadmin start-domain`. (Under Windows, type `asadmin start-domain` without the leading `./`.) The server will now start.
8. Under Windows, a window may pop up asking you for allowing the program internet access permission, which you should OK.
9. Open your web browser and visit its configuration page at [`http://localhost:4848`](http://localhost:4848).

##### 2.4.1.2. Deploy `war` to GlassFish

1. Find the `war` file, e.g., `calculatorJSONServer.war` in the folder `target` of this example project.
2. Copy it into the folder `F/glassfish4/glassfish/domains/domain1`.
3. In your web browser, visit [`http://localhost:4848`](http://localhost:4848).
4. Click `Applications` in the menu pane on the right.
5. It should list `calculatorJSONServer`. If you click this menu point, you get a list of the installed servlets