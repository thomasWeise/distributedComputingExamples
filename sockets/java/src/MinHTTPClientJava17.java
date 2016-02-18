import java.io.BufferedReader;          import java.io.InputStreamReader;
import java.io.OutputStreamWriter;      import java.net.Socket;

public class MinHTTPClientJava17 {//this is a minimum web client; see lesson 07 coming later
  public static final void main(final String[] args) {
    String dest, request, response;   

    dest    = "www.baidu.com";      // a random example for a Chinese host
    request = "GET /index.html HTTP/1.1\nHost: " + dest + "\n\n\n";

    try(Socket sock = new Socket(dest, 80)) { // web servers are usually listening at port 80
      try(OutputStreamWriter w = new OutputStreamWriter(sock.getOutputStream())) {
      w.write(request);             // write the HTTP request (*@\citep{RFC1945,RFC2616,BT2002HTDG}@*)
      w.flush();                    // make sure that all data has been sent
      sock.shutdownOutput();        // closing down the channel for sending data to the server

      try(BufferedReader r =
           new BufferedReader(new InputStreamReader(sock.getInputStream(), "UTF-8"))) { // Baidu uses UTF-8 (*@\citep{RFC3629STC63}@*),
          while ((response = r.readLine()) != null) { // read strings line-by-line until connection closed by serve
            System.out.println(response);             // print to output
          }
        }
      }
    } catch (Throwable t) {
      t.printStackTrace();
    }
  }
}
