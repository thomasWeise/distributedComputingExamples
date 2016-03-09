import java.rmi.registry.LocateRegistry;      import java.rmi.registry.Registry;

public class RemotePrintClientErroneous { // the erroneous remote print rmi client
  public static final void main(final String args[]) {
    RemotePrintInterface rmiServer;         Registry registry;

    try {
      registry = LocateRegistry.getRegistry(9999);          // find the object registry

      rmiServer = ((RemotePrintServer)(registry.lookup("server"))); //!cast to server class!

      rmiServer.print("Hello World"); //$NON-NLS-1$
    } catch (Throwable t) {
      t.printStackTrace();
    }
  }
}
