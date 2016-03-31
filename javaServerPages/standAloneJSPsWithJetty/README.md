# Stand-Alone Java Server Pages with Jetty

## 1. Introduction

[Java Server Pages](http://en.wikipedia.org/wiki/JavaServer_Pages) (JSP) are a technology for building dynamic websites with [Java](http://en.wikipedia.org/wiki/Java_(software_platform)). They are basically a mixture of [HTML](http://www.w3.org/html/) and Java code. They run in a special server, a servlet container, which compiles them to [Java Servlets](http://en.wikipedia.org/wiki/Java_Servlet), Java objects with handler routines which are called whenever a client (user) accesses the page via HTTP(S). Thus, in order to get your JSP to run, you deploy it into a servlet container, like [Tomcat](http://tomcat.apache.org/), [Jetty](http://www.eclipse.org/jetty/), or [JBoss](http://www.jboss.org/). This means you have to install the servlet container.

This small project is an example for how you can build a "fat" `jar` which includes both the JSPs *and* the servlet container (Jetty). This `jar` is stand-alone, i.e., you can directly run the `jar` and access the server page without any installation or deployment.

You can compile it with [Maven](http://maven.apache.org/) with goals `compile` and `package`. As result, you will get the `standAloneJSPs-full.jar`, which you can run via `java -jar standAloneJSPs-full.jar` without any additional requirements. It will then start the internal servlet container and then a browser to visit the entry page. In other words, different from our [deployable JSP examples](http://github.com/thomasWeise/distributedComputingExamples/tree/master/javaServerPages/examples), you do not need a servlet container. This is similar to what we did in the [HTTP Proxy Servlet](http://github.com/thomasWeise/distributedComputingExamples/tree/master/javaServlets/proxy) example.

## 2. Building and Deployment

### 2.1. Import Project into Eclipse

If you import this project in [Eclipse](http://www.eclipse.org), it may first show you a lot of errors. (I recommend using Eclipse Mars or later.) This is a Maven project, so you should "update" it first in Eclipse by

1. Make sure that you can see the `package view` on the left-hand side of the Eclipse window.
2. Right-click on the project (`standAloneJSPsWithJetty`) in the `package view`.
3. In the opening pop-up menu, left-click on `Maven`.
4. In the opening sub-menu, left-click on `Update Project...`.
5. In the opening window...
  1. Make sure the project (`standAloneJSPsWithJetty`) is selected.
  2. Make sure that `Update project configuration from pom.xml` is selected.
  3. You can also select `Clean projects`.
  4. Click `OK`.
6. Now the structure of the project in the `package view` should slightly change, the project will be re-compiled, and the errors should disappear.


## 2.2. Build Project in Eclipse

Now you can actually build the project, i.e., generate a [`jar`](https://en.wikipedia.org/wiki/JAR_%28file_format%29) file that you can directly execute on the command line and which contains both your JSPs as well as the (Jetty) servlet container:

1. Make sure that you can see the `package view` on the left-hand side of the Eclipse window.
2. Right-click on the project (`standAloneJSPsWithJetty`) in the `package view`.
3. In the opening pop-up menu, choose `Run As`.
4. In the opening sub-menu choose `Run Configurations...`.
5. In the opening window, choose `Maven Build`
6. In the new window `Run Configurations` / `Create, manage, and run configurations`, choose `Maven Build` in the small white pane on the left side.
7. Click `New launch configuration` (the first symbol from the left on top of the small white pane).
8. Write a useful name for this configuration in the `Name` field. You can use this configuration again later.
9. In the tab `Main` enter the `Base directory` of the project, this is the folder called `javaServerPages/standAloneJSPsWithJetty` containing the Eclipse/Maven project.
10. Under `Goals`, enter `clean compile package`. This will build a `jar` archive.
11. Click `Apply`
12. Click `Run`
13. The build will start, you will see its status output in the console window.
14. The folder `target` will contain a file `standAloneJSPs-full.jar` after the build. This is the executable archive with our application.

## 2.3. Building under Linux without Eclipse

Under Linux, you can also simply run `make_linux.sh` in this project's folder to build the servlet without Eclipse, given that you have Maven installed.

## 3. License

This project is derived from [embedded-jetty-jsp](https://github.com/jetty-project/embedded-jetty-jsp) with copyright (c) 1995-2013 Mort Bay Consulting Pty. Ltd. Compared to that project, the main differences are

1. the "fat" `jar`, making the application stand-alone,
2. the usage of different (newer) versions of the dependencies in the Maven pom
3. a new example servlet
4. with the goal to make the example smaller,
    1. the Main class has been redesigned
    2. packages have been renamed
    3. while other classes and elements from that project have been deleted.
5. a browser is automatically opened and pointed to the entry page of the example

The license of this project is [Apache License v2.0](http://www.opensource.org/licenses/apache2.0.php).