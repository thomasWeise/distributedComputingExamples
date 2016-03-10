# Examples for XML

Here you can find some examples for the Extensible Markup Language ([XML](https://en.wikipedia.org/wiki/Xml)] and related standards.

## 1. course.xml

`course.xml` is a simple XML file which describes basic data of a course, such as
the number of units, the teachers, and the participating students. It shows how
data can be arranged hierarchical in an XML file and has both elements and attributes.

1. [course.xml](http://github.com/thomasWeise/distributedComputingExamples/tree/master/xml/xml/course.xml)

## 2. courses.xsd

The example above showed a basic use case for XML, but left away a few crucial issues. For instance, how does someone who receives the `course.xml` file know whether it is [valid](https://en.wikipedia.org/wiki/XML_validation)? In other words, how does she know that the `teachers` element is allowed to contain arbitrarily many `teacher` elements? If a person interprets the data, that might not be an issue. But we want to interpret data *automatically*. We may assume that hundreds of courses XML files are generated an processed. If we want to do that, we need some sort of mechanism to perform a sanity check, ideally already when loading the files, before feeding their data into the actual processing step.

For this purpose, [XML Schemas](https://en.wikipedia.org/wiki/XML_Schema_%28W3C%29) exist. With an XML Schema, we can specify a blueprint for certain type of XML files, for XML files belonging to a certain [namespace](https://en.wikipedia.org/wiki/XML_namespace). Think that a XML Schema corresponds to a [`typedef`](https://en.wikipedia.org/wiki/Typedef) of a `struct` in `C`, while the XML file is the actual variable instance of it.

After having defined an XML namespace via a Schema, we can now use it in an XML file by declaring it via `xmlns`.

However, this declaration itself does not ensure that the document is actually validated. For this purpose, an [XML parser](https://en.wikipedia.org/wiki/XML#Programming_interfaces) needs to know where to find the schema. This can be done by adding a `schemaLocation` attribute to the element declaration inside the XML file. Notice that an XML namespace is identified by a [URI](https://en.wikipedia.org/wiki/Uniform_Resource_Identifier). This URI may or may not be a [URL](https://en.wikipedia.org/wiki/Uniform_Resource_Locator) and even if it is a URL, it does not necessarily point to an existing resource. In our example, I intentionally did not use a URL but `ustc:courses` as namespace. Even if I had used `http://www.test.com/xyz.xsd`, there would have been no guarantee that this file actually existed. With the `schemaLocation`, we can link the namespace URI `ustc:courses` to a link to the location of the schema in the internet, right here on GitHub [https://raw.githubusercontent.com/thomasWeise/distributedComputingExamples/master/xml/xml/courses.xsd](https://raw.githubusercontent.com/thomasWeise/distributedComputingExamples/master/xml/xml/courses.xsd). Now a validating XML parser can always download the schema and use it to check the document.

1. [courses.xsd](http://github.com/thomasWeise/distributedComputingExamples/tree/master/xml/xml/courses.xsd)
1. [courseWithNamespace.xml](http://github.com/thomasWeise/distributedComputingExamples/tree/master/xml/xml/courseWithNamespace.xml)
1. [courseWithNamespaceAndSchemaLocation.xml](http://github.com/thomasWeise/distributedComputingExamples/tree/master/xml/xml/courseWithNamespaceAndSchemaLocation.xml)

## 3. courses2html.xslt

With Extensible Stylesheet Language Transformations ([XSLT]](https://en.wikipedia.org/wiki/XSLT)), we can transform one XML document to another - not necessarily XML - document. XSLT is basically a language which tells a transformation process what output to produce for which element and attribute. Sometimes, there might be two different XML dialects / schemas / namespaces for similar domains, e.g., there could be a `tsignhua:courses` namespace with similar purpose than our declaration in the above example. With XSLT, we could translate one document from `ustc:courses` to `tsinghua:courses`. Or we could translate our document to [HTML](https://en.wikipedia.org/wiki/HTML), i.e., we could translate our raw data to a web site. This is what we do with this example: 

1. [courses2html.xslt](http://github.com/thomasWeise/distributedComputingExamples/tree/master/xml/xml/courses2html.xslt)
1. [courseWithNamespace.xml](http://github.com/thomasWeise/distributedComputingExamples/tree/master/xml/xml/courseWithNamespace.xml)
1. [course.html](http://github.com/thomasWeise/distributedComputingExamples/tree/master/xml/xml/course.html) (the result of the transformation)