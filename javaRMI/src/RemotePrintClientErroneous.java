import java.rmi.registry.LocateRegistry;      import java.rmi.registry.Registry;

public class RemotePrintClientErroneous { // the erroneous remote print client
  public static final void main(final String args[]) {
    RemotePrintInterface rmiServer;         Registry registry;

    try {
      // find the object registry
      registry = LocateRegistry.getRegistry(9999);

      //! invalid cast to server class !
      rmiServer = ((RemotePrintServer)(registry.lookup("server"))); 

      rmiServer.print("Hello World"); //$NON-NLS-1$
    } catch (Throwable t) {
      t.printStackTrace();
    }
  }
}
