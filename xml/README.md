# Examples for XML and XML Processing

The Extensible Markup Language ([XML](https://en.wikipedia.org/wiki/Xml)) is a way to represent data in a structured text format. There actually exist a lot of standards around XML, for specifying how a document of a specific type is structured and how to transform a document to another document of different type. Additionally, there exists an extremely broad software support for processing XML documents. In Java, you get most of this out-of-the-box.

## 1. Description

We can consider XML from two perspectives: The first one is the *document* perspective. XML allows us to build (languages for) structured documents to exchange data for specific applications. The second perspective of XML is the *database perspective*: An XML document can store data, thus we could indeed consider it as data base. Here, XML offers a [semi-structured data model](https://en.wikipedia.org/wiki/Semi-structured_model), where the *structure* of the data and the *data* itself are part of the same (text) stream. The structure is given by XML elements and attributes and the data are the values of the elements and attributes. XML thus is something between a *structured* or [*relational*](https://en.wikipedia.org/wiki/Relational_model) data model, such as offered by [SQL databases](https://en.wikipedia.org/wiki/SQL) where the data structure information and the data are stored separately, and an [unstructured data model](https://en.wikipedia.org/wiki/Unstructured_data), such as simple text files or web pages, where data follows no specific scheme and must be interpreted intelligently.

Anyway, from the perspective of distributed applications, we are mainly interested in the document perspective, as we

1. want to exchange documents with information between applications and
2. specify how these documents should look like and how they should be exchanged

We will therefore first learn some [basic examples](http://github.com/thomasWeise/distributedComputingExamples/tree/master/xml/xml) of XML documents, [XML Schemas](https://en.wikipedia.org/wiki/XML_Schema_%28W3C%29), which are used to define XML-based languages, and the Extensible Stylesheet Language Transformations ([XSLT]](https://en.wikipedia.org/wiki/XSLT)), which can be used to define a transformation from one XML-based language to another text format or XML-based language. Afterwards, we [discuss](http://github.com/thomasWeise/distributedComputingExamples/tree/master/xml/java) how we can process XML documents with Java and explore the Document Object Model ([DOM](http://docs.oracle.com/javase/tutorial/jaxp/dom/)), the [Simple API for XML](http://www.saxproject.org/) ([SAX](https://en.wikipedia.org/wiki/Simple_API_for_XML)), the Streaming API for XML ([StAX](https://en.wikipedia.org/wiki/StAX)), and how to dynamically perform XSL transformations.

This lesson is the foundation for our following [investigation](http://github.com/thomasWeise/distributedComputingExamples/tree/master/webServices/) of technologies for enterprise computing environments centered around [Web Services](https://en.wikipedia.org/wiki/Web_service).

## 2. Examples

Here we provide

1. [examples for XML documents and related standards](http://github.com/thomasWeise/distributedComputingExamples/tree/master/xml/xml)
1. [examples for XML processing with Java](http://github.com/thomasWeise/distributedComputingExamples/tree/master/xml/java)