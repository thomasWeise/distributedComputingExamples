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
  

Each of the above links leads you to a sub-directory containing a set of examples. Each sub-directory has an own `README.md` file with detailed descriptions.

Since I also use the same code in my slides, there are some special comments such as `//(*@\serverBox{2)}@*)` for formatting in my codes ... you can safely ignore them ^_^

## 2. Software Requirements

### 2.1. Java JDK

Most of the examples I provide are written in the Java programming language and can run under arbitrary systems, given that Java is installed. In order to compile them, you need a [Java JDK](https://en.wikipedia.org/wiki/Java_Development_Kit) installed. My examples require [Java 7](https://en.wikipedia.org/wiki/Java_version_history#Java_SE_7) or later.

Under Windows, you need to download and install Java from the [Oracle website](http://www.oracle.com/technetwork/java/javase/downloads/index.html).

Under Linux, you would do `sudo apt-get install openjdk-7-jdk` (where you can replace `7` with any later version, such as `8`, if you like)

### 2.2. Maven

Several of my Java examples are built with [Maven](https://en.wikipedia.org/wiki/Apache_Maven). All of these examples have a `pom.xml` file in their root folder. In order to build them, you thus need to install Maven.

Under Windows, you need to download and install Maven from the [Apache website](http://maven.apache.org/download.cgi).

Under Linux, you would do `sudo apt-get install maven`. 

## 3. Licensing

This work has purely educational purposes. Besides everything mentioned below, for anything in this repository, I impose one additional licensing condition: The code must never be used for anything which might violate the laws of Germany, China, or the USA. This also holds for any other file or resource provided here.

The examples in this repository are licensed under the [GNU GENERAL PUBLIC LICENSE Version 3, 29 June 2007](http://github.com/thomasWeise/distributedComputingExamples/tree/master/LICENSE), with the exception of everything in the directories [/javaServerPages/standAloneJSPsWithJetty](http://github.com/thomasWeise/distributedComputingExamples/tree/master/javaServerPages/standAloneJSPsWithJetty) and [/javaServlets/proxy](http://github.com/thomasWeise/distributedComputingExamples/tree/master/javaServlets/proxy), which re licensed under the [Apache License v2.0](http://www.opensource.org/licenses/apache2.0.php) and are partially derived from project [embedded-jetty-jsp](https://github.com/jetty-project/embedded-jetty-jsp) with copyright (c) 1995-2013 Mort Bay Consulting Pty. Ltd.
