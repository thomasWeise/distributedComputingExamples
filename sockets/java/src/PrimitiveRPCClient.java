import java.io.DataInputStream; import java.io.DataOutputStream; import java.net.InetAddress; import java.net.Socket;

public class PrimitiveRPCClient {

  public static final void main(final String[] args) {
    Socket            client;     InetAddress       ia;
    DataOutputStream  dos;        DataInputStream   dis;    

    try {
      ia = InetAddress.getByName("localhost");
      
      client = new Socket(ia, 9996);                      //open connection to server
      
      dos = new DataOutputStream(client.getOutputStream());
      dos.writeUTF("sub");                                //write name of method to execute
      dos.writeLong(9876);                                //write first parameter
      dos.writeLong(1234);                                //write second parameter
      dos.flush();                                        //flush is important, otherwise stuff may just be buffered!
      
      dis = new DataInputStream(client.getInputStream()); //obtain input stream
      System.out.println("Result: " + dis.readLong());    //read result
      
      client.close();                                     //close connection
    } catch (Throwable t) {
      t.printStackTrace();
    }
  }
}
