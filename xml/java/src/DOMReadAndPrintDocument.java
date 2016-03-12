import org.w3c.dom.Document; import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Element;  import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Node;     import javax.xml.transform.TransformerFactory;
import org.w3c.dom.NodeList; import javax.xml.parsers.DocumentBuilderFactory;     
import java.io.File;         import javax.xml.transform.dom.DOMSource; 

public class DOMReadAndPrintDocument {
  public static void main(final String argv[]) throws Throwable {
    Document  document;         Node  n, m;
  
    document = DocumentBuilderFactory.newInstance()       // read the course example
                                     .newDocumentBuilder()// document as DOM tree
                                     .parse("../xml/course.xml");  
      
    n = document.createElement("myNewElement");       // create and add a new element
    document.getDocumentElement().appendChild(n);
    
    m = document.createElement("myNewSubElement");    // create and add a sub-element
    n.appendChild(m);
    
    n = document.createAttribute("myAttribute");      // create a new attribute
    n.setNodeValue("myAttributeValue");               // add the attribute
    m.getAttributes().setNamedItem(n);

    
    TransformerFactory.newInstance() 
                      .newTransformer()
                      .transform(new DOMSource(document), 
                                 new StreamResult(System.out)); // print to stdout    
  }
}