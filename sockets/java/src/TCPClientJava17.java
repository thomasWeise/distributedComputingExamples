import java.io.OutputStream; import java.net.InetAddress; import java.net.Socket;

public class TCPClientJava17 {

  public static final void main(final String[] args) {
    InetAddress   ia;

    try {
      ia = InetAddress.getByName("localhost");
      
      try(Socket client = new Socket(ia, 9999)){   //(*@\clientBox{1+2)}@*)
      
       try(OutputStream os = client.getOutputStream()) {
           os.write(1);  //write one byte with value 1 (*@\clientBox{3)}@*)
         } //close writing end of connection
      }  //(*@\clientBox{4)}@*)
    } catch (Throwable t) {
      t.printStackTrace();
    }
  }
}
