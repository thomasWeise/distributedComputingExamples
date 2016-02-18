import java.io.DataInputStream;   import java.io.DataOutputStream;
import java.net.InetAddress;      import java.net.Socket;

public class TCPClientStructuredData {

  public static final void main(final String[] args) {
    Socket            client;     InetAddress       ia;
    DataOutputStream  dos;        DataInputStream   dis;    

    try {
      ia = InetAddress.getByName("localhost");
      
      client = new Socket(ia, 9996); //(*@\clientBox{1+2)}@*)
      
      dos = new DataOutputStream(client.getOutputStream()); //marshall data
      dos.writeUTF("sub"); //send operation name (*@\clientBox{3)}@*)
      dos.writeLong(9876); //send 64bit long integer
      dos.writeLong(1234); //send another 64bit long integer
      dos.flush();  //flush is important, otherwise stuff may just be buffered!
      
      dis = new DataInputStream(client.getInputStream()); // unmashall input
      System.out.println("Result: " + dis.readLong());    //(*@\clientBox{3)}@*)
      
      client.close(); //(*@\clientBox{4)}@*)
    } catch (Throwable t) {
      t.printStackTrace();
    }
  }
}
