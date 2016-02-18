import java.io.OutputStream;        import java.net.DatagramPacket;
import java.net.DatagramSocket;     import java.net.InetAddress;

public class UDPClient {

  public static final void main(final String[] args) {
    DatagramSocket client;      InetAddress    ia;
    DatagramPacket p;           byte[]         data;

    try {
      ia     = InetAddress.getByName("localhost");

      client = new DatagramSocket(); //create socket (*@\clientBox{1)}@*)

      data   = new byte[] { 1 }; //allocate data for package
      p      = new DatagramPacket(data, 1, ia, 9998); //create package
      client.send(p); //send package to localhost:9998 (*@\clientBox{2)}@*)

      client.close(); //dispose socket (*@\clientBox{4)}@*)
    } catch (Throwable t) {
      t.printStackTrace();
    }
  }
}
