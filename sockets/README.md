# Sockets

## 1. Contents

Here you can find examples on how to use [sockets](https://en.wikipedia.org/wiki/Network_socket).

1. [c](http://github.com/thomasWeise/distributedComputingExamples/tree/master/sockets/c)
2. [java](http://github.com/thomasWeise/distributedComputingExamples/tree/master/sockets/java)


## 2. Introduction

Distributed applications consist of multiple processes usually running on different computers which cooperate with each other. This cooperation is based on the exchange of messages (sent over a communication network). Communication involves several independent aspects which are separated in different [protocol layers](https://en.wikipedia.org/wiki/Internet_protocol_suite#Abstraction_layers). At the lowest level, this network is composed of cables and communication hardware. Above that, we find protocols realized by either software or hardware, divided into different layers. Very close to the hardware is the [network layer](https://en.wikipedia.org/wiki/Link_layer) (with, e.g., [Ethernet](https://en.wikipedia.org/wiki/Ethernet)), then comes the [internet layer](https://en.wikipedia.org/wiki/Internet_layer) (with, e.g., the [Internet Protocol](https://en.wikipedia.org/wiki/Internet_Protocol)), and the [transport layer](https://en.wikipedia.org/wiki/Internet_protocol_suite#Transport_layer) with [TCP](https://en.wikipedia.org/wiki/Transmission_Control_Protocol) and [UDP](https://en.wikipedia.org/wiki/User_Datagram_Protocol).

This upper layer is what we usually can access with software: The [sockets API](https://en.wikipedia.org/wiki/Network_socket) is provided by the operating systems and most programming languages. It allows us to access the TCP and UDP protocols.

### 2.1. Client/Server Applications

We use sockets to implement "application layer protocols", i.e., the communication interactions specific to our distributed applications. Such applications are usually divided into a [client and a server](https://en.wikipedia.org/wiki/Client%E2%80%93server_model) part. The server part is a process which waits for incoming communication (messages or connections). Its [IP address](https://en.wikipedia.org/wiki/IP_address) and [port](https://en.wikipedia.org/wiki/Port_%28computer_networking%29) must therefore be known to the client. The client is the part of the application which establishes the communication to the server, usually by sending a request message. The client does not necessarily need to be known by the server in advance: The server will find out who the client is automatically with the incoming message, as the message meta-data provided by the socket API will include the IP address and port of the client. Anyway, the client sends a request to the server. The server can now process the request and send back an answer. This is the most basic interaction we can find in distributed systems. [Web servers](https://en.wikipedia.org/wiki/Web_server) (server) and [web browsers](https://en.wikipedia.org/wiki/Web_browser) (client) for the [world wide web](https://en.wikipedia.org/wiki/World_Wide_Web), for instance, fit into this pattern.

### 2.2. Data Representation

When building a distributed applications, just using sockets or communication is not enough, as there are several hidden pitfalls and booby traps. One key problem is that distributed applications may interconnect heterogeneous systems, meaning systems that might differ in hardware, operating system, software, programming language, and configuration/setup.

#### 2.2.1. Endianess

Some hardware represents numbers in the [Big Endian](https://en.wikipedia.org/wiki/Endianness) format, meaning that the most significant byte (of, say, a four-byte integer) comes at the lowest address in memory. Others use little endian, meaning that the least-significant byte comes first. If we send an integer from a big endian machine to a little endian machine without considering this, the data will get destroyed during the process.

The solution for this and similar problem is [marshalling and unmarshalling](https://en.wikipedia.org/wiki/Marshalling_%28computer_science%29): Before we send data, we always translate (marshall) it from the format used on our machine into a well-defined and (at least application-wide) agreed-on format. We send the data in this format. Upon receiving a message, we translate (unmarshall) the data into the format required by the current machine. For sending numbers, the common format is the so-called network byte order, which is big endian. Before we send a number, we first translate it to the network byte order. If our machine uses big endian, we need to do nothing. If our machine uses little endian, we reverse the byte order for sending. When we receive a message supposed to contain an integer, we know that it must be in network byte order (big endian). We therefore need to translate it from network byte order into the order used by our machine. If that already is big endian, we are good. Otherwise, i.e., if we use little endian, we copy the number from the message into the memory in reverse order.  

#### 2.2.2. Text

A more complex problem is posed by text. There are many different languages on this planet, and many use their own special characters. As computers only understand bytes and numbers, each character must be represented as number on the computer.

Originally, [7 or 8 bits](https://en.wikipedia.org/wiki/American_Standard_Code_for_Information_Interchange), i.e., a byte, was more than enough for that: Western languages have usually about 26 letters in upper and lower case, maybe some accented like "é", 10 numerals, some punctuation marks, braces, and special characters like "%" or "$", some mathematical characters like "+" and "-", space (" ") and a few control characters, and that's it. The numbers from 0 to 127 would be enough.

Of course, there are multiple western languages and for each one, we may have a different assignment of numbers to text. This means that if we send text over a network, we must make sure that the receiving knows which [assignment](https://en.wikipedia.org/wiki/Code_page#IBM_PC_.28OEM.29_code_pages) was used and how we encoded it. Otherwise, the text may appear as a soup of meaningless characters on the other end - a phenomenon you probably have observed in the web once or twice.

It gets even worse: Asian languages such as Chinese have far more than 256 characters, often several thousands. Then the one-byte-one-character idea breaks. [Two](https://en.wikipedia.org/wiki/DBCS) or [more](https://en.wikipedia.org/wiki/Multi-byte_character_set) bytes need to be used per character.

Today, [Unicode](https://en.wikipedia.org/wiki/Unicode) assigns one unique number to virtually all characters used anywhere on earth. Via the [UTF-8](https://en.wikipedia.org/wiki/UTF-8), these numbers can efficiently be transformed to variable-length sequences of bytes. This is the [predominant encoding](https://en.wikipedia.org/wiki/File:UnicodeGrow2b.png) now found in the internet.

### 2.3. Parallelism

Using sockets and being able to properly represent data are the foundations needed to implement correctly working web applications. However, we want these applications to be efficient and fast. Creating a server which, in a loop, accepts an incoming connection, processes it, closes it, then accepts the next one, and so on, will not give us that. Processing a single connection at a time is wasteful for two reasons:

1. Most computers today have multi-core CPUs or hyperthreadding. If we only have a single main loop doing all the work, we use one thread on one core. We waste the chance for parallelizing tasks.
2. Often, processing an connection may require us to perform additional IO or blocking operations. Maybe we need to read a file and send its contents back via the connection to the client. Such a blocking interaction with the operating system means that we wait for an operation to complete and effectively do nothing while waiting. We could instead already process another connection.

Using multiple threads is an answer. A process, such as our server, may be considered as a container for threads. Usually, such a process has one single main thread. But we can create more threads. All the threads in a process run quasi-parallel and share the same resources. For each incoming connection, we could create a new thread processing this connection. Hence, all connections would be processed in parallel.

This makes the server faster, but, too, is wasteful. First of all, if many users connect at once, we may create many many threads. Since they all share the same actual CPU resources, the fraction of runtime per thread will decrease. Our system gets slower. Second, threads are operating system resources. Creating them takes time and consumes memory. Creating a new thread for each incoming connection and throwing it away after the connection was closed is thus wasteful.

Thread pools are the answer: We create a fixed set of (worker) threads when our server starts. The main loop does no longer process incoming connections directly, but enters them into a queue. If one of worker threads is idle, i.e., has nothing to do, it will try to extract a new connection to be processed from the queue. If the queue was not empty and it got such a new job, it will process the job. After finishing it, it becomes idle again.

Since the number of threads is limited, we cannot run out of memory or resources due to too many connections because of the thread creation, at least. Also, we avoid that too many threads compete for the CPU, as the number of threads is limited. Finally, since we re-use threads again and again instead of creating one for each connection and disposing it afterwards, we also become generally more resource friendly and efficient.