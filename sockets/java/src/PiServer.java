import java.io.ByteArrayInputStream;    import java.io.ByteArrayOutputStream;   import java.io.DataInputStream;
import java.io.DataOutputStream;        import java.io.OutputStream;            import java.net.DatagramPacket;
import java.net.DatagramSocket;         import java.net.InetAddress;

public class PiServer {
  public static final void main(final String[] args) {
    DatagramSocket        server;   DatagramPacket    p, answer;
    ByteArrayInputStream  bis;      DataInputStream   dis;
    byte[]                data;     String            s;
    long                  n, c;     double            d;

    n=0;c=0;//try to approximate PI
    try {
      server = new DatagramSocket(9992);//create server socket                           
      data   = new byte[16];//create package: 2* 8 byte long ints must fit

      for (;;) {//forever
        p = new DatagramPacket(data, data.length);//create new package
        server.receive(p); //wait for and receive incoming data

        bis = new ByteArrayInputStream(data, 0, p.getLength());//wrap data into stream    
        dis = new DataInputStream(bis);//unmarshall data

        n += dis.readLong(); //update total number of random points sampled from unit square
        c += dis.readLong(); //update number of these points that fell into the unit circle
        d  = ((4.0 * c) / n);//approximate PI
        System.out.println(d + " " + (d - Math.PI)); //print approximation and error        
      }
    } catch (Throwable t) {
      t.printStackTrace();
    }
  }
}
