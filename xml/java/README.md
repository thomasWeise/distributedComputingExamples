# Examples for Java XML Processing

Under Java, there exist several different ways to process streams formatted in the Extensible Markup Language ([XML](https://en.wikipedia.org/wiki/Xml)) and related standards. Here you can find examples for them.

## 1. DOMCreateAndPrintDocument

An XML document is created in memory using the Document Object Model ([DOM](http://docs.oracle.com/javase/tutorial/jaxp/dom/)) and then serialized to the standard output. Instead of stdout, we could as well have used a file. So now we can create XML documents.

1. [DOMCreateAndPrintDocument.java](http://github.com/thomasWeise/distributedComputingExamples/tree/master/xml/java/src/DOMCreateAndPrintDocument.java)

## 2. DOMReadAndPrintDocument

We load the file [course.xml](http://github.com/thomasWeise/distributedComputingExamples/tree/master/xml/xml/course.xml) as DOM document from a file into memory. In memory, we modify it. Then we serialize it to the standard output.

1. [DOMReadAndPrintDocument.java](http://github.com/thomasWeise/distributedComputingExamples/tree/master/xml/java/src/DOMReadAndPrintDocument.java)
1. [course.xml](http://github.com/thomasWeise/distributedComputingExamples/tree/master/xml/xml/course.xml)


## 3. SAXReaderExample

The [Simple API for XML](http://www.saxproject.org/) ([SAX](https://en.wikipedia.org/wiki/Simple_API_for_XML)) is another way to load XML documents. While DOM loads the whole document contents into memory, which allows for you to edit it but is also memory consuming, SAX treats an XML document more like a stream of events. A [SAX parser](http://docs.oracle.com/javase/tutorial/jaxp/sax/parsing.html) accepts a [listener](download.oracle.com/javase/tutorial/uiswing/events/index.html)-like handler which it notifies whenever it encounters a new element or text or processing instructions in an XML stream.

A SAX parser is a [push parser](http://docs.oracle.com/cd/E19159-01/819-3669/bnbdy/index.html), meaning that the parser invokes the methods of your provided handler, i.e., the parser makes the decision when your application is notified about events and you have little control over this. You can imagine SAX something like a [visitor pattern](https://en.wikipedia.org/wiki/Visitor_pattern) for XML documents. Obviously, this does not require loading the whole document into memory, instead, it can be processed more efficiently, in a stream-like fashion. This has another advantage: While processing a DOM document requires the whole document to be loaded first, a SAX handler can receive the first events already after only a few bytes have been read from the source document.

Here we apply a SAX parser to the [courseWithNamespace.xml](http://github.com/thomasWeise/distributedComputingExamples/tree/master/xml/xml/courseWithNamespace.xml) example file. The class [SAXReaderExample.java](http://github.com/thomasWeise/distributedComputingExamples/tree/master/xml/java/src/SAXReaderExample.xml) not only executes the SAX parser in its `main` method, it also extends the class [`DefaultHandler`](http://docs.oracle.com/javase/7/docs/api/org/xml/sax/helpers/DefaultHandler.html), i.e., the default implementation of the SAX event listener interfaces. It therefore can receive and print SAX events.

1. [SAXReaderExample.java](http://github.com/thomasWeise/distributedComputingExamples/tree/master/xml/java/src/SAXReaderExample.java)
1. [courseWithNamespace.xml](http://github.com/thomasWeise/distributedComputingExamples/tree/master/xml/xml/courseWithNamespace.xml)


## 4. SAXReaderExampleValidating

While the above example can recognize namespaces in our XML document, it may not validate the document. This has two reasons:

1. Validation is a feature which must be turned on via `setValidating(true)`.
2. The parser may not know where to find the XML schema for a given namespace. For this purpose, a `schemaLocation` attribute can be added to the XML file, as we do in [courseWithNamespaceAndSchemaLocation.xml](http://github.com/thomasWeise/distributedComputingExamples/tree/master/xml/xml/courseWithNamespaceAndSchemaLocation.xml). 

Here we unite these two steps into a new version of the SAX parsing example:

1. [SAXReaderExampleValidating.java](http://github.com/thomasWeise/distributedComputingExamples/tree/master/xml/java/src/SAXReaderExampleValidating.java)
1. [courseWithNamespaceAndSchemaLocation.xml](http://github.com/thomasWeise/distributedComputingExamples/tree/master/xml/xml/courseWithNamespaceAndSchemaLocation.xml)
1. [courses.xsd](http://github.com/thomasWeise/distributedComputingExamples/tree/master/xml/xml/courses.xsd)


## 5. StAXReaderExample

The Streaming API for XML ([StAX](https://en.wikipedia.org/wiki/StAX)) is complement to SAX: Like SAX, it presents an XML document as stream of events. However, different from SAX, it is a pull parser. Here, the user has to actively query the reader for events, process them, and then query for the next event. You can imagine this like an [iterator pattern](https://en.wikipedia.org/wiki/Iterator_pattern) for XML document elements.

1. [StAXReaderExample.java](http://github.com/thomasWeise/distributedComputingExamples/tree/master/xml/java/src/StAXReaderExample.java)
1. [courseWithNamespace.xml](http://github.com/thomasWeise/distributedComputingExamples/tree/master/xml/xml/courseWithNamespace.xml)


## 6. StAXWriterExample

This iterator pattern can also be "turned around": An `XMLStreamWriter` provides methods such as `writeStartElement` and `writeAttribute` which allow us to, well, build an XML document in a stream-like fashion. We do not need to build the document completely in memory and then serialize it, as in DOM, but now can build it step-by-step. This does, of course, require significantly fewer resources than DOM-based processing. Also, combine this with either StAX or SAX parsing and you get something very fast: Our process may already begin to write an output document while it is still reading its input document.

1. [StAXWriterExample.java](http://github.com/thomasWeise/distributedComputingExamples/tree/master/xml/java/src/StAXWriterExample.java)
1. [courses.xsd](http://github.com/thomasWeise/distributedComputingExamples/tree/master/xml/xml/courses.xsd)


## 7. XSLTTransform

With Extensible Stylesheet Language Transformations ([XSLT]](https://en.wikipedia.org/wiki/XSLT)), we can transform one XML document to another document, which does not even necessarily need to be an XML document.  In the file [courses2html.xslt](http://github.com/thomasWeise/distributedComputingExamples/tree/master/xml/xml/courses2html.xslt) we specify how to transform XML documents conforming to our [courses.xsd](http://github.com/thomasWeise/distributedComputingExamples/tree/master/xml/xml/courses.xsd) schema to [HTML](https://en.wikipedia.org/wiki/HTML). Here you can find how we can actually perform this application, i.e., apply [courses2html.xslt](http://github.com/thomasWeise/distributedComputingExamples/tree/master/xml/xml/courses2html.xslt) to [courseWithNamespace.xml](http://github.com/thomasWeise/distributedComputingExamples/tree/master/xml/xml/courseWithNamespace.xml) in Java.

1. [XSLTTransform.java](http://github.com/thomasWeise/distributedComputingExamples/tree/master/xml/java/src/XSLTTransform.java)
1. [courses2html.xslt](http://github.com/thomasWeise/distributedComputingExamples/tree/master/xml/xml/courses2html.xslt)
1. [courses.xsd](http://github.com/thomasWeise/distributedComputingExamples/tree/master/xml/xml/courses.xsd)