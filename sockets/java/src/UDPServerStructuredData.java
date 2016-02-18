import java.io.ByteArrayInputStream;  import java.io.DataInputStream;  import java.io.OutputStream; import java.net.DatagramPacket;         
import java.io.ByteArrayOutputStream; import java.io.DataOutputStream; import java.net.InetAddress; import java.net.DatagramSocket;

public class UDPServerStructuredData {
  public static final void main(final String[] args) {
    DatagramSocket        server;   DatagramPacket    p, answer;
    ByteArrayOutputStream bos;      DataOutputStream  dos;
    ByteArrayInputStream  bis;      DataInputStream   dis;
    byte[]                data;     String            s;
    long                  a, b,r;

    try {
      server = new DatagramSocket(9997); //(*@\serverBox{1)}@*)
      data = new byte[2048];

      for (int j = 5; (--j) >= 0;) {
        p = new DatagramPacket(data, data.length); // create package
        server.receive(p);  // receive data (*@\serverBox{2)}@*)

        bis = new ByteArrayInputStream(data, 0, p.getLength());  //wrap in stream (*@\serverBox{3)}@*)
        dis = new DataInputStream(bis); //wrap again for unmarshalling
        s   = dis.readUTF();      //read string with operation id
        r   = a = dis.readLong(); //read 64bit long integer
        b   = dis.readLong();     //read 64bit long integer
        if ("add".equalsIgnoreCase(s)) { r += b; } else { //add
          if ("sub".equalsIgnoreCase(s)) { r -= b;  }    //subtract
        }  //end (*@\serverBox{3)}@*)

        System.out.println(s + "(" + a + ", " + b + ") = " + r + " to " + p.getSocketAddress());
        
        bos = new ByteArrayOutputStream(); //create buffered stream for answer
        dos = new DataOutputStream(bos); //marshall
        dos.writeLong(r); //write 64bit long with result
        dos.close(); //flush to buffer and close

        answer = new DatagramPacket(bos.toByteArray(), bos.size(), p.getSocketAddress()); //(*@\serverBox{4)}@*)
        server.send(answer); //send marshalled answer data
      }
      server.close(); //(*@\serverBox{5)}@*)
    } catch (Throwable t) {
      t.printStackTrace();
    }
  }
}
