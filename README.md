# Examples for Distributed Computing

[<img alt="Travis CI Build Status" src="https://img.shields.io/travis/thomasWeise/distributedComputingExamples/master.svg" height="20"/>](https://travis-ci.org/thomasWeise/distributedComputingExamples/)
[<img alt="Shippable Build Status" src="https://img.shields.io/shippable/56d905429d043da07b368422.svg" height="20"/>](https://app.shippable.com/projects/56d905429d043da07b368422)

## 1. Introduction and Contents

Distributed systems surround us everywhere today. Their most prominent example is the internet hosting the world wide web. The computing environment in enterprise computing systems is often distributed too, interconnecting different services from human resources, financial departments, to asset management systems. Many applications are even hosted in the cloud. Finally, large-scale engineering and scientific computing today rely heavily on clusters in order to parallelize their workload. These topics are discussed in my distributed computing lecture. In this repository, you can find the practical examples I use in my course.

### 1.1. Contents

1. [Sockets](http://github.com/thomasWeise/distributedComputingExamples/tree/master/sockets/)
  1. in [C](http://github.com/thomasWeise/distributedComputingExamples/tree/master/sockets/c)
  2. in [Java](http://github.com/thomasWeise/distributedComputingExamples/tree/master/sockets/java)

2. [HTML, CSS, and JavaScript](http://github.com/thomasWeise/distributedComputingExamples/tree/master/html/)

3. [Java Servlets](http://github.com/thomasWeise/distributedComputingExamples/tree/master/javaServlets/)
  1. [deployable examples](http://github.com/thomasWeise/distributedComputingExamples/tree/master/javaServlets/examples)
  2. [HTTP Proxy Servlet](http://github.com/thomasWeise/distributedComputingExamples/tree/master/javaServlets/proxy)

4. [JavaServer Pages](http://github.com/thomasWeise/distributedComputingExamples/tree/master/javaServerPages/)
  1. [deployable examples](http://github.com/thomasWeise/distributedComputingExamples/tree/master/javaServerPages/examples)
  2. [stand-alone JSPs](http://github.com/thomasWeise/distributedComputingExamples/tree/master/javaServerPages/standAloneJSPsWithJetty)

5. [Java RMI](http://github.com/thomasWeise/distributedComputingExamples/tree/master/javaRMI/)
  
6. [XML](http://github.com/thomasWeise/distributedComputingExamples/tree/master/xml/)
  1. [examples for XML documents and related standards](http://github.com/thomasWeise/distributedComputingExamples/tree/master/xml/xml)
  1. [examples for XML processing with Java](http://github.com/thomasWeise/distributedComputingExamples/tree/master/xml/java)
  
7. [Web Services](http://github.com/thomasWeise/distributedComputingExamples/tree/master/webServices/)
  
8. [Message Passing Interface](http://github.com/thomasWeise/distributedComputingExamples/tree/master/mpi/)
  
Each of the above links leads you to a sub-directory containing a set of examples. Each sub-directory has an own `README.md` file with detailed descriptions. 

Since I also use the same code in my slides, there are some special comments such as `//(*@\serverBox{2)}@*)` for formatting in my codes ... you can safely ignore them ^_^

### 1.2. Concept of this Course

The concept of this course is that we want to understand how the web and distributed enterprise application environments work. We want to do that by starting to explore how to communicate over a network at the lowest level of abstraction (normally) available to programmers, the [socket API](https://en.wikipedia.org/wiki/Network_socket). From there, we work our way up step-by-step higher levels of abstraction, i.e., simpler and more powerful API stacking on top of each other (and ultimately grounded in sockets). This way, we will gain a solid understanding how distributed applications and the web work. We will be able to look at a website and immediately have a rough understanding of how it may work, down to the nuts and bolts. For each level of abstraction that we explore, we therefore always learn example technologies. 

As said, we start at the very bottom: Communication in distributed systems today is usually either based on the [UDP](https://en.wikipedia.org/wiki/User_Datagram_Protocol) or [TCP](https://en.wikipedia.org/wiki/Transmission_Control_Protocol). Both protocols are accessed via the [socket API](https://en.wikipedia.org/wiki/Network_socket), for which we provide [examples](http://github.com/thomasWeise/distributedComputingExamples/tree/master/sockets/) in both [C](http://github.com/thomasWeise/distributedComputingExamples/tree/master/sockets/c) and [Java](http://github.com/thomasWeise/distributedComputingExamples/tree/master/sockets/java). As part of these examples, we also show how text can be encoded in Java and how to construct servers which can process multiple requests in [parallel](http://github.com/thomasWeise/distributedComputingExamples/tree/master/sockets/java/src/MinHTTPServerThreadPool.java). Sockets are thus the very basis of distributed applications, the lowest level with which a programmer might have to work.

The [world wide web](https://en.wikipedia.org/wiki/World_Wide_Web) is based on three pillars: [HTTP](https://en.wikipedia.org/wiki/Hypertext_Transfer_Protocol), [HTML](https://en.wikipedia.org/wiki/HTML)/[CSS](https://en.wikipedia.org/wiki/Cascading_Style_Sheets)/[Javascript](https://en.wikipedia.org/wiki/JavaScript), and [URLs](https://en.wikipedia.org/wiki/Uniform_Resource_Locator). HTTP, the Hyper Text Transfer Protocol, is a text-based protocol to query resources which is usually transmitted over TCP connections. Actually, we already provide  example implementations of both the [server](http://github.com/thomasWeise/distributedComputingExamples/tree/master/sockets/java/src/MinHTTPServer.java) ([web server](https://en.wikipedia.org/wiki/Web_server)) and [client](http://github.com/thomasWeise/distributedComputingExamples/tree/master/sockets/java/src/MinHTTPClient.java) ([web browser](https://en.wikipedia.org/wiki/Web_browser)) client side of the HTTP communication using [sockets](http://github.com/thomasWeise/distributedComputingExamples/tree/master/sockets/java) (and even a small [parallel web server](http://github.com/thomasWeise/distributedComputingExamples/tree/master/sockets/java/src/MinHTTPServerThreadPool.java). We then provide some rudimentary [examples](http://github.com/thomasWeise/distributedComputingExamples/tree/master/html/) for [HTML](http://github.com/thomasWeise/distributedComputingExamples/tree/master/html/example_pain/index.html), [CSS](http://github.com/thomasWeise/distributedComputingExamples/tree/master/html/example_css/index.html), and [JavaScript](http://github.com/thomasWeise/distributedComputingExamples/tree/master/html/example_javascript_calculator/index.html).

Implementing HTTP based on sockets is quite complex. Sockets allow us access TCP. What we would like to have is a similarly elegant API to access HTTP (the next higher level of abstraction). One such technology are [Java Servlets](https://en.wikipedia.org/wiki/Java_Servlet). Servlets are used to implement the server-side of a HTTP conversation. A servlet is a sub-class of a special Java class which implements handler methods for different HTTP interactions ("HTTP methods"). These methods are called by a [servlet container](https://en.wikipedia.org/wiki/Web_container), the actual implementation of the server. We can therefore fully concentrate on the application logic and don't need to worry about the protocol interaction itself. We provide a wide range of examples for [Java Servlets](http://github.com/thomasWeise/distributedComputingExamples/tree/master/javaServlets/), both [deployable examples](http://github.com/thomasWeise/distributedComputingExamples/tree/master/javaServlets/examples) as well as a stand-alone [HTTP Proxy Servlet](http://github.com/thomasWeise/distributedComputingExamples/tree/master/javaServlets/proxy). So with Java Servlets, we can build server components that can dynamically interact with a HTTP client (such as a web browser). This means that we can dynamically generate contents of a web page when a browser requests them. However, building complete dynamic web sites as Java Servlets is again quite cumbersome, as servlets are Java classes while web pages are HTML, which we would then write in form of string constants to be written to the output of a Servlet.

The next higher level [to be continued]


## 2. Software Requirements

For each example, I explicitly list the required software and discuss how it can be obtained and installed. Here I give an overview over these software components.

### 2.1. Java JDK

Most of the examples I provide are written in the Java programming language and can run under arbitrary systems, given that Java is installed. In order to compile them, you need a [Java JDK](https://en.wikipedia.org/wiki/Java_Development_Kit) installed. My examples require [Java 7](https://en.wikipedia.org/wiki/Java_version_history#Java_SE_7) or later.

Under Windows, you need to download and install Java from the [Oracle website](http://www.oracle.com/technetwork/java/javase/downloads/index.html).

Under Linux, you would do `sudo apt-get install openjdk-7-jdk` (where you can replace `7` with any later version, such as `8`, if you like)

### 2.2. Maven

Several of my Java examples are built with [Maven](https://en.wikipedia.org/wiki/Apache_Maven). All of these examples have a `pom.xml` file in their root folder. In order to build them, you thus need to install Maven.

Under Windows, you need to download and install Maven from the [Apache website](http://maven.apache.org/download.cgi).

Under Linux, you would do `sudo apt-get install maven`.

If you are using Eclipse (see below), you do not need to install Maven as it is already integrated into Eclipse.

### 2.3. Eclipse 

I recommend [Eclipse](http://www.eclipse.org) as developer environment for all the Java examples in this repository. Each Java example actually comes already with an Eclipse `.project` file and with Eclipse `.settings`. Eclipse integrates both Maven and git. This means you can clone this repository from within Eclipse and directly import the Jave projects during this process. If you then right-click the Maven projects and choosen `Maven` -> `Update Project...`, Eclipse will also download and use all required libraries and dependencies as specified by the Maven `pom.xml` for you.

You can download Eclipse from the [Eclipse website](http://www.eclipse.org). I recommend to use at least Eclipse Mars.1 for its excellent Maven and git support.

### 2.4. GlassFish Server

For running some of the [Java Servlets](http://github.com/thomasWeise/distributedComputingExamples/tree/master/javaServlets/) and [JavaServer Pages](http://github.com/thomasWeise/distributedComputingExamples/tree/master/javaServerPages/) examples, you need to download the [GlassFish Server](https://glassfish.java.net/) from the corresponding [download website](https://glassfish.java.net/download.html). I recommend using at least GlassFish 4.1.1.

### 2.5. Apache Axis2/Java

For running the [Web Service](http://github.com/thomasWeise/distributedComputingExamples/tree/master/webServices/) examples, you will need to download [Apache Axis2/Java](http://axis.apache.org/axis2/java/core/) from the corresponding [download page](http://axis.apache.org/axis2/java/core/download.html). I recommend using at least Axis2 1.7.1.

### 2.6. GCC

In order to compile the examples written in the C programming language (such as the C-based [sockets examples](http://github.com/thomasWeise/distributedComputingExamples/tree/master/sockets/c)), you will need a C compiler such as GCC. Under Linux, it should normally be already installed and can otherwise be installed via `sudo apt-get install gcc`. Under Windows, you will need to install [MinGW](http://mingw.org/), usually via the [web installer](https://sourceforge.net/projects/mingw/files/Installer/mingw-get-setup.exe/download).

### 2.7. Cross-Compiling for Windows under Linux with GCC

Several of the C examples come for Windows or Linux. GCC allows you to cross-compile, i.e., if you are using Linux, you can compile C programs for Windows. For this purpose, you would first install `sudo apt-get install gcc-mingw-w64-i686` and then can use the command `gcc-mingw-w64-i686` in the same way you would use `gcc` under MinGW.

### 2.8. MPICH

In order to build and compile our [examples](http://github.com/thomasWeise/distributedComputingExamples/tree/master/mpi/) for using the Message Passing Interface (MPI), we need an [MPI implementation](https://en.wikipedia.org/wiki/Message_Passing_Interface#Implementations). We choose [MPICH](https://en.wikipedia.org/wiki/MPICH).

Under Linux, you can install the required files via `sudo apt-get install mpich libmpich-dev`.

## 3. Licensing

This work has purely educational purposes. Besides everything mentioned below, for anything in this repository, I impose one additional licensing condition: The code must never be used for anything which might violate the laws of Germany, China, or the USA. This also holds for any other file or resource provided here.

The examples in this repository are licensed under the [GNU GENERAL PUBLIC LICENSE Version 3, 29 June 2007](http://github.com/thomasWeise/distributedComputingExamples/tree/master/LICENSE), with the exception of everything in the directories [/javaServerPages/standAloneJSPsWithJetty](http://github.com/thomasWeise/distributedComputingExamples/tree/master/javaServerPages/standAloneJSPsWithJetty) and [/javaServlets/proxy](http://github.com/thomasWeise/distributedComputingExamples/tree/master/javaServlets/proxy), which re licensed under the [Apache License v2.0](http://www.opensource.org/licenses/apache2.0.php) and are partially derived from project [embedded-jetty-jsp](https://github.com/jetty-project/embedded-jetty-jsp) with copyright (c) 1995-2013 Mort Bay Consulting Pty. Ltd.
