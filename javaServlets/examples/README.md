# Java Servlets Examples

Here you can find the example [Java Servlets](https://en.wikipedia.org/wiki/Java_Servlet) from my distributed computing class. The project is built using [Maven](http://maven.apache.org/).

## 1. Examples

### 1.1. HelloWorldServlet

This small servlet accepts an incoming HTTP request and sends back a `Hello World` message in `text/plain` format.

If your application is running in GlassFish, you can access this servlet as [http://localhost:8080/myServlets/HelloWorld](http://localhost:8080/myServlets/HelloWorld).

1. [`HelloWorldServlet`](http://github.com/thomasWeise/distributedComputingExamples/tree/master/javaServlets/examples/src/main/java/myServlets/HelloWorldServlet.java)

### 1.2. RequestDataServlet

This servlet is similar to our [MinHTTPServer.java](http://github.com/thomasWeise/distributedComputingExamples/tree/master/sockets/java/src/MinHTTPServer.java) example in the Java [sockets](http://github.com/thomasWeise/distributedComputingExamples/tree/master/sockets/java/) example set. It accepts an incoming HTTP request and dynamically creates a `text/html` formatted response (i.e., a web page). This web page contains all the request's data, such as the client address, all request headers, cookies, and dynamic query parameters (such as form data).

If your application is running in GlassFish, you can access this servlet as [http://localhost:8080/myServlets/RequestData](http://localhost:8080/myServlets/RequestData).

1. [`RequestDataServlet`](http://github.com/thomasWeise/distributedComputingExamples/tree/master/javaServlets/examples/src/main/java/myServlets/RequestDataServlet.java)

### 1.3. SessionDataServlet

This servlet shows how sessions can be handled with Java Servlets. It accepts an incoming connection and dynamically creates a `text/html` formatted response (i.e., a web page). This web page contains all the information of (and stored in) the current session. The first time you access this servlet, there is no current session, so it is created. This session initially has no information stored in it in form of attributes. However, this servlet adds some attributes to the session. The next time you access this servlet, these attributes will be printed. Hence, sessions are a way to preserve information between HTTP requests to a servlet.

If your application is running in GlassFish, you can access this servlet as [http://localhost:8080/myServlets/SessionData](http://localhost:8080/myServlets/SessionData).

1. [`SessionDataServlet`](http://github.com/thomasWeise/distributedComputingExamples/tree/master/javaServlets/examples/src/main/java/myServlets/SessionDataServlet.java)

## 2. Building and Deployment

### 2.1. Import Project into Eclipse

If you import this project in [Eclipse](http://www.eclipse.org), it may first show you a lot of errors. (I recommend using Eclipse Mars or later.) This is a Maven project, so you should "update" it first in Eclipse by

1. Make sure that you can see the `package view` on the left-hand side of the Eclipse window.
2. Right-click on the project (`JavaServlets`) in the `package view`.
3. In the opening pop-up menu, left-click on `Maven`.
4. In the opening sub-menu, left-click on `Update Project...`.
5. In the opening window...
  1. Make sure the project (`JavaServlets`) is selected.
  2. Make sure that `Update project configuration from pom.xml` is selected.
  3. You can also select `Clean projects`.
  4. Click `OK`.
6. Now the structure of the project in the `package view` should slightly change, the project will be re-compiled, and the errors should disappear.


### 2.2. Build Project in Eclipse

Now you can actually build the project, i.e., generate a [`war`](https://en.wikipedia.org/wiki/WAR_file_format_%28Sun%29) file that you can deploy in a [servlet container](https://en.wikipedia.org/wiki/Web_container). For building this `war`, take the following steps:

1. Make sure that you can see the `package view` on the left-hand side of the Eclipse window.
2. Right-click on the project (`JavaServlets`) in the `package view`.
3. In the opening pop-up menu, choose `Run As`.
4. In the opening sub-menu choose `Run Configurations...`.
5. In the opening window, choose `Maven Build`
6. In the new window `Run Configurations` / `Create, manage, and run configurations`, choose `Maven Build` in the small white pane on the left side.
7. Click `New launch configuration` (the first symbol from the left on top of the small white pane).
8. Write a useful name for this configuration in the `Name` field. You can use this configuration again later.
9. In the tab `Main` enter the `Base directory` of the project, this is the folder called `javaServlets` containing the Eclipse/Maven project.
10. Under `Goals`, enter `clean compile war:war`. This will build a `war` archive.
11. Click `Apply`
12. Click `Run`
13. The build will start, you will see its status output in the console window.
14. The folder `target` will contain a file `myServlets.war` after the build. This is the deployable archive with our application.

### 2.3. Building under Linux without Eclipse

Under Linux, you can also simply run `make_linux.sh` in this project's folder to build the servlet without Eclipse, given that you have Maven installed.

### 2.4. Deploy

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

1. Find the file `myServlets.war` in the folder `target` of this example project.
2. Copy it into the folder `F/glassfish4/glassfish/domains/domain1`.
3. In your web browser, visit [`http://localhost:4848`](http://localhost:4848).
4. Click `Applications` in the menu pane on the right.
5. It should list `myServlets`. If you click this menu point, you get a list of the installed servlets.
6. You can access the servlets as follows

  1. [http://localhost:8080/myServlets/HelloWorld](http://localhost:8080/myServlets/HelloWorld)
  2. [http://localhost:8080/myServlets/RequestData](http://localhost:8080/myServlets/RequestData)
  3. [http://localhost:8080/myServlets/SessionData](http://localhost:8080/myServlets/SessionData)
  
  
## 3. Stand-alone Java Servlets?

In the [stand-alone JSPs](http://github.com/thomasWeise/distributedComputingExamples/tree/master/javaServerPages/standAloneJSPsWithJetty) example in the [JavaServer Pages](http://github.com/thomasWeise/distributedComputingExamples/tree/master/javaServerPages/) example set, I describe how stand-alone JavaServer Pages (JSPs) can be built with Jetty. These are JSPs which do not need to be deployed but where the whole application server is packaged into one `jar` archive also including the JSPs. You just need to start this `jar` to get the application running. Of course, you can modify this example to serve Java Servlets instead rather easily
