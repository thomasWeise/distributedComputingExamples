import javax.xml.parsers.SAXParser;   import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;        import org.xml.sax.helpers.DefaultHandler;
import org.w3c.dom.Document;

public class SAXReaderExampleValidating extends DefaultHandler { //derive new class from DefaultHandler

  public static void main(final String argv[]) throws Throwable {
    SAXParserFactory factory = SAXParserFactory.newInstance();
    factory.setValidating(true); // validate the document
    factory.newSAXParser()
           .parse("../xml/courseWithNamespaceAndSchemaLocation.xml", // parse the document 
                  new SAXReaderExampleValidating());     
  }
  
  // implement one of the many event handler methods
  public void startElement(String uri, String localName, String qName, Attributes attributes) {
    System.out.print("<" + qName);
    for(int i = attributes.getLength(); (--i) >= 0; ) {
      System.out.print(" " + attributes.getQName(i) + "=" + attributes.getValue(i));
    }
    System.out.println(">");
  }
}