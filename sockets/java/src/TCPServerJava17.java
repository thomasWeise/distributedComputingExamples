import java.io.InputStream; import java.net.ServerSocket; import java.net.Socket;

public class TCPServerJava17 {
  public static final void main(final String[] args) {

    try(ServerSocket server = new ServerSocket(9999)){  //(*@\serverBox{1 + 2)}@*)
      
      for (int j = 5; (--j) >= 0;) { //process only 5 clients, so I can show (*@\serverBox{5)}@*) below
        try(Socket client = server.accept()) { //wait for incoming connection (*@\serverBox{3)}@*)
          System.out.println("New connection from " + client.getRemoteSocketAddress());
      
          try(InputStream is = client.getInputStream()){//get stream to read
            System.out.println(is.read()); //(*@\serverBox{4} + \clientBox{3})@*)             
            } //close reading end of connection
          } //close connection (*@\serverBox{5)}@*)
        }
    //(*@\serverBox{5)}@*)
    } catch (Throwable t) {
      t.printStackTrace();
    }
  }
}
