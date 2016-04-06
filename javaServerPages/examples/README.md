# JavaServer Pages Deployable Examples

Here you can find the deployable example [JavaServer Pages](https://en.wikipedia.org/wiki/JavaServer_Pages) (JSPs) from my distributed computing class. The project is built using [Maven](http://maven.apache.org/).

## 1. Examples

### 1.1. Greetings `greetings.jsp`

This small JSP creates a [HTML](https://en.wikipedia.org/wiki/HTML) page printing some greetings and the clock time. Depending on the current time on the server, the greeting will be "Good Morning!" or "Good Evening!".

If your application is running in GlassFish, you can access this JSP as [http://localhost:8080/myJSPs/greetings.jsp](http://localhost:8080/myJSPs/greetings.jsp).

1. [`greetings.jsp`](http://github.com/thomasWeise/distributedComputingExamples/tree/master/javaServerPages/examples/src/main/webapp/greetings.jsp)

### 1.2. Print Client Address: `printClientAddress.jsp`

This small JSP creates a plain text response containing the [IP address](https://en.wikipedia.org/wiki/IP_address) of the client.

If your application is running in GlassFish, you can access this JSP as [http://localhost:8080/myJSPs/printClientAddress.jsp](http://localhost:8080/myJSPs/printClientAddress.jsp).

1. [`printClientAddress.jsp`](http://github.com/thomasWeise/distributedComputingExamples/tree/master/javaServerPages/examples/src/main/webapp/printClientAddress.jsp)

### 1.3. Count Web Page Visits (unsynchronized): `counterUnsynchronized.jsp`

This JSP counts the number of times it was visited. It does so by creating a `long` member variable named `numVisitors`. Whenever the servlet is loaded, this variable will be incremented by one and the result is printed. This incrementing and printing step is not protected with [synchronization](http://docs.oracle.com/javase/tutorial/essential/concurrency/sync.html). It is not an [atomic operation](https://en.wikipedia.org/wiki/Linearizability). This means, the results can potentially become inconsistent. At the same time, this [critical section](https://en.wikipedia.org/wiki/Critical_section) is extremely small and executes extremely fast. This means that the error is virtually impossible to reproduce and to find. If such a programming mistake is made, it might only be discovered in production and would be extremely hard to identify.

If your application is running in GlassFish, you can access this JSP as [http://localhost:8080/myJSPs/counterUnsynchronized.jsp](http://localhost:8080/myJSPs/counterUnsynchronized.jsp).

1. [`counterUnsynchronized.jsp`](http://github.com/thomasWeise/distributedComputingExamples/tree/master/javaServerPages/examples/src/main/webapp/counterUnsynchronized.jsp)

### 1.4. Count Web Page Visits (synchronized): `counterSynchronized.jsp`

This JSP counts the number of times it was visited, but avoids the synchronization problem from the previous example above. It again creates a `long` member variable named `numVisitors`. This counter is increased by the private `synchronized` method `long __inc()`. This fixes the previous error.

If your application is running in GlassFish, you can access this JSP as [http://localhost:8080/myJSPs/counterSynchronized.jsp](http://localhost:8080/myJSPs/counterSynchronized.jsp).

1. [`counterSynchronized.jsp`](http://github.com/thomasWeise/distributedComputingExamples/tree/master/javaServerPages/examples/src/main/webapp/counterSynchronized.jsp)

### 1.5. Rate this Course: `rating.jsp`

This JSP includes a small form which allows you to rate the distributed computing course. You can choose between six different ratings. Clicking the submit button will send your chosen rating back to the same page. The internal counters will be updated in a synchronized fashion (see example 1.4 above). The response page of the rating contains a section displaying the ratings received so far. This example can therefore be used to get a rough impression on how the data from web forms is sent to a server and how the server can process it.

If your application is running in GlassFish, you can access this JSP as [http://localhost:8080/myJSPs/rating.jsp](http://localhost:8080/myJSPs/rating.jsp).

1. [`rating.jsp`](http://github.com/thomasWeise/distributedComputingExamples/tree/master/javaServerPages/examples/src/main/webapp/rating.jsp)

### 1.6. Shopping Cart: `shoppingCart.jsp`

This JSP is an example for [session](https://en.wikipedia.org/wiki/Session_(computer_science)) data. A session is enriched with a [JavaBean](https://en.wikipedia.org/wiki/JavaBeans) named `cart` bound to class `shopping.Cart`. This bean basically holds a list of items in the user's shopping cart. The `shoppingCart.jsp` loads the bean, displays the contents of the shopping cart, and *includes* another JSP named `shoppingCartForm.jsp` which displays a form that allows us to add items to or remove items from the shopping cart. It therefore creates a form which, upon submission, sends it data back to the hosting JSP (`shoppingCart.jsp`). This data includes the parameter `submit`, which is either `add` or `remove` (or `null`, in which case nothing needs to be done) and the parameter `item`, representing the item to be added or removed. The `cart` bean therefore has read-only properties `add` and `remove` which receive the value of `item` and update the internal list, i.e., the content of the user's shopping cart. If we open this JSP from multiple different browsers, each will have an own, unique session and, hence, an own, unique instance of `shopping.Cart`.

If your application is running in GlassFish, you can access this JSP as [http://localhost:8080/myJSPs/shoppingCart.jsp](http://localhost:8080/myJSPs/shoppingCart.jsp).

1. [`shoppingCart.jsp`](http://github.com/thomasWeise/distributedComputingExamples/tree/master/javaServerPages/examples/src/main/webapp/shoppingCart.jsp)
2. [`shoppingCartForm.jsp`](http://github.com/thomasWeise/distributedComputingExamples/tree/master/javaServerPages/examples/src/main/webapp/shoppingCartForm.jsp)
3. [`shopping.Cart.java`](http://github.com/thomasWeise/distributedComputingExamples/tree/master/javaServerPages/examples/src/main/java/shopping/Cart.java)

### 1.7. Expression Language Examples: `expressionLanguageExamples.jsp`

This JSP includes a set of small examples for the expression language [EL](https://en.wikipedia.org/wiki/Unified_Expression_Language) which you can use since JSPs 2.1.

If your application is running in GlassFish, you can access this JSP as [http://localhost:8080/myJSPs/expressionLanguageExamples.jsp](http://localhost:8080/myJSPs/expressionLanguageExamples.jsp).

1. [`expressionLanguageExamples.jsp`](http://github.com/thomasWeise/distributedComputingExamples/tree/master/javaServerPages/examples/src/main/webapp/expressionLanguageExamples.jsp)

### 1.8. JSTL Examples: `jstlExamples.jsp`

This JSP includes a set of small examples for the JavaServer Pages Standard Tag Library [JSTL](https://en.wikipedia.org/wiki/JavaServer_Pages_Standard_Tag_Library) which you can use since 2006.

If your application is running in GlassFish, you can access this JSP as [http://localhost:8080/myJSPs/jstlExamples.jsp](http://localhost:8080/myJSPs/jstlExamples.jsp).

1. [`jstlExamples.jsp`](http://github.com/thomasWeise/distributedComputingExamples/tree/master/javaServerPages/examples/src/main/webapp/jstlExamples.jsp)

### 1.9. Shopping Cart with JSTL and EL: `shoppingCartJSTLEL.jsp`

This is basically the same example as example 1.6, the session-based shopping cart. However, now we make use of both the JSTL and EL to simplify the example.

If your application is running in GlassFish, you can access this JSP as [http://localhost:8080/myJSPs/shoppingCartJSTLEL.jsp](http://localhost:8080/myJSPs/shoppingCartJSTLEL.jsp).

1. [`shoppingCartJSTLEL.jsp`](http://github.com/thomasWeise/distributedComputingExamples/tree/master/javaServerPages/examples/src/main/webapp/shoppingCartJSTLEL.jsp)
2. [`shoppingCartForm.jsp`](http://github.com/thomasWeise/distributedComputingExamples/tree/master/javaServerPages/examples/src/main/webapp/shoppingCartForm.jsp)
3. [`shopping.Cart.java`](http://github.com/thomasWeise/distributedComputingExamples/tree/master/javaServerPages/examples/src/main/java/shopping/Cart.java)


## 2. Building and Deployment

### 2.1. Import Project into Eclipse

If you import this project in [Eclipse](http://www.eclipse.org), it may first show you a lot of errors. (I recommend using Eclipse Mars or later.) This is a Maven project, so you should "update" it first in Eclipse by

1. Make sure that you can see the `package view` on the left-hand side of the Eclipse window.
2. Right-click on the project (`JavaServer Pages`) in the `package view`.
3. In the opening pop-up menu, left-click on `Maven`.
4. In the opening sub-menu, left-click on `Update Project...`.
5. In the opening window...
  1. Make sure the project (`JavaServer Pages`) is selected.
  2. Make sure that `Update project configuration from pom.xml` is selected.
  3. You can also select `Clean projects`.
  4. Click `OK`.
6. Now the structure of the project in the `package view` should slightly change, the project will be re-compiled, and the errors should disappear.


### 2.2. Build Project in Eclipse

Now you can actually build the project, i.e., generate a [`war`](https://en.wikipedia.org/wiki/WAR_file_format_%28Sun%29) file that you can deploy in a [servlet container](https://en.wikipedia.org/wiki/Web_container). For building this `war`, take the following steps:

1. Make sure that you can see the `package view` on the left-hand side of the Eclipse window.
2. Right-click on the project (`JavaServer Pages`) in the `package view`.
3. In the opening pop-up menu, choose `Run As`.
4. In the opening sub-menu choose `Run Configurations...`.
5. In the opening window, choose `Maven Build`
6. In the new window `Run Configurations` / `Create, manage, and run configurations`, choose `Maven Build` in the small white pane on the left side.
7. Click `New launch configuration` (the first symbol from the left on top of the small white pane).
8. Write a useful name for this configuration in the `Name` field. You can use this configuration again later.
9. In the tab `Main` enter the `Base directory` of the project, this is the folder called `javaServerPages/examples` containing the Eclipse/Maven project.
10. Under `Goals`, enter `clean compile war:war`. This will build a `war` archive.
11. Click `Apply`
12. Click `Run`
13. The build will start, you will see its status output in the console window.
14. The folder `target` will contain a file `myJSPs.war` after the build. This is the deployable archive with our application.

### 2.3. Building under Linux without Eclipse

Under Linux, you can also simply run `make_linux.sh` in this project's folder to build the servlet without Eclipse, given that you have Maven installed.

### 2.4. Deploy

#### 2.4.1. Deploy to GlassFish

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
5. It should list `myJSPs`. If you click this menu point, you get a list of the installed servlets.
6. You can access the JavaServer Pages as follows
  a. [http://localhost:8080/myJSPs/index.jsp](http://localhost:8080/myJSPs/index.jsp)
  b. [http://localhost:8080/myJSPs/greetings.jsp](http://localhost:8080/myJSPs/greetings.jsp)