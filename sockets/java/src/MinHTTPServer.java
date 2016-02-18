import java.io.BufferedReader;      import java.io.File;        import java.io.FileInputStream;   import java.io.InputStreamReader;
import java.io.OutputStreamWriter;  import java.io.PrintWriter; import java.net.ServerSocket;     import java.net.Socket;

public class MinHTTPServer { //this is a minimum web server; see lesson 07 coming later
  public static final void main(final String[] args) {
    ServerSocket server;   Socket client;   BufferedReader  br;   int  i;   byte[]    bs;
    PrintWriter  pw;       String s;        FileInputStream fis;  File f;   Throwable x;

    try {
      server = new ServerSocket(9995); //create server socket (*@\serverBox{1 + 2)}@*)

      for (;;) { 
        client = server.accept(); //wait for and accept incoming connections (*@\serverBox{3)}@*)

        br = new BufferedReader(new InputStreamReader(client.getInputStream())); // read character data
        pw = new PrintWriter(new OutputStreamWriter(client.getOutputStream(), "ISO_8859-1")); //chose the right encoding! (*@\citep{RFC1945,RFC2616}@*)
        
        process: { //(*@\serverBox{4} + \clientBox{3})@*)
          x = null;
          try {
            while ((s = br.readLine()) != null) { //read text from connection line-by-line until end
              if (s.startsWith("GET ")) {  // try to find the GET command in the HTTP request (*@\citep{RFC1945,RFC2616}@*)    
                f   = new File(s.substring(4, s.indexOf(' ', 4)).replace('/', File.separatorChar)); //in a very crude way, extract the requested path from that command          
                bs  = new byte[(int) (f.length())];  //allocate a buffer of the right size                
                fis = new FileInputStream(f);        //open the file
                i   = fis.read(bs);                  //read the complete file into memory
                fis.close();                         //close the file
                pw.write("HTTP/1.1 200 OK\r\n\r\n");  pw.flush(); //send "success" according to (*@\citep{RFC1945,RFC2616}@*)
                client.getOutputStream().write(bs, 0, i); //...and the file content  ((*@\serverBox{4} + \clientBox{3})@*))            
                break process;                            //ok, we are finished here
              }
            }
          } catch (Throwable t) { x= t; } //if request fails, remember why

          //hm, we did not find the file or had an error (*@\citep{RFC1945,RFC2616}@*)
          pw.write("HTTP/1.1 404 Not Found\r\n\r\n<html><head><title>404</title></head><body><h1>404 - Not found</h1><pre>");
          if(x != null) { x.printStackTrace(pw); } //write the error message (notice the <pre>...</pre> wrapper)
          pw.write("</pre></body></html");         //end the html body
          pw.flush(); //and flush ((*@\serverBox{4} + \clientBox{3})@*))
        }

        client.close();                                                                             //(*@\clientBox{4)}@*)
      }     
    } catch (Throwable t) {
      t.printStackTrace();
    }
  }
}
