# Sockets

## 1. Contents

Here you can find examples on how to use [sockets](https://en.wikipedia.org/wiki/Network_socket).

1. [c](http://github.com/thomasWeise/distributedComputingExamples/tree/master/sockets/c)
2. [java](http://github.com/thomasWeise/distributedComputingExamples/tree/master/sockets/java)


## 2. Introduction

Distributed applications consist of multiple processes usually running on different computers which cooperate with each other. This cooperation is based on the exchange of messages (sent over a communication network). At the lowest level, this network is composed of cables and communication hardware. Above that, we find protocols realized by either software or hardway, divided into different layers. Very close to the hardware is the network layer (e.g., Ethernet), then comes the internet layer (with, e.g., the IP protocol), and the transportation layer with TCP and UDP.

This upper layer is what we usually can access with software: The [sockets API](https://en.wikipedia.org/wiki/Network_socket) is provided by the operating systems and most programming languages. It allows us to access the TCP and UDP protocols.

### 2.1. Client/Server Applications

We use sockets to implement "application layer protocols", i.e., the communication interactions specific to our distributed applications. Such applications are usually divided into a client and a server part. The server part is a process which waits for incoming communication (messages or connections).  Its IP address and port must therefore be known to the client. The client is the part of the application which establishes the communication to the server, usually by sending a request message. The client does not necessarily need to be known by the server in advance: The server will find out who the client is automatically with the incoming message, as the message meta-data provided by the socket API will include the IP address and port of the client. Anyway, the client sends a request to the server. The server can now process the request and send back an answer. This is the most basic interaction we can find in distributed systems. Web servers (server) and web browsers (client) for the world wide web, for instance, fit into this pattern.

### 2.2. Data Representation

When building a distributed applications, just using sockets or communication is not enough, as there are several hidden pitfalls and booby traps. One key problem is that distributed applications may interconnect heterogeneous systems, meaning systems that might differ in hardware, operating system, software, programming language, and configuration/setup.

#### 2.2.1. Endianess

Some hardware represents numbers in the Big Endian format, meaning that the most significant byte (of, say, a four-byte integer) comes at the lowest address in memory. Others use little endian, meaning that the least-significant byte comes first. If we send an integer from a big endian machine to a little endian machine without considering this, the data will get destroyed during the process.

The solution for this and similar problem is marshalling and unmarshalling: Before we send data, we always translate (marshall) it from the format used on our machine into a well-defined and (at least application-wide) agreed-on format. We send the data in this format. Upon receiving a message, we translate (unmarshall) the data into the format required by the current machine. For sending numbers, the common format is the so-called network byte order, which is big endian. Before we send a number, we first translate it to the network byte order. If our machine uses big endian, we need to do nothing. If our machine uses little endian, we reverse the byte order for sending. When we receive a message supposed to contain an integer, we know that it must be in network byte order (big endian). We therefore need to translate it from network byte order into the order used by our machine. If that already is big endian, we are good. Otherwise, i.e., if we use little endian, we copy the number from the message into the memory in reverse order.  

#### 2.2.2. Text

A more complex problem is posed by text. There are many different languages on this planet, and many use their own special characters. As computers only understand bytes and numbers, each character must be represented as number on the computer.

Originally, 7 or 8 bits, i.e., a byte, was more than enough for that: Western languages have usually about 26 letters in upper and lower case, maybe some accented like "é", 10 numerals, some punctuation marks, braces, and special characters like "%" or "$", some mathematical characters like "+" and "-", space (" ") and a few control characters, and that's it. The numbers from 0 to 127 would be enough.

Of course, there are multiple western languages and for each one, we may have a different assignment of numbers to text. This means that if we send text over a network, we must make sure that the receiving knows which assignment was used and how we encoded it. Otherwise, the text may appear as a soup of meaningless characters on the other end - a phenomenon you probably have observed in the web once or twice.

It gets even worse: Asian languages such as Chinese have far more than 256 characters, often several thousands. Then the one-byte-one-character idea breaks.
 [to be continued]

### 2.3. Parallelism