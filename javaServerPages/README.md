# Examples for JavaServer Pages

After we have [learned](http://github.com/thomasWeise/distributedComputingExamples/tree/master/javaServlets/) about [Java Servlets](https://en.wikipedia.org/wiki/Java_Servlet), we know basically how we can build dynamic web applications based on [HTML](http://github.com/thomasWeise/distributedComputingExamples/tree/master/html/), [web forms](https://en.wikipedia.org/wiki/Form_%28HTML%29), [Javascript](https://en.wikipedia.org/wiki/JavaScript), and servlets. In a servlet class, the HTML is contained as String constants. The strings are then written to the output sent to the browser.

Now, this method gets relatively quickly awkward when the size of the website increases. Again, all the HTML contents are string constants in a Java class. Changes become harder and just understanding the page takes a lot of time. If we think about it, often we may have much more HTML code than Java code in a reactive page. Thus, the "inverse" idea would be nice to have: A HTML file containing some Java code, able to reference other Java classes. [JavaServer Pages](https://en.wikipedia.org/wiki/JavaServer_Pages) (JSPs) are just that. HTML (or other text files) which contain Java code.

A JSP is also served by a [servlet container](https://en.wikipedia.org/wiki/Web_container). The first time a JSP is accessed, it is translated to a Java Servlet. The HTML code becomes Strings, the Java (original embedded in the HTML) becomes pure Java code, and some Java code (class declaration and stuff) is wrapped around that. This servlet is then compiled a Java class. This class is then executed exactly like a default servlet.

We can now build applications in a fashion similar to what we would do with [PHP](https://en.wikipedia.org/wiki/PHP) but use the whole stack of technologies we already discussed: servlets, sockets, threads, and Java. Here are our examples for this:

1. [deployable examples](http://github.com/thomasWeise/distributedComputingExamples/tree/master/javaServerPages/examples)
2. [stand-alone JSPs](http://github.com/thomasWeise/distributedComputingExamples/tree/master/javaServerPages/standAloneJSPsWithJetty)

## Licensing

The examples in this repository are licensed under the [GNU GENERAL PUBLIC LICENSE Version 3, 29 June 2007](http://github.com/thomasWeise/distributedComputingExamples/tree/master/LICENSE), with the exception of everything in the directories [/javaServerPages/standAloneJSPsWithJetty](http://github.com/thomasWeise/distributedComputingExamples/tree/master/javaServerPages/standAloneJSPsWithJetty) and [/javaServlets/proxy](http://github.com/thomasWeise/distributedComputingExamples/tree/master/javaServlets/proxy), which re licensed under the [Apache License v2.0](http://www.opensource.org/licenses/apache2.0.php) and are partially derived from project [embedded-jetty-jsp](https://github.com/jetty-project/embedded-jetty-jsp) with copyright (c) 1995-2013 Mort Bay Consulting Pty. Ltd.
