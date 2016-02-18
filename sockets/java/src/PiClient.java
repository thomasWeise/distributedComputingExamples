import java.io.ByteArrayInputStream;   import java.io.ByteArrayOutputStream;   import java.io.DataInputStream;   import java.io.DataOutputStream;
import java.net.DatagramPacket;        import java.net.DatagramSocket;         import java.net.InetAddress;

public class PiClient { //the worker part of the example for approximating the number of pi
  public static final void main(final String[] args) {
    DatagramSocket  client;         InetAddress           ia;
    ByteArrayOutputStream bos;      DataOutputStream      dos;
    DatagramPacket        p;        byte[]                data;
    long                  c, n;     double                x, y;

    c = 0;//work: approximate fraction of points in unit square which are in unit circle
    for(n = 1; n <= 100000000; n++) {//create a lot of random points in [0, 1)
      x = Math.random(); //x-coordinate of point
      y = Math.random(); //y-coordinate of point
      if(Math.sqrt((x*x) + (y*y)) <= 1d){ //is the point inside the unit circle?
        c++; //count
        }
      }
      
    try {
      ia = InetAddress.getByName("localhost");//get local host address
      client = new DatagramSocket(); //create UDP/datagram socket

      bos = new ByteArrayOutputStream();//create buffered output stream
      dos = new DataOutputStream(bos);//marshall computed data
      dos.writeLong(n);//store the number of generated points in unit square
      dos.writeLong(c);//store the number of points in unit circle
      dos.close();//close and flush
      data = bos.toByteArray();//get data
      
      p = new DatagramPacket(data, data.length, ia, 9992);//create data package
      client.send(p);//send the package to the server
      
      client.close();//close connection                                
    } catch (Throwable t) {
      t.printStackTrace();
    }
  }
}
