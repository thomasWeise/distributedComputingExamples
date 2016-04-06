# Examples for Distributed Computing

[<img alt="Travis CI Build Status" src="https://img.shields.io/travis/thomasWeise/distributedComputingExamples/master.svg" height="20"/>](https://travis-ci.org/thomasWeise/distributedComputingExamples/)
[<img alt="Shippable Build Status" src="https://img.shields.io/shippable/56d905429d043da07b368422.svg" height="20"/>](https://app.shippable.com/projects/56d905429d043da07b368422)

## 1. Introduction and Contents

Distributed systems surround us everywhere today. Their most prominent example is the internet hosting the world wide web. The computing environment in enterprise computing systems is often distributed too, interconnecting different services from human resources, financial departments, to asset management systems. Many applications are even hosted in the cloud. Finally, large-scale engineering and scientific computing today rely heavily on clusters in order to parallelize their workload. These topics are discussed in my distributed computing lecture. In this repository, you can find the practical examples I use in my course.

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
  

Each of the above links leads you to a sub-directory containing a set of examples. Each sub-directory has an own `README.md` file with detailed descriptions.

Since I also use the same code in my slides, there are some special comments such as `//(*@\serverBox{2)}@*)` for formatting in my codes ... you can safely ignore them ^_^

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

### 2.6. GCC and Libraries

In order to compile the examples written in the C programming language (such as the C-based [sockets examples](http://github.com/thomasWeise/distributedComputingExamples/tree/master/sockets/c)), you will need a C compiler such as GCC. Under Linux, it should normally be already installed and can otherwise be installed via `sudo apt-get install gcc`. Under Windows, you will need to install [MinGW](http://mingw.org/), usually via the [web installer](https://sourceforge.net/projects/mingw/files/Installer/mingw-get-setup.exe/download).

#### 2.6.1. Cross-Compiling for Windows under Linux

Several of the C examples come for Windows or Linux. GCC allows you to cross-compile, i.e., if you are using Linux, you can compile C programs for Windows. For this purpose, you would first install `sudo apt-get install gcc-mingw-w64-i686` and then can use the command `gcc-mingw-w64-i686` in the same way you would use `gcc` under MinGW.

## 3. Licensing

This work has purely educational purposes. Besides everything mentioned below, for anything in this repository, I impose one additional licensing condition: The code must never be used for anything which might violate the laws of Germany, China, or the USA. This also holds for any other file or resource provided here.

The examples in this repository are licensed under the [GNU GENERAL PUBLIC LICENSE Version 3, 29 June 2007](http://github.com/thomasWeise/distributedComputingExamples/tree/master/LICENSE), with the exception of everything in the directories [/javaServerPages/standAloneJSPsWithJetty](http://github.com/thomasWeise/distributedComputingExamples/tree/master/javaServerPages/standAloneJSPsWithJetty) and [/javaServlets/proxy](http://github.com/thomasWeise/distributedComputingExamples/tree/master/javaServlets/proxy), which re licensed under the [Apache License v2.0](http://www.opensource.org/licenses/apache2.0.php) and are partially derived from project [embedded-jetty-jsp](https://github.com/jetty-project/embedded-jetty-jsp) with copyright (c) 1995-2013 Mort Bay Consulting Pty. Ltd.
