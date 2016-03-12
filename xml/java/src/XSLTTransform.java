import javax.xml.transform.stream.StreamSource; import javax.xml.transform.Result;
import javax.xml.transform.stream.StreamResult; import javax.xml.transform.Source;
import javax.xml.transform.TransformerFactory;  import java.io.File; 
import javax.xml.transform.Transformer;

public class XSLTTransform {
  public static void main(final String argv[]) throws Throwable {
    TransformerFactory factory;    Transformer transformer;
    Source xmlInput, xsltInput;    Result      output;
    
    
    xmlInput   = new StreamSource(new File("../xml/courseWithNamespace.xml"));
    xsltInput  = new StreamSource(new File("../xml/courses2html.xslt"));
    output     = new StreamResult(System.out);

    factory     = TransformerFactory.newInstance();  // create the necessary objects to
    transformer = factory.newTransformer(xsltInput); // transform an XML stream with XSLT!

    transformer.transform(xmlInput, output);         // flush the data    
  }
}