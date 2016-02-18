import java.io.DataInputStream;   import java.io.DataOutputStream;
import java.net.ServerSocket;     import java.net.Socket;

public class TCPServerStructuredData {
  public static final void main(final String[] args) {
    ServerSocket      server;       Socket            client;     
    DataOutputStream  dos;          DataInputStream   dis;
    String            s;            long              a, b,r;

    try {
      server = new ServerSocket(9996);//(*@\serverBox{1 + 2)}@*)
      
      for (int j = 5; (--j) >= 0;) { //process only 5 clients, so I can show (*@\serverBox{5)}@*) below
        client = server.accept();    //(*@\serverBox{3)}@*)
              
        dis = new DataInputStream(client.getInputStream()); //(*@\serverBox{4} + \clientBox{3}@*)
        s   = dis.readUTF();      //read an UTF-encoded string: the operation
        r   = a = dis.readLong(); //read a 64 bit long integer
        b   = dis.readLong();     //read another 64 bit long int
        if ("add".equalsIgnoreCase(s)) { r += b; } else { // add
          if ("sub".equalsIgnoreCase(s)) { r -= b;  }     // subtract
        }  //(*@\serverBox{4} + \clientBox{3})@*)

        System.out.println(s + "(" + a + ", " + b + ") = " + r + " to " + client.getRemoteSocketAddress());

        dos = new DataOutputStream(client.getOutputStream()); //marshall output    
        dos.writeLong(r); //write 64bit long integer: (*@\serverBox{4} + \clientBox{3})@*)
        dos.close(); // flush and close
             
        client.close(); //(*@\clientBox{4)}@*)
      }
      server.close(); //(*@\serverBox{5)}@*)
    } catch (Throwable t) {
      t.printStackTrace();
    }
  }
}
