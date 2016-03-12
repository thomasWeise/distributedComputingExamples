import javax.xml.stream.XMLOutputFactory;     import javax.xml.stream.XMLStreamWriter;

public class StAXWriterExample {
  public static void main(final String argv[]) throws Throwable {
    XMLOutputFactory output;     XMLStreamWriter writer;
    
    String namespace = "ustc:courses";                 // let's use the course namespace
    output = XMLOutputFactory.newInstance();           // instantiate the necessary classes
    writer = output.createXMLStreamWriter(System.out); // create a stream writer (to stdout)
    
    writer.writeStartDocument();                       // begin an xml document
    writer.setPrefix("c", namespace);                  // define a namespace prefix
    
    writer.writeStartElement(namespace, "course");     // write the root element
      writer.writeNamespace("c", namespace);           // write the namespace prefix
      writer.writeAttribute(namespace,  "courseName", "Distributed Computing");
      
      writer.writeStartElement(namespace, "units");    // start an element
        writer.writeCharacters("60");                  // write some text
      writer.writeEndElement();                        // close/end an element
      
      writer.writeStartElement(namespace, "teachers"); // start a teachers element
        writer.writeStartElement(namespace, "teacher");// start a teacher element
          writer.writeAttribute(namespace, "familyName",   "Weise"); //write some attribute
        writer.writeEndElement();                      // end the teacher element
      writer.writeEndElement();                        // end the teachers element
      
    writer.writeEndElement();                          // end the course element
    writer.flush();                                    // flush the data
  }
}