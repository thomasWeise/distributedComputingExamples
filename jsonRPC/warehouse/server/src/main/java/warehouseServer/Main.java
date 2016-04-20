package warehouseServer;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;

import org.apache.tomcat.InstanceManager;
import org.apache.tomcat.SimpleInstanceManager;
import org.eclipse.jetty.annotations.ServletContainerInitializersStarter;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.webapp.WebAppContext;

/**
 * The main entry point for our warehouse JSON RPC server. It instantiates
 * the embedded Jetty server and setups up our {@linkplain WarehouseServlet
 * warehouse servlet}.
 */
public class Main {
  /**
   * This is the main routine, which starts the application
   *
   * @param args
   *          the command line arguments
   * @throws Exception
   *           if it has to
   */
  @SuppressWarnings("resource")
  public static void main(final String[] args) throws Exception {
    final Server server;
    final WebAppContext context;

    server = new Server();
    try (ServerConnector connector = new ServerConnector(server)) {
      connector.setPort(8080);
      server.addConnector(connector);

      context = new WebAppContext();
      context.setContextPath("/"); //$NON-NLS-1$
      context.setAttribute("javax.servlet.context.tempdir", //$NON-NLS-1$
          Main.__getScratchDir());

      context.setResourceBase(Main.class.getResource("/webapp/") //$NON-NLS-1$
          .toURI().toASCIIString());

      context.setAttribute(InstanceManager.class.getName(),
          new SimpleInstanceManager());

      context.addBean(//
          new ServletContainerInitializersStarter(context), true);
      context.setClassLoader(
          new URLClassLoader(new URL[0], Main.class.getClassLoader()));

      context.addServlet(WarehouseServlet.class, "/");//$NON-NLS-1$

      server.setHandler(context);

      // start the server and wait for termination
      server.start();
      server.join();
    }
  }

  /**
   * Create the directory in which Jetty will store the generated java
   * classes
   *
   * @return the directory for the java classes
   * @throws IOException
   *           if I/O fails
   */
  private static final File __getScratchDir() throws IOException {
    final File tempDir, scratchDir;

    tempDir = new File(System.getProperty("java.io.tmpdir"));//$NON-NLS-1$
    scratchDir = new File(tempDir.toString(), "embedded-jetty-jsp");//$NON-NLS-1$

    if (!scratchDir.exists()) {
      if (!scratchDir.mkdirs()) {
        throw new IOException("Unable to create scratch directory: "//$NON-NLS-1$
            + scratchDir);
      }
    }

    return scratchDir;
  }
}
