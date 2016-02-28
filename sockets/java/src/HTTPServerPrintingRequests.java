import java.io.BufferedReader;          import java.io.BufferedWriter;      import java.io.InputStreamReader;
import java.io.OutputStreamWriter;      import java.net.ServerSocket;       import java.net.Socket;

public class HTTPServerPrintingRequests {
  public static final void main(final String[] args) {
    String s;     StringBuilder sb;

    try (ServerSocket server = new ServerSocket(9995)) { //create server socket
      try (Socket client = server.accept()) { //accept incoming client

        sb = new StringBuilder(); //allocate buffer

        try (InputStreamReader ir = new InputStreamReader(client.getInputStream());//request=character stream
             BufferedReader    br = new BufferedReader(ir)) { //read request line-by-line
          while ((s = br.readLine()) != null) { //as long as lines can be read...
            sb.append(s);                       //append them to the buffer
            sb.append("<br/>");                 //add HTML line breaks
            if(s.length()<=0) { break; } // the final newline of the header
          }
          client.shutdownInput(); //no more input is requests

          try (OutputStreamWriter pw = new OutputStreamWriter(client.getOutputStream())) {
            pw.write("HTTP/1.1 200 OK\r\n\r\n<html><body><pre>"); //now write the answer: HTTP OK + HTML document
            pw.write(sb.toString()); //buffered content
            pw.write("</pre></body></html>"); //close the HTML document
          }
        }
      }
    } catch (Throwable t) {
      t.printStackTrace();
    }
  }
}
