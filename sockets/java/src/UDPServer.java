import java.io.OutputStream;      import java.net.DatagramPacket;
import java.net.DatagramSocket;   import java.net.InetAddress;

public class UDPServer {
  public static final void main(final String[] args) {
    DatagramSocket  server;     DatagramPacket  p;
    
    try {
      server = new DatagramSocket(9998); //create socket (*@\serverBox{1)}@*)

      for(int j = 5; (--j) >= 0; ){
        p = new DatagramPacket(new byte[1], 1); //create package
        server.receive(p); //wait for and receive incoming data (*@\serverBox{2)}@*)
  
        System.out.println("New message " + p.getSocketAddress());
        if (p.getLength() > 0) { //is there data? (*@\serverBox{3)}@*)
          System.out.println(p.getData()[0]);  //(*@\serverBox{3)}@*)
        }
      }
      server.close();  //(*@\serverBox{5)}@*)
    } catch (Throwable t) {
      t.printStackTrace();
    }
  }
}
