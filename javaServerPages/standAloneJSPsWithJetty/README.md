# Stand-Alone Java Server Pages with Jetty

[Java Server Pages](http://en.wikipedia.org/wiki/JavaServer_Pages) (JSP) are a technology for building dynamic websites with [Java](http://en.wikipedia.org/wiki/Java_(software_platform)). They are basically a mixture of [HTML](http://www.w3.org/html/) and Java code. They run in a special server, a servlet container, which compiles them to [Java Servlets](http://en.wikipedia.org/wiki/Java_Servlet), Java objects with handler routines which are called whenever a client (user) accesses the page via HTTP(S). Thus, in order to get your JSP to run, you deploy it into a servlet container, like [Tomcat](http://tomcat.apache.org/), [Jetty](http://www.eclipse.org/jetty/), or [JBoss](http://www.jboss.org/). This means you have to install the servlet container.

This small project is an example for how you can build a "fat" `jar` which includes both the JSPs *and* the servlet container (Jetty). This `jar` is stand-alone, i.e., you can directly run the `jar` and access the server page without any installation or deployment.

You can compile it with [Maven](http://maven.apache.org/) with goals `compile` and `package`. As result, you will get the `standAloneJSPs-full.jar`, which you can run via `java -jar standAloneJSPs-full.jar` without any additional requirements. It will then start the internal servlet container and then a browser to visit the entry page. In other words, different from our [deployable JSP examples](http://github.com/thomasWeise/distributedComputingExamples/tree/master/javaServerPages/examples), you do not need a servlet container.

## License

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