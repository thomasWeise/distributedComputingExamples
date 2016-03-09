import java.io.DataInputStream; import java.io.DataOutputStream;
import java.net.ServerSocket;   import java.net.Socket;

public class PrimitiveRPCServer {
  public static final void main(final String[] args) {
    ServerSocket      server;       Socket            client;     
    DataOutputStream  dos;          DataInputStream   dis;
    String            s;            long              a, b,r;

    try {
      server = new ServerSocket(9996);                            //instantiate server
      
      for ( ; ; ) {                                               //main loop
        client = server.accept();                                 //accept client
              
        dis = new DataInputStream(client.getInputStream());       //marshalling
        s   = dis.readUTF();                                      //read name of method to run
        r   = a = dis.readLong();                                 //read first parameter
        b   = dis.readLong();                                     //read second parameter
        if ("add".equalsIgnoreCase(s)) { r += b; } else {         //execute method
          if ("sub".equalsIgnoreCase(s)) { r -= b;  }             //execute method
        }

        System.out.println(s + "(" + a + ", " + b + ") = " + r + " to " + client.getRemoteSocketAddress());

        dos = new DataOutputStream(client.getOutputStream());     
        dos.writeLong(r);                                         //send result
        dos.close();
             
        client.close();                                           //close client stream
      }
    } catch (Throwable t) {
      t.printStackTrace();
    }
  }
}
