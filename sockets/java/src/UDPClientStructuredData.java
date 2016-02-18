import java.io.ByteArrayInputStream;  import java.io.DataInputStream;  import java.io.OutputStream;         
import java.io.ByteArrayOutputStream; import java.io.DataOutputStream; import java.net.InetAddress;
import java.net.DatagramPacket;       import java.net.DatagramSocket;

public class UDPClientStructuredData {
  public static final void main(final String[] args) {
    DatagramSocket  client;         InetAddress           ia;
    ByteArrayOutputStream bos;      DataOutputStream      dos;
    ByteArrayInputStream  bis;      DataInputStream       dis;
    DatagramPacket        p;        byte[]                data;

    try {
      ia = InetAddress.getByName("localhost");
      client = new DatagramSocket();  //create socket (*@\clientBox{1)}@*)

      bos = new ByteArrayOutputStream(); //create buffered stream for building message
      dos = new DataOutputStream(bos);   //mashall data
      dos.writeUTF("add");               //write operation name
      dos.writeLong(1234);               //write 64bit long: 1st operand
      dos.writeLong(9876);               //write 64bit long: 2nd operand
      dos.close();              //flush to buffer and close
      data = bos.toByteArray(); //get array with marshalled data to send 
      
      p = new DatagramPacket(data, data.length, ia, 9997); //create package
      client.send(p);     //send package to server (*@\clientBox{2)}@*)

      client.receive(p); // receive answer
      bis = new ByteArrayInputStream(p.getData(), 0, p.getLength());
      dis = new DataInputStream(bis); //unmarshall
      System.out.println("Result: " + dis.readLong()); //(*@\clientBox{3)}@*)

      client.close(); //(*@\clientBox{4)}@*)
    } catch (Throwable t) {
      t.printStackTrace();
    }
  }
}
