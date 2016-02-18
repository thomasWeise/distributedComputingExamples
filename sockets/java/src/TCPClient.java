import java.io.OutputStream;  import java.net.InetAddress;  import java.net.Socket;

public class TCPClient {

  public static final void main(final String[] args) {
    Socket        client;
    OutputStream  os;
    InetAddress   ia;

    try {
      ia = InetAddress.getByName("localhost");//get local host address
      
      client = new Socket(ia, 9999); //create socket (*@\clientBox{1+2)}@*)
      
      os = client.getOutputStream(); //get stream to write to
      os.write(1);  //write one byte of value 1 (*@\clientBox{3)}@*)
      
      client.close(); //close (*@\clientBox{4)}@*)
    } catch (Throwable t) {
      t.printStackTrace();
    }
  }
}
