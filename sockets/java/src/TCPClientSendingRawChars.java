import java.io.OutputStream;    import java.net.InetAddress;    import java.net.Socket;

public class TCPClientSendingRawChars {

  public static final void main(final String[] args) {
    Socket        client;       OutputStream  os;
    InetAddress   ia;           int           ch;

    try {
      ia = InetAddress.getByName("localhost");
      
      client = new Socket(ia, 9999); //(*@\clientBox{1+2)}@*)
      
      os = client.getOutputStream();
      while ( (ch = System.in.read()) != '\n' ){ //read 1 char (until newline)
        os.write(ch); //write char to connection, may be buffered and not yet sent (*@\clientBox{3)}@*)  
      }   
      
      client.close(); //flush and close connection (*@\clientBox{4)}@*)
    } catch (Throwable t) {
      t.printStackTrace();
    }
  }
}
