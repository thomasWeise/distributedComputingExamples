import java.io.InputStream;   import java.net.ServerSocket;   import java.net.Socket;

public class TCPServer {
  public static final void main(final String[] args) {
    ServerSocket server;           InputStream  is;
    Socket       client;          

    try {
      server = new ServerSocket(9999);//(*@\serverBox{1 + 2)}@*)
      
      for (int j = 5; (--j) >= 0;) {  //process only 5 clients, so I can show (*@\serverBox{5)}@*) below
        client = server.accept(); //wait for incoming connection (*@\serverBox{3)}@*)
        System.out.println("New connection from " + client.getRemoteSocketAddress());
        
        is     = client.getInputStream(); //get stream to read from
        System.out.println(is.read());//(*@\serverBox{4} + \clientBox{3})@*)
             
        client.close(); //close connection to client
      }
      server.close();   //(*@\serverBox{5)}@*)
    } catch (Throwable t) {
      t.printStackTrace();
    }
  }
}
