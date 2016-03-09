import java.rmi.registry.LocateRegistry;      import java.rmi.registry.Registry;

public class RemotePrintClient { // the remote print rmi client
  public static final void main(final String args[]) {
    RemotePrintInterface rmiServer;         Registry registry;

    try {
      registry = LocateRegistry.getRegistry(9999);      // find the object registry

      rmiServer = (RemotePrintInterface) (registry.lookup("server")); // find the server object

      rmiServer.print("Hello World"); //$NON-NLS-1$
    } catch (Throwable t) {
      t.printStackTrace();
    }
  }
}
