# Sockets in Java

Here I provide a set of examples on using [sockets](https://en.wikipedia.org/wiki/Network_socket)
with the [`Java`](https://en.wikipedia.org/wiki/Java_%28programming_language%29). I recommend using
[Eclipse](http://www.eclipse.org) as developer environment for Java. Matter of fact, this folder contains an Eclipse project named `sockets` which can directly be imported into Eclipse.

## 1. Examples

The following examples are included in this folder.

## 1.1. TCPClient / TCPServer

The simple TCP Client and TCP Server examples do nothing spectacular.
The client sends one byte with value `1` over a TCP connection to the server (which listens at port 9999 of the local host) and terminates.
The server accepts 5 incoming connections, prints the received byte, and then terminates.

1. [TCPClient.java](http://github.com/thomasWeise/distributedComputingExamples/tree/master/sockets/java/src/TCPClient.java)
1. [TCPServer.java](http://github.com/thomasWeise/distributedComputingExamples/tree/master/sockets/java/src/TCPServer.java)

## 1.2. TCPClientJava17 / TCPServerJava17

This is exactly the same example as `TCPClient/TCPServer` above, except that it uses Java 1.7's `try-with-resource` statement to handle the sockets. It is therefore both more compact and robust.

1. [TCPClientJava17.java](http://github.com/thomasWeise/distributedComputingExamples/tree/master/sockets/java/src/TCPClientJava17.java)
1. [TCPServerJava17.java](http://github.com/thomasWeise/distributedComputingExamples/tree/master/sockets/java/src/TCPServerJava17.java)

## 1.3. UDPClient / UDPServer

The simple UDP Client and UDP Server examples do nothing spectacular, actually, basically the same as the TCP client/server (except that they use port 9998 and the UDP instead of the TCP protocol).
The client sends one byte with value `1` in an UDP package to the server and terminates.
The server accepts 5 incoming packages, prints the received byte, and then terminates.

1. [UDPClient.java](http://github.com/thomasWeise/distributedComputingExamples/tree/master/sockets/java/src/UDPClient.java)
1. [UDPServer.java](http://github.com/thomasWeise/distributedComputingExamples/tree/master/sockets/java/src/UDPServer.java)

## 1.4. UDPClientJava17 / UDPServerJava17

This is exactly the same example as `UDPClient/UDPServer` above, except that it uses Java 1.7's `try-with-resource` statement to handle the sockets. It is therefore both more compact and robust.

1. [UDPClientJava17.java](http://github.com/thomasWeise/distributedComputingExamples/tree/master/sockets/java/src/UDPClientJava17.java)
1. [UDPServerJava17.java](http://github.com/thomasWeise/distributedComputingExamples/tree/master/sockets/java/src/UDPServerJava17.java)

## 1.5. TCPClientSendingRawChars / TCPServerPrintingRawChars

This example contains of a client which reads characters from `stdin` and sends them over TCP to a server listening at port 9999 of the local host. Once `return` is pressed, it will close its connection. The server part will read raw `byte`s from an incoming connection, cast them to `char`s and print them. This leads to the issue of encoding: If characters are not in the normal, Western, 7 bit range of ASCII, garbage will result.

1. [TCPClientSendingRawChars.java](http://github.com/thomasWeise/distributedComputingExamples/tree/master/sockets/java/src/TCPClientSendingRawChars.java)
1. [TCPServerPrintingRawChars.java](http://github.com/thomasWeise/distributedComputingExamples/tree/master/sockets/java/src/TCPServerPrintingRawChars.java)

## 1.6. TCPClientStructuredData / TCPServerStructuredData

As we learn in the lesson, the memory layout of even primitive data types and structures may be different from machine to machine. This example shows how we can send and receive different instances of (primitive) data types over a TCP connection with Java.

We therefore use Java's [`DataInputStream`](http://docs.oracle.com/javase/7/docs/api/java/io/DataInputStream.html)/[`DataOutputStream`](http://docs.oracle.com/javase/7/docs/api/java/io/DataOutputStream.html) API to realize a small calculator client-server application. The client sends an operation name (`add` or `sub`) and two 64 bit long integers to the server. The server computes the result and sends it back. The application uses TCP port 9996 and the client connects to the local host.

This program is also a very simple [client/server](https://en.wikipedia.org/wiki/Client%E2%80%93server_model) application which shows the basic concept of [remote procedure calls](https://en.wikipedia.org/wiki/Remote_procedure_call) (RPC): A client sends the identifier of a method it wishes to be executed, along with its parameters, (marshalled) to an RPC server. The server reads the method it, unmarshalls the parameters, invokes the method, and sends back the results. Of course, this is done by fancy frameworks and all, but the basic idea is this simple.

1. [TCPClientStructuredData.java](http://github.com/thomasWeise/distributedComputingExamples/tree/master/sockets/java/src/TCPClientStructuredData.java)
1. [TCPServerStructuredData.java](http://github.com/thomasWeise/distributedComputingExamples/tree/master/sockets/java/src/TCPServerStructuredData.java)

## 1.7. UDPClientStructuredData / UDPServerStructuredData

As we learn in the lesson, the memory layout of even primitive data types and structures may be different from machine to machine. This example shows how we can send and receive different instances of (primitive) data types via a UDP package with Java. It works basically the same as the TCP example above, just with UDP and port 9997.

We use Java's [`DataInputStream`](http://docs.oracle.com/javase/7/docs/api/java/io/DataInputStream.html)/[`DataOutputStream`](http://docs.oracle.com/javase/7/docs/api/java/io/DataOutputStream.html) API to realize a small calculator client-server application. The client sends an operation name (`add` or `sub`) and two 64 bit long integers to the server. The server computes the result and sends it back. The application uses TCP port 9996 and the client connects to the local host.

1. [UDPClientStructuredData.java](http://github.com/thomasWeise/distributedComputingExamples/tree/master/sockets/java/src/UDPClientStructuredData.java)
1. [UDPServerStructuredData.java](http://github.com/thomasWeise/distributedComputingExamples/tree/master/sockets/java/src/UDPServerStructuredData.java)

## 1.8. MinHTTPServer

A minimum [HTTP](https://en.wikipedia.org/wiki/Hypertext_Transfer_Protocol) Server, i.e., a small [web server](https://en.wikipedia.org/wiki/Web_server). It accepts HTTP reqeuests from normal web browsers and resolves the path part of the requests [URLs](https://en.wikipedia.org/wiki/Uniform_Resource_Locator) against the file system. If it can load the requested file, it will send it as text as answer. Otherwise, it will send an error page (in [HTML](https://en.wikipedia.org/wiki/HTML)) containing the [exception](https://en.wikipedia.org/wiki/Exception_handling) encountered when trying to load the file. This server just sequentially processes client after client at port 9995.

1. [MinHTTPServer.java](http://github.com/thomasWeise/distributedComputingExamples/tree/master/sockets/java/src/MinHTTPServer.java)

## 1.9. MinHTTPClient and MinHTTPClientJava17

A minimum [HTTP](https://en.wikipedia.org/wiki/Hypertext_Transfer_Protocol) clients, i.e., a program that behaves similar to a small web browser (at least, from the [web server](https://en.wikipedia.org/wiki/Web_server)'s perspective). It opens a connection to a web server, sends a HTTP request, and prints the answer. Both in "normal" Java and using Java 1.7's `try-with-resource` statement.

1. [MinHTTPClient.java](http://github.com/thomasWeise/distributedComputingExamples/tree/master/sockets/java/src/MinHTTPClient.java)
1. [MinHTTPClientJava17.java](http://github.com/thomasWeise/distributedComputingExamples/tree/master/sockets/java/src/MinHTTPClientJava17.java)

## 1.10. MinHTTPServerMultiThread

In the above `MinHTTPServer` example, the server processes one client after the other. Everytime, it loads the requested file from the disk and sends it to the client. However, while it is waiting to load a file from disk, it has nothing to do. It could accept another client connection and already read its header. Actually, we could process as many client connections in parallel as our machine allows.

This example here behaves exactly like the `MinHTTPServer`, except that it creates a new [thread](https://en.wikipedia.org/wiki/Thread_%28computer_science%29) which runs in parallel. Each thread then does exactly the same as the old `MinHTTPServer`'s connection processing code.

1. [MinHTTPServerMultiThread.java](http://github.com/thomasWeise/distributedComputingExamples/tree/master/sockets/java/src/MinHTTPServerMultiThread.java)

## 1.11. MinHTTPServerThreadPool

The above example `MinHTTPClientMultiThread` can achieve some good parallelism to process multiple requests. However, it is also wasteful. [Threads](https://en.wikipedia.org/wiki/Thread_%28computer_science%29) are operating system resources and we create one for each connection and afterwards discard it. Additionally, if many connections come in at once, we may create many threads and try to read many files at once, which may slow down our server or cause it to crash.

In this example here, we therefore use a [thread pool](http://docs.oracle.com/javase/tutorial/essential/concurrency/pools.html). A thread pool is a set of threads, let's say 10. Whenever a connection comes in, we submit it as job to the thread pool. The first thread who is not busy doing something else catches this job and processes it in the same way as in the `MinHTTPClientMultiThread` example. While doing so, it is busy. Afterwards, it may process the next job. This means we can process 10 connections in parallel. No threads are discarded and no additional threads are created.

1. [MinHTTPServerThreadPool.java](http://github.com/thomasWeise/distributedComputingExamples/tree/master/sockets/java/src/MinHTTPServerThreadPool.java)

## 1.12. HTTPServerPrintingRequest

A simple sequential [HTTP](https://en.wikipedia.org/wiki/Hypertext_Transfer_Protocol) server which sends back the client's requests as [HTML](https://en.wikipedia.org/wiki/HTML) files. It is single-threaded. Clients are accepted from port `9995` and everything they send is stored in a buffer. This buffer is then sent back inside `<pre>...</pre>` tags with line breaks (`<br/>`) added. 

1. [HTTPServerPrintingRequests.java](http://github.com/thomasWeise/distributedComputingExamples/tree/master/sockets/java/src/HTTPServerPrintingRequests.java)

## 1.13. PiClient/PiServer

A [client/server](https://en.wikipedia.org/wiki/Client%E2%80%93server_model) application which tries to approximate the value of Pi. The clients therefore sample a couple of numbers randomly from the unit square and count how many of them fall into the unit circle. They send both, the number of sampled points and the number of those in the unit circle, to the server. The server manages two such counters as well and updates them with data received from the clients. It can use these numbers to estimate Pi. Many clients can work in parallel and send their numbers to the server. This should increase speed significantly.

Different from the normal client/server structure, the client here does the work. This is somewhat similar to the processing model of [MPI](https://en.wikipedia.org/wiki/Message_Passing_Interface).

1. [PiClient.java](http://github.com/thomasWeise/distributedComputingExamples/tree/master/sockets/java/src/PiClient.java)
1. [PiServer.java](http://github.com/thomasWeise/distributedComputingExamples/tree/master/sockets/java/src/PiServer.java)

[Here](http://github.com/thomasWeise/distributedComputingExamples/tree/master/mpi/piPointToPoint.c) you can find a C implementation for computing Pi in a similar fashion by using the [Message Passing Interface](http://github.com/thomasWeise/distributedComputingExamples/tree/master/mpi/).