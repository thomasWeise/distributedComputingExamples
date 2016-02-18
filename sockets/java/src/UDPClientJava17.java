import java.io.OutputStream;        import java.net.DatagramPacket;
import java.net.DatagramSocket;     import java.net.InetAddress;

public class UDPClientJava17 {

  public static final void main(final String[] args) {
    InetAddress  ia;        DatagramPacket p;        byte[] data;

    try {
      ia = InetAddress.getByName("localhost"); //get local host address

      try(DatagramSocket client = new DatagramSocket()) {  //(*@\clientBox{1)}@*)
        data   = new byte[] { 1 }; //allocate data
        p      = new DatagramPacket(data, 1, ia, 9998); //create package
        client.send(p); //send package to localhost:9998 (*@\clientBox{2)}@*)
      } //(*@\clientBox{4)}@*)
    } catch (Throwable t) {
      t.printStackTrace();
    }
  }
}
