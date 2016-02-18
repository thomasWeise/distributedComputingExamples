import java.io.InputStream; import java.net.ServerSocket; import java.net.Socket;

public class TCPServerPrintingRawChars {

  public static final void main(final String[] args) {
    ServerSocket serv;      Socket client;      InputStream is;    int i;

    try {
      serv = new ServerSocket(9999); //start server (*@\serverBox{1 + 2)}@*)

      for (;;) {
        client = serv.accept();       //wait for incoming connection (*@\serverBox{3)}@*)
        is = client.getInputStream(); //get stream to read from connection
        
        while ((i = is.read()) >= 0) {//read bytes until connection closed (*@\serverBox{4} + \clientBox{3})@*)  
          System.out.print((char) i); //cast byte to char: dangerous!
        }
        System.out.println(); //print newline

        is.close(); //close reading stream of connection
        client.close(); //close connection (*@\clientBox{4)}@*)
      }
    } catch (Throwable t) {
      t.printStackTrace();
    }
  }
}
