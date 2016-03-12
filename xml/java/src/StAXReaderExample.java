import java.io.File;                      import java.io.FileReader;
import javax.xml.stream.XMLInputFactory;  import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamConstants;

public class StAXReaderExample {
  @SuppressWarnings("incomplete-switch")
  public static void main(final String argv[]) throws Throwable {
    XMLInputFactory factory;     XMLStreamReader reader;

    factory = XMLInputFactory.newInstance(); //create the necessary objects
    try(final FileReader fr = new FileReader("../xml/courseWithNamespace.xml")) {
      reader  = factory.createXMLStreamReader(fr); //create the parser...
                 
      while (reader.hasNext()) { // iterate over the "XML-events"...
        
        switch (reader.next()) { // get the next event and switch/case the event type
          case XMLStreamConstants.START_ELEMENT: {         // element start?
            System.out.println("<" + reader.getLocalName() + ">"); break;
          }
          case XMLStreamConstants.END_ELEMENT: {           // element end?
            System.out.println("</" + reader.getLocalName() + ">");
          }
        }
      }    
    }  
  }
}