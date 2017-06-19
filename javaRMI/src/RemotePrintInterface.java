import java.rmi.Remote;
import java.rmi.RemoteException;

// the RemotePrint interface
public interface RemotePrintInterface extends Remote {

  /** A method to be remote-accessible
   * @param what the string to print
   * @throws RemoteException a possible exception */
  public abstract void print(final String what) throws RemoteException;

}
