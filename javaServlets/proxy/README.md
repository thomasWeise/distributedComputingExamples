# HTTP Proxy Implemented Java Servlet inside Embedded Jetty

## 1. Introduction

### 1.1. Java Servlets

[Java Servlets](https://en.wikipedia.org/wiki/Java_Servlet) are an API for accessing the [HTTP protocol](https://en.wikipedia.org/wiki/Hypertext_Transfer_Protocol). A servlet extends the class [HttpServlet](https://docs.oracle.com/javaee/7/api/javax/servlet/http/HttpServlet.html) and implements a handler method for each [HTTP Request Method](https://en.wikipedia.org/wiki/Hypertext_Transfer_Protocol#Request_methods) it supports. The servlet runs in a special server, a servlet container. Here we introduce a Java Servlet which works as a HTTP Proxy server.

### 1.1. HTTP Proxy Server

In this example, we implement a HTTP Servlet which acts as a [HTTP Proxy Server](https://en.wikipedia.org/wiki/Proxy_server). Modern web browsers allow to configure a proxy for HTTP in their network connection settings. Then, the web browser will no longer send its requests to the web servers associated with the [host part](https://en.wikipedia.org/wiki/Uniform_Resource_Locator#Syntax) of the [URLs](https://en.wikipedia.org/wiki/Uniform_Resource_Locator) but instead to that proxy. The proxy will then usually send the requests to the actual web server, receive their answer, and send the answer back to the web browser. This can be used to realize caching, to prevent, limit, or monitor internet access, as well as to protect the privacy of the users. Roughly, the communication looks as follows:

    1. Communication without Proxy
     __________                                                          ________
     |         |                                                        |        |
     | web     | --------------------- HTTP Request ------------------> | web    |
     |         |                                                        |        |
     | browser | <-------------------- HTTP Response ------------------ | server |
     |         |                                                        |        |
     -----------                                                        ----------

     
    2. Communication with Proxy    
     __________                        _________                         ________
     |         | --- HTTP Request ---> |        |                       |        |
     | web     |                       | HTTP   | --- HTTP Request ---> | web    |
     |         |    "Connection A"     | proxy  |    "Connection B"     |        |
     | browser |                       | server | <-- HTTP Response --- | server |
     |         | <-- HTTP Response --- |        |                       |        |
     -----------                       ----------                       ----------
     
A HTTP proxy server therefore needs to do the following things:

1. For each incoming HTTP connection `A` (from a client such as a web browser),

   1. (re)construct the original URL that the client originally wanted, including
   2. the "parameters" of the URL, if any, i.e., the [query string](https://en.wikipedia.org/wiki/Query_string) and
   3. read the [HTTP header fields](https://en.wikipedia.org/wiki/List_of_HTTP_header_fields) and adapt them to what our proxy can support (maybe we cannot allow [persistent connections](https://en.wikipedia.org/wiki/HTTP_persistent_connection)), as in this example)
   
2. If the request cannot be satisfied directly, open a new HTTP connection `B` to the host indicated by the original URL and
   
   1. send the query from the original URL and
   2. forward the HTTP header fields from the original request coming from the browser (which is necessary to include, for instance, [cookies](https://en.wikipedia.org/wiki/HTTP_cookie) needed to enable sessions)
   
3. From the connection `B` read the answer, including  

   1. the [HTTP status code](https://en.wikipedia.org/wiki/List_of_HTTP_status_codes),
   2. the [HTTP header fields](https://en.wikipedia.org/wiki/List_of_HTTP_header_fields), and
   3. the [message body](https://en.wikipedia.org/wiki/HTTP_message_body), i.e., the actual resource requested by the browser in step 1, e.g., a HTML page or an image
  
4. Then it will forward these elements read from connection `B` via connection `A` back to the client, i.e.,

  1. the HTTP status code,
  2. the HTTP header fields (this includes, e.g., cookies sent from the server to web browser to establish a session), and
  3. the actual message body

This way, the proxy server can act more or less transparently: After the browser is configured to send its request to the proxy instead of the actual hosts, all further HTTP interactions take place entirely unchanged and in the same way as if there was no proxy.

## 2. Java Servlet as Proxy

### 2.1. Connection A

In Java, the Java Servlet API takes the burden of HTTP interaction from us - for the connection called `A` in the above discussion. A servlet container can accept *incoming* HTTP connections, read all the header fields and query parameters for us, and allows us to send back a HTTP response for which we can again set arbitrary header fields, status codes, and send arbitrary contents.

### 2.2. Connection B

This leaves us to establish another *(outgoing)* connection from our proxy to the actual host identified by the URL and to acquire the resource the client (web browser) was actually looking for. For this, we can use the class [`URLConnection`](http://docs.oracle.com/javase/7/docs/api/java/net/URLConnection.html) provided by standard Java, which can be instantiated via an [`URL`](http://docs.oracle.com/javase/7/docs/api/java/net/URL.html) object. This class allows us to open a reading connection to the host identified by the host part of a URL and to query the resource targeted by the URL without much hassle. We can also set header fields for the HTTP request to be send and read header fields from the HTTP response received. If the URL [uses the HTTP protocol](https://en.wikipedia.org/wiki/URI_scheme#Syntax) (as it should, after all, we implement a HTTP proxy), the URL object will actually create an instance of [HttpURLConnection](http://docs.oracle.com/javase/7/docs/api/java/net/HttpURLConnection.html). `HttpURLConnection` is a sub-class of `URLConnection` which allows us to additionally read the HTTP status code from the HTTP response (coming back from the actual host).

As we need to construct the original query URL anyway, we might as well do it as instance of the `URL` class. Then we can use the provided `HttpURLConnection` and can fulfill all the immediate necessities of communication with the host hosting the requested resource. Hence, together with the elements from the previous section, we can build all the required functionality.

### 2.3. Building the Proxy

We now can build a servlet that implements all necessary proxy functionality. This servlet will be able to accept an incoming connection (from a web browser), read the query parameters and HTTP header fields, forward them appropriately to the actually queried host, and send back that host's answer to the web browser:

1. Connection `A`: The web browser opens a TCP connection to the servlet container (instead of its real target host). Over this TCP connection, it sends a HTTP request. The servlet container translates the request to appropriate objects and invokes the corresponding handler methods of our Java servlet.
2. Connection `B`: Our servlet reconstructs the URL that the browser has requested. Using the `URL` object, we instantiate an `URLConnection` (which actually is a `HttpURLConnection`). We set all the header fields which the browser has sent in the original request from connection `A` also for this connection (except stuff like `keep-alive` for connections and other things we cannot support). We open the new connection `B`, which means we establish a TCP connection to the real server and send the new HTTP request to that server.
3. The server answers us and sends a HTTP response back via connection `B` to our proxy. Our proxy reads the HTTP status code, all header fields, and the response body, i.e., the actual web page or resource sent to us.
4. In the response of our servlet, we use the object `HttpServletResponse` provided by the servlet container to forward all header fields and the HTTP status code and the HTTP response body coming from the actual server back to the web browser. 

We would configure this servlet to serve at the base URI of the servlet container, i.e., directly at URL `/`. Then we can specify the IP address and port as HTTP proxy of the servlet container in our web browsers. If the proxy runs on our local machine, this could be `127.0.0.1:8080`, for instance.

### 2.4. Make it Stand-Alone!

Normally, in order to get your servlet to run, you deploy it into a servlet container, like [Tomcat](http://tomcat.apache.org/), [Jetty](http://www.eclipse.org/jetty/), or [JBoss](http://www.jboss.org/). This means you have to install the servlet container. But using a servlet container with its architecture of servlet deployment and all seems a bit too much and too complex here.

This small project is an example for how you can build a "fat" `jar` which includes both the (proxy) servlet *and* the servlet container (Jetty). This `jar` is stand-alone, i.e., you can directly run the `jar` and access the proxy without any installation or deployment.

You can compile it with [Maven](http://maven.apache.org/) with goals `compile` and `package`. As result, you will get the `proxy-full.jar`, which you can run via `java -jar proxy-full.jar` without any additional requirements. It will then start the internal servlet container and the proxy servlet. In other words, different from our [deployable Java Servlet examples](http://github.com/thomasWeise/distributedComputingExamples/tree/master/javaServlets/examples), you do not need a servlet container. This is similar to the [stand-alone JSPs](http://github.com/thomasWeise/distributedComputingExamples/tree/master/javaServerPages/standAloneJSPsWithJetty) which comes later in the lessons.

## 3. Building and Deployment

### 3.1. Import Project into Eclipse

If you import this project in [Eclipse](http://www.eclipse.org), it may first show you a lot of errors. (I recommend using Eclipse Mars or later.) This is a Maven project, so you should "update" it first in Eclipse by

1. Make sure that you can see the `package view` on the left-hand side of the Eclipse window.
2. Right-click on the project (`proxy`) in the `package view`.
3. In the opening pop-up menu, left-click on `Maven`.
4. In the opening sub-menu, left-click on `Update Project...`.
5. In the opening window...
  1. Make sure the project (`proxy`) is selected.
  2. Make sure that `Update project configuration from pom.xml` is selected.
  3. You can also select `Clean projects`.
  4. Click `OK`.
6. Now the structure of the project in the `package view` should slightly change, the project will be re-compiled, and the errors should disappear.

## 3.2. Build Project in Eclipse

Now you can actually build the project, i.e., generate a [`jar`](https://en.wikipedia.org/wiki/JAR_%28file_format%29) file that you can directly execute on the command line and which contains both the proxy servlet as well as the (Jetty) servlet container:

1. Make sure that you can see the `package view` on the left-hand side of the Eclipse window.
2. Right-click on the project (`proxy`) in the `package view`.
3. In the opening pop-up menu, choose `Run As`.
4. In the opening sub-menu choose `Run Configurations...`.
5. In the opening window, choose `Maven Build`
6. In the new window `Run Configurations` / `Create, manage, and run configurations`, choose `Maven Build` in the small white pane on the left side.
7. Click `New launch configuration` (the first symbol from the left on top of the small white pane).
8. Write a useful name for this configuration in the `Name` field. You can use this configuration again later.
9. In the tab `Main` enter the `Base directory` of the project, this is the folder called `javaServlets/proxy` containing the Eclipse/Maven project.
10. Under `Goals`, enter `clean compile package`. This will build a `jar` archive.
11. Click `Apply`
12. Click `Run`
13. The build will start, you will see its status output in the console window.
14. The folder `target` will contain a file `proxy-full.jar` after the build. This is the executable archive with our application.

## 3.3. Building under Linux without Eclipse

Under Linux, you can also simply run `make_linux.sh` in this project's folder to build the servlet without Eclipse, given that you have Maven installed.

## 4. Running the Proxy

You can start the proxy servlet via the command

    java -jar proxy-full.jar
    
Then, the proxy server will start up and begin to listen at port `8080` for incoming requests.
This works both under Linux and Windows, but it requires you keep the terminal/command line window open.

### 4.1. Running the Proxy in Background under Linux

Under Linux, you can start the proxy in the background by typing the following command

    java -jar proxy-full.jar > /dev/null 2>&1 &
  
You can find this line implemented in the file `launch_linux.sh`. Just run this file and the proxy server will start in the background.
  
### 4.2. Running the Proxy in Background under Windows

Under Windows, it is slightly more complicated to get a command line application to run in the background. But we actually do not need to do that: Under windows we can just run the `jar` directly by opening it. In the emerging dialog you need to grant internet access to the application.

If that is too easy for you, you can execute the following [VBScript](https://en.wikipedia.org/wiki/VBScript):

    Dim WinScriptHost
    Set WinScriptHost = CreateObject("WScript.Shell")
    WinScriptHost.Run "java -jar target\proxy-full.jar", 0
    Set WinScriptHost = Nothing

You can find it implemented as `launch_windows.vbs` in the project root folder.

## 5. Setting Up HTTP Proxies in Web Browser

For the following setup guides, let us assume that the proxy is running on the local computer, i.e., under IP address `127.0.0.1`. It will use port `8080`. If it is running on a different machine, use the IP address of that machine instead.

### 5.1. Firefox

In the [Firefox](https://en.wikipedia.org/wiki/Firefox_%28browser%29) web browser, you set up the HTTP proxy as follows:

1. Click the "Open Menu" icon  looking like three horizontal bars in the top-right corner of the window.
2. Click "Preferences" in the opening icon field near the right side of the window.
3. Click "Advanced" in the left selection pane of the newly opening "Preferences" browser tab.
4. Click the "Network" tab inside the "Preferences" browser tab.
5. The first line reads "Connection", click "Settings..."
6. In the newly opening dialog, choose "Manual Proxy Configuration".
7. In the now-enabled controls, enter `127.0.0.1` at the "HTTP Proxy:" field and `8080` into the field `Port:`.
8. Click "OK"
9. Close the "Preferences" browser tab.

Another guide can be found at [http://www.wikihow.com/Enter-Proxy-Settings-in-Firefox](http://www.wikihow.com/Enter-Proxy-Settings-in-Firefox).

### 5.2. Internet Explorer

In the [Internet Explorer](https://en.wikipedia.org/wiki/Internet_Explorer) web browser, you set up the HTTP proxy as follows:

1. Click the small gear wheel icon for "Tools" in the top-right corner of the window or press `Alt-X`.
2. Click "Internet Options" in the unfolding menu (or simply press `o`).
3. In the newly opening dialog "Internet Options", choose the "Connections" tab.
4. Click the "LAN Settings" button in the bottom half of the tab.
5. In the newly opening dialog "Local Area Network (LAN) Settings" in the lower half there is an option group called "Proxy server". Check the box "Use a proxy server for your LAN [...]".
6. In the `Address:` field, enter `127.0.0.1` and in the field `Port:` enter `8080`.
7. Click "OK" (and the dialog "Local Area Network (LAN) Settings" closes).
8. Click OK in the "Internet Options" dialog and it closes.


## 6. License

This work has purely educational purposes. Besides everything mentioned below, for anything in this repository and project, I impose one additional licensing condition: The code must never be used for anything which might violate the laws of Germany, China, or the USA. This also holds for any other file or resource provided here.

The embedded usage of the Jetty server here is derived from [embedded-jetty-jsp](https://github.com/jetty-project/embedded-jetty-jsp) with copyright (c) 1995-2013 Mort Bay Consulting Pty. Ltd. Compared to that project, the main differences are

1. our `jar` runs a Java Servlet, not JavaServer Pages
2. the "fat" `jar`, making the application stand-alone,
3. the usage of different (newer) versions of the dependencies in the Maven pom
4. entirely different application, the `embedded-jetty-jsp` has nothing to do with proxies at all
5. with the goal to also make the example smaller,
    1. the Main class has been redesigned
    2. packages have been renamed
    3. while other classes and elements from that project have been deleted,
    4. new classes have been added as well.

The license of this project is [Apache License v2.0](http://www.opensource.org/licenses/apache2.0.php).