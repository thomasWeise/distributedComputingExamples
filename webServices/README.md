# Examples for Web Services

Here you can find examples for using [Web Services](https://en.wikipedia.org/wiki/Web_service). Web services are the basic foundation of many distributed enterprise computing systems and [service-oriented architectures](https://en.wikipedia.org/wiki/Service-oriented_architecture). Since they use the [XML](http://github.com/thomasWeise/distributedComputingExamples/tree/master/xml/)-based [SOAP](https://en.wikipedia.org/wiki/SOAP) protocol usually over [HTTP](https://en.wikipedia.org/wiki/Hypertext_Transfer_Protocol), they can be implemented as Java Servlets](http://github.com/thomasWeise/distributedComputingExamples/tree/master/javaServlets/). How to find and use a given web service is often specified in [WSDL](https://en.wikipedia.org/wiki/Web_Services_Description_Language).

Web services are the primary technology used to build distributed enterprise application systems. A web service is a self-contained, coarse-grained building block of such a system, basically an encapsulated and published application.

Assume that we have an imaginary company. It is easy to see that the software of the human resource (HR) department of a company should interact with the software of its financial department, which, in turn, should interact with the software of the bank where the company has its account. If a new person is hired, the HR department will fill her information into its software system. This should automatically notify the system of the financial department to now pay salary to that new employee. Every month, this software could then invoke a transaction for sending the salary to the new employee in the bank's software. The three software components thus are not something like libraries or simple classes. They are self-contained, each using its own database, and provide high-level functionality. The question how this functionality is provided has the answer "Web Service".

A web service can be invoked with the XML-based SOAP protocol and can be described via WSDL. Frameworks like [Axis2](http://axis.apache.org/axis2/java/core/) can publish the functions of a normal Java class as service routines on the server side. They take care of all necessary communication, e.g., implement [HTTP](https://en.wikipedia.org/wiki/Hypertext_Transfer_Protocol) (or are [Java Servlets](https://en.wikipedia.org/wiki/Java_Servlet)), parse both  XML and SOAP, and (un)mashall data to and from XML/SOAP. For the client side, they can read a WSDL service description and generate corresponding Java classes for invoking the service described in the WSDL.

In a service-oriented architecture ([SOA](https://en.wikipedia.org/wiki/Service-oriented_architecture)), web services encapsulating stand-alone applications form the building blocks of [business processes](https://en.wikipedia.org/wiki/Business_Process). (But this is outside the scope of this example.)

## 1. Examples

### 1.1. Warehouse Web Service

The warehouse web service example shows how we can construct a simple web service managing the number of certain stocks in a warehouse. It basically maintains a [`HashMap`](http://docs.oracle.com/javase/7/docs/api/java/util/HashMap.html) storing the number of available items of a given type. The web service offers a method for getting the number of items of a given type and one method for changing it. The [class](http://github.com/thomasWeise/distributedComputingExamples/tree/master/webServices/examples/warehouse/server/src/main/java/warehouseServer/Warehouse.java) implementing web service itself is a plain Java object without any annotation or fancy stuff. Based on the annotations in the [`services.xml`](http://github.com/thomasWeise/distributedComputingExamples/tree/master/webServices/examples/warehouse/server/src/main/resources/META-INF/services.xml) file, [Axis2](http://axis.apache.org/axis2/java/core/) knows how to expose the public methods of this object as service.

1. [`Warehouse.java`](http://github.com/thomasWeise/distributedComputingExamples/blob/master/webServices/examples/warehouse/server/src/main/java/warehouseServer/Warehouse.java)
1. [`services.xml`](http://github.com/thomasWeise/distributedComputingExamples/blob/master/webServices/examples/warehouse/server/src/main/resources/META-INF/services.xml) 

You need to compile (via [Maven](http://maven.apache.org/)) and deploy (to [Axis2](http://axis.apache.org/axis2/java/core/)) this web service before you can compile the warehouse web service client, the next example below.

### 1.2. Warehouse Web Service Client

The example clients of the warehouse web service introduced above: There are two example clients, [one that gets](http://github.com/thomasWeise/distributedComputingExamples/blob/master/webServices/examples/warehouse/client/src/main/java/warehouseClient/GetStockTest.java) the number of "cars" in the warehouse and [one that adds 100 "cars"](http://github.com/thomasWeise/distributedComputingExamples/blob/master/webServices/examples/warehouse/client/src/main/java/warehouseClient/ChangeStockTest.java) to the stock. Both of them are compiled into the same `jar` archive.

In order to get the current number of "cars" in the warehouse, you would then invoke

    java -cp warehouseClient-full.jar warehouseClient.GetStockTest

In order to add 100 "cars" into the warehouse, you would then invoke

    java -cp warehouseClient-full.jar warehouseClient.ChangeStockTest

Obviously, instead of the hard-coded "cars" stock type and number 100, one could use our service for managing arbitrary numbers of arbitrary stock.

In order to build the warehouse service client, you need to have the warehouse web service running on `localhost` and providing its WSDL [http://localhost:8080/axis2/services/WarehouseService?wsdl](http://localhost:8080/axis2/services/WarehouseService?wsdl) (as [Axis2](http://axis.apache.org/axis2/java/core/) does in its default configuration). The reason is that our [Maven](http://maven.apache.org/) [build](http://github.com/thomasWeise/distributedComputingExamples/blob/master/webServices/examples/warehouse/client/pom.xml) will use that WSDL to generate two classes, `WarehouseServiceCallbackHandler.java` and `WarehouseServiceStub.java` (which are not part of this repository) during the build process. These classes are needed by the client and without them it won't compile. Hence you will not be able to compile the warehouse service client before compiling and deploying the warehouse web service.

1. [`GetStockTest.java`](http://github.com/thomasWeise/distributedComputingExamples/blob/master/webServices/examples/warehouse/client/src/main/java/warehouseClient/GetStockTest.java)
2. [`ChangeStockTest.java`](http://github.com/thomasWeise/distributedComputingExamples/blob/master/webServices/examples/warehouse/client/src/main/java/warehouseClient/ChangeStockTest.java)

### 1.3. Text Client

Here we make a client for a web service out there in the internet: The [Text Casing Service](http://www.dataaccess.com/webservicesserver/textcasing.wso) by the [Data Access Corporation](http://www.dataaccess.com/). This free web service can receive a string and transform its [case](https://en.wikipedia.org/wiki/Letter_case), e.g., change all characters to upper case, to lower case, or to title case. The [client](http://github.com/thomasWeise/distributedComputingExamples/blob/master/webServices/examples/textClient/src/main/java/textClient/TestClient.java) performs the latter: It sends a fixed string to the web service in order to have it transformed to title case. It prints the result.

Building this example is not much different from building the previous one: Our [Maven](http://maven.apache.org/) [build](http://github.com/thomasWeise/distributedComputingExamples/blob/master/webServices/examples/warehouse/client/pom.xml) will first download the [WSDL](http://www.dataaccess.com/webservicesserver/textcasing.wso) of the web service and use it to generate two classes, `TextCasingCallbackHandler.java` and `TextCasingStub.java`. These classes are needed by the client (and without them it cannot compile). Hence, before you run the Maven build, you will see errors in the Eclipse project.

1. [`TestClient.java`](http://github.com/thomasWeise/distributedComputingExamples/blob/master/webServices/examples/textClient/src/main/java/textClient/TestClient.java)


### 1.4. (Complex Number) Calculator Web Service

The complex number [calculator](http://github.com/thomasWeise/distributedComputingExamples/blob/master/webServices/examples/calculator/server/src/main/java/calculator/Calculator.java) web service offers methods to add, subtract, multiply, and divide [complex numbers](https://en.wikipedia.org/wiki/Complex_number). We therefore introduce a new [data type](http://github.com/thomasWeise/distributedComputingExamples/blob/master/webServices/examples/calculator/server/src/main/java/calculator/Complex.java) for representing a complex number in form of a [JavaBean](https://en.wikipedia.org/wiki/JavaBeans). If the bean is mapped to a namespace URI in the [`services.xml`](http://github.com/thomasWeise/distributedComputingExamples/blob/master/webServices/examples/calculator/server/src/main/resources/META-INF/services.xml) file, it can be used as parameter and return value of the service routines, i.e., our service can receive and respond with complex Java objects. The web service is otherwise structured in exactly the same way as our warehouse web service.

1. [`Calculator.java`](http://github.com/thomasWeise/distributedComputingExamples/blob/master/webServices/examples/calculator/server/src/main/java/calculator/Calculator.java)
1. [`Complex.java`](http://github.com/thomasWeise/distributedComputingExamples/blob/master/webServices/examples/calculator/server/src/main/java/calculator/Complex.java)
1. [`services.xml`](http://github.com/thomasWeise/distributedComputingExamples/blob/master/webServices/examples/calculator/server/src/main/resources/META-INF/services.xml) 

You need to compile (via [Maven](http://maven.apache.org/)) and deploy (to [Axis2](http://axis.apache.org/axis2/java/core/)) this web service before you can compile the calculator web service client, the next example below.

A similar example has been provided as [JSON RPC](http://github.com/thomasWeise/distributedComputingExamples/tree/master/jsonRPC/).

### 1.5. (Complex Number) Calculator Web Service Client

The example client of the calculator web service introduced above creates two complex numbers `3+11i` and `5+7i`, multiplies then with each other, and prints the result. The multiplication is, of course, done by the web service.

In order to build the calculator service client, you need to have the calculator web service running on `localhost` and providing its WSDL [http://localhost:8080/axis2/services/Calculator?wsdl](http://localhost:8080/axis2/services/Calculator?wsdl) (as [Axis2](http://axis.apache.org/axis2/java/core/) does in its default configuration). The reason is that our [Maven](http://maven.apache.org/) [build](http://github.com/thomasWeise/distributedComputingExamples/blob/master/webServices/examples/calculator/client/pom.xml) will use that WSDL to generate two classes, `CalculatorCallbackHandler.java` and `CalculatorStub.java` (which are not part of this repository) during the build process. These classes are needed by the client and without them it won't compile. Hence you will not be able to compile the warehouse service client before compiling and deploying the warehouse web service.

The compiled jar archive is a stand-alone executable which you can run via `java -jar calculatorClient-full.jar`.

e same way as our warehouse web service.

1. [`TestClient.java`](http://github.com/thomasWeise/distributedComputingExamples/blob/master/webServices/examples/calculator/client/src/main/java/calculatorClient/TestClient.java)

A similar example has been provided as [JSON RPC](http://github.com/thomasWeise/distributedComputingExamples/tree/master/jsonRPC/).

## 2. Building and Deployment

Our web service examples are distributed over several independent Maven projects. Actually, there is one project for each example above.

### 2.1. Import Project into Eclipse

If you import one of these projects into [Eclipse](http://www.eclipse.org), it may first show you a lot of errors. (I recommend using Eclipse Mars or later.) All projects are Maven projects and we just now assume you would have chosen (Complex Number) Calculator Web Service example, but this holds for other projects as well. Anyway, you should "update" the project first in Eclipse by

1. Make sure that you can see the `package view` on the left-hand side of the Eclipse window.
2. Right-click on the project (`calculatorServer`) in the `package view`.
3. In the opening pop-up menu, left-click on `Maven`.
4. In the opening sub-menu, left-click on `Update Project...`.
5. In the opening window...
  1. Make sure the project (`calculatorServer`) is selected.
  2. Make sure that `Update project configuration from pom.xml` is selected.
  3. You can also select `Clean projects`.
  4. Click `OK`.
6. Now the structure of the project in the `package view` should slightly change, the project will be re-compiled, and the errors should disappear.

## 2.2. Missing Java Files Cause Errors in Client Project: This is Expected/OK

This will remove the errors appearing for the *server* projects (`warehouse/server`, `calculator/server`) only. For any client project, errors *will* persist as they require some code-generation to compile. In other words, projects such as `warehouse/client`, `calculator/client`, and `textClient` are missing some Java source code files when you check them out from GitHub. These files are automatically generated from the [WSDL](https://en.wikipedia.org/wiki/Web_Services_Description_Language) descriptions of the web services they use. This code generation will take place during the Maven build, which you can perform in Eclipse as discussed below.

### 2.3. Build Project in Eclipse

Building with Eclipse via Maven allows us to either

a. web services are packaged as [`aar`](http://axis.apache.org/axis2/java/core/docs/quickstartguide.html) that can be deployed to the [Apache Axis2/Java](http://axis.apache.org/axis2/java/core/) server
b. web service clients are packaged as normal [`jar`](https://en.wikipedia.org/wiki/JAR_%28file_format%29) archives that can directly be executed by Java via the command line.

As a result, there are two slightly different build processes, one for the web service itself (i.e., the server part) and one for the web service client.

##### 2.3.1. Building a Web Service (Server Part) in Eclipse

Now you can build the server projects, i.e., generate an [`aar`](http://axis.apache.org/axis2/java/core/docs/quickstartguide.html) file that you can deploy into the [Apache Axis2/Java](http://axis.apache.org/axis2/java/core/) server. Let us assume you would build the `calculator/server` example, but the same steps (with changes names and paths) also work for the other server examples. For building this `aar`, take the following steps:

1. Make sure that you can see the `package view` on the left-hand side of the Eclipse window.
2. Right-click on the project (`calculatorServer`) in the `package view`.
3. In the opening pop-up menu, choose `Run As`.
4. In the opening sub-menu choose `Run Configurations...`.
5. In the opening window, choose `Maven Build`
6. In the new window `Run Configurations` / `Create, manage, and run configurations`, choose `Maven Build` in the small white pane on the left side.
7. Click `New launch configuration` (the first symbol from the left on top of the small white pane).
8. Write a useful name for this configuration in the `Name` field. You can use this configuration again later.
9. In the tab `Main` enter the `Base directory` of the project, this is the folder called `webServices/calculator/server` containing the Eclipse/Maven project.
10. Under `Goals`, enter `clean compile axis2-aar:aar`. This will build a `aar` archive.
11. Click `Apply`
12. Click `Run`
13. The build will start, you will see its status output in the console window.
14. The folder `target` will contain a file `calculatorServer.aar` after the build. This is the deployable archive with our web service.

##### 2.3.2. Building a Client (for using a Web Service) in Eclipse

Now you can actually build the client projects, i.e., generate the normal [`jar`](https://en.wikipedia.org/wiki/JAR_%28file_format%29) which you can directly execute via `java -jar ...`. Please notice that for doing this, you must have the web service running for this build procedure: 

1. For all examples that come as client/server pairs (`warehouse`, `calculator`), you must first built and deployed the corresponding server component of the web services in an [Apache Axis2/Java](http://axis.apache.org/axis2/java/core/) server running on the same local machine (see below how this can be done), if any.
2. The exception is the `textClient` example, which uses a web service from the internet. For building this one, you need internet access which can reach this service. Also, if the the [Data Access Corporation](http://www.dataaccess.com/) would decide to no longer provide this service, the example will no longer compile. 

Let us assume you would build the `calculator/client` example. For this, we will assume that you have first built the `calculator/server` example and deployed it as discussed later below. The same steps (with changes names and paths) also work for the other client examples. For building the `jar`, take the following steps:

1. Make sure that you can see the `package view` on the left-hand side of the Eclipse window.
2. Right-click on the project (`calculatorClient`) in the `package view`.
3. In the opening pop-up menu, choose `Run As`.
4. In the opening sub-menu choose `Run Configurations...`.
5. In the opening window, choose `Maven Build`
6. In the new window `Run Configurations` / `Create, manage, and run configurations`, choose `Maven Build` in the small white pane on the left side.
7. Click `New launch configuration` (the first symbol from the left on top of the small white pane).
8. Write a useful name for this configuration in the `Name` field. You can use this configuration again later.
9. In the tab `Main` enter the `Base directory` of the project, this is the folder called `webServices/calculator/client` containing the Eclipse/Maven project.
10. Under `Goals`, enter `clean compile package`. This will build a `jar` archive.
11. Click `Apply`
12. Click `Run`
13. The build will start, you will see its status output in the console window.
14. The folder `target` will contain the files `calculatorClient.jar` and `calculatorClient-full.jar` after the build. The first one requires Axis2 libraries in the classpath, the latter one is stand-alone, the one we are after.

If you are in the `target` folder with your command line/terminal, you can now run the client which uses the web service by doing
  
    java -jar calculatorClient-full.jar

Of course, this will work only if the web service is actually running (as it should, because otherwise you could not have done the built).

### 2.4. Building under Linux without Eclipse

Under Linux, you can also simply run the `make_linux.sh` in folders of each project folder to build the web services and clients without Eclipse, given that you have Maven installed. Of course, for building the clients, you still need access to the corresponding web services, exactly as discussed in the build receipt for Eclipse/Maven.

### 2.5. Deploy Web Services to Axis2/Java

The web services we consider are deployed into [Apache Axis2/Java](http://axis.apache.org/axis2/java/core/), which can either itself be deployed into a [servlet container](https://en.wikipedia.org/wiki/Web_container) or used as stand-alone server. Here we only consider the latter case. The deployment therefore requires you to have an Axis2 installation (which must have `JAVA_HOME` set correctly to run). We discuss this below.

#### 2.5.1. Install Apache Axis2/Java

The following steps are for Linux, but under Windows it will be pretty much the same.

1. Go to the [http://axis.apache.org/axis2/java/core/](http://axis.apache.org/axis2/java/core/) .
2. Select [Downloads](http://axis.apache.org/axis2/java/core/download.html).
3. Click to download the `Binary Distribution`, at the time of this writing, this is [Axis2-1.7.2](http://www.apache.org/dyn/closer.lua/axis/axis2/java/core/1.7.2/axis2-1.7.2-bin.zip)
4 The click leads you to a page to select the mirror. Choose the one closest to you and click the corresponding link. Here let's use [this link](http://www-eu.apache.org/dist/axis/axis2/java/core/1.7.2/axis2-1.7.2-bin.zip).
4. Unpack the downloaded archive into a folder of your liking (let's call this folder `F`).
5. Open a terminal console and go (`cd`) into folder `F`.
6. In the folder `F` (where you unpackaged the archive), go to sub-folder `axis2-1.7.2/bin/`, i.e., to `F/axis2-1.7.2/bin/`, where the `1.7.2` is to be replaced by the version number of your actual download.
7. Type `./axis2-server.sh` under Linux or `axis2server.bat` under Windows. The server will now start or it may say something like `You must set the JAVA_HOME variable before running Axis2 Script.`. In the latter case, we need to set `JAVA_HOME` properly. We discuss later how to do that. For now let us assume success.
8. Under Windows, a window may pop up asking you for allowing the program internet access permission, which you should OK.
9. Open your web browser and visit its configuration page at [`http://localhost:8080`](http://localhost:8080).

#### 2.5.2. Axis2 Complains about `CLASS_PATH`?

Axis2 wants to have an environment variable called `CLASS_PATH` pointing to your [Java JDK](https://en.wikipedia.org/wiki/Java_Development_Kit).

##### 2.5.3.1 Solution under Linux

Under Linux, we can solve this in a rather crude way, by editing the server startup script.

1. Open the file `axis2server.sh` (the one we tried to run, which complained about missing `CLASS_PATH`) with your favorite text editor.
2. Scroll to the second line, right after `#!/bin/sh`.
3. Insert `export JAVA_HOME=$(dirname $(dirname $(readlink -f $(which javac))))`
4. Save and close the file.
5. Try again to execute `./axis2-server.sh`, now it should work.

We basically now create the environment variable on the fly when starting the server.

##### 2.5.3.2 Solution under Windows

Under Windows, we can solve this in a similar crude way, by editing the server startup script.

1. Open the file `axis2server.bat` (the one we tried to run, which complained about missing `CLASS_PATH`) with your favorite text editor.
2. Scroll to the end of the fist line (the one with `@echo off`) and press enter.
3. Insert `SET JAVA_HOME=C:\Program Files\Java\jdk1.8.0_66`, where `C:\Program Files\Java\jdk1.8.0_66` is to be replaced with the installation folder of your JDK. You can find this folder by going into `C:\Program Files\Java` with the Explorer and searching for something looking like a JDK.  
4. Save and close the file.
5. Try again to execute `axis2-server.bat`, now it should work.

We basically now create the environment variable on the fly when starting the server.

##### 2.5.3. Deploy `aar` to Axis2

1. Find the `aar` archive of your Web Service server component. This will usually be in the folder `target` of the example projects.
2. Copy it into the folder `F/axis2-1.7.2/repositories/services` (where, again, `1.7.2` is to be replaced by your actual Axis2 version).
3. In your web browser, visit [`http://localhost:8080`](http://localhost:8080).
4. The service should now be listed on the web page. If you click it, you can get the automatically-generated WSDL, e.g.,

  1. [http://localhost:8080/axis2/services/Calculator?wsdl](http://localhost:8080/axis2/services/Calculator?wsdl)
  2. [http://localhost:8080/axis2/services/WarehouseService?wsdl](http://localhost:8080/axis2/services/WarehouseService?wsdl)
