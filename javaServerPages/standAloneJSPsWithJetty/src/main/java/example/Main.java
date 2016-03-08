//
// This file is derived from org.eclipse.jetty.demo.Main.java
// https://github.com/jetty-project/embedded-jetty-jsp/blob/master/src/main/java/org/eclipse/jetty/demo/Main.java
// from the GitHub project jetty-project/embedded-jetty-jsp available at
// https://github.com/jetty-project/embedded-jetty-jsp.
//
// This file is a strongly modified version of that file.
// The original file has the following copyright information:
//
// ========================================================================
// Copyright (c) 1995-2013 Mort Bay Consulting Pty. Ltd.
// ------------------------------------------------------------------------
// All rights reserved. This program and the accompanying materials
// are made available under the terms of the Eclipse Public License v1.0
// and Apache License v2.0 which accompanies this distribution.
//
// The Eclipse Public License is available at
// http://www.eclipse.org/legal/epl-v10.html
//
// The Apache License v2.0 is available at
// http://www.opensource.org/licenses/apache2.0.php
//
// You may elect to redistribute this code under either of these licenses.
// ========================================================================
//
// The present file is under the Apache License v2.0.
package example;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;

import org.apache.tomcat.InstanceManager;
import org.apache.tomcat.SimpleInstanceManager;
import org.eclipse.jetty.annotations.ServletContainerInitializersStarter;
import org.eclipse.jetty.apache.jsp.JettyJasperInitializer;
import org.eclipse.jetty.jsp.JettyJspServlet;
import org.eclipse.jetty.plus.annotation.ContainerInitializer;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.webapp.WebAppContext;

/** The main entry point for our embedded Java Server Pages. */
public class Main {
  /**
   * This is the main routine, which starts the application
   *
   * @param args
   *          the command line arguments
   * @throws Exception
   *           if it has to
   */
  public static void main(final String[] args) throws Exception {
    final Server server;
    final WebAppContext webAppContext;

    // set JSP to always use the installed JDK's javac
    System.setProperty("org.apache.jasper.compiler.disablejsr199", //$NON-NLS-1$
        "false"); //$NON-NLS-1$

    server = new Server();
    try (ServerConnector connector = new ServerConnector(server)) {
      connector.setPort(8080);
      server.addConnector(connector);

      webAppContext = Main.__getWebAppContext(//
          Main.class.getResource("/webroot/").toURI(), //$NON-NLS-1$
          Main.__getScratchDir());

      server.setHandler(webAppContext);

      // start the server and wait for termination
      server.start();
      if (Desktop.isDesktopSupported()) {
        Desktop.getDesktop().browse(URI.create("http://localhost:8080"));//$NON-NLS-1$
      }
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

  /**
   * Setup the basic application "context" for this application at "/" This
   * is also known as the handler tree (in jetty speak)
   *
   * @param baseUri
   *          the base uri
   * @param scratchDir
   *          the scratch directory
   * @return the web app context
   */
  private static final WebAppContext __getWebAppContext(final URI baseUri,
      final File scratchDir) {
    WebAppContext context;

    context = new WebAppContext();
    context.setContextPath("/"); //$NON-NLS-1$
    context.setAttribute("javax.servlet.context.tempdir", scratchDir);//$NON-NLS-1$
    context.setAttribute(
        "org.eclipse.jetty.server.webapp.ContainerIncludeJarPattern", //$NON-NLS-1$
        ".*/[^/]*servlet-api-[^/]*\\.jar$|.*/javax.servlet.jsp.jstl-.*\\.jar$|.*/.*taglibs.*\\.jar$");//$NON-NLS-1$

    context.setResourceBase(baseUri.toASCIIString());
    context.setAttribute("org.eclipse.jetty.containerInitializers", //$NON-NLS-1$
        Main.__jspInitializers());

    context.setAttribute(InstanceManager.class.getName(),
        new SimpleInstanceManager());

    context.addBean(//
        new ServletContainerInitializersStarter(context), true);
    context.setClassLoader(Main.__getUrlClassLoader());

    context.addServlet(Main.__jspServletHolder(), "*.jsp");//$NON-NLS-1$
    context.addServlet(ExampleServlet.class, "/servlet");//$NON-NLS-1$
    context.addServlet(Main.__defaultServletHolder(baseUri), "/");//$NON-NLS-1$
    return context;
  }

  /**
   * Ensure the JSP engine is initialized correctly
   *
   * @return the JSP initializers
   */
  private static final List<ContainerInitializer> __jspInitializers() {
    final JettyJasperInitializer sci;
    final ContainerInitializer initializer;
    final List<ContainerInitializer> initializers;

    sci = new JettyJasperInitializer();
    initializer = new ContainerInitializer(sci, null);
    initializers = new ArrayList<>();
    initializers.add(initializer);
    return initializers;
  }

  /**
   * Set {@link java.lang.ClassLoader} of the context to be sane (needed
   * for JSTL). JSP requires a non-System {@link java.lang.ClassLoader}.
   * This method simply wraps the embedded System
   * {@link java.lang.ClassLoader} in a way that makes it suitable for JSP
   * to use.
   *
   * @return the {@link java.lang.ClassLoader}
   */
  private static final ClassLoader __getUrlClassLoader() {
    return new URLClassLoader(new URL[0], Main.class.getClassLoader());
  }

  /**
   * Create JSP Servlet (must be named "jsp")
   *
   * @return the servlet holder
   */
  private static final ServletHolder __jspServletHolder() {
    ServletHolder holderJsp;

    holderJsp = new ServletHolder("jsp", JettyJspServlet.class); //$NON-NLS-1$
    holderJsp.setInitOrder(0);
    holderJsp.setInitParameter("compilerTargetVM", "1.7");//$NON-NLS-1$//$NON-NLS-2$
    holderJsp.setInitParameter("compilerSourceVM", "1.7");//$NON-NLS-1$//$NON-NLS-2$
    holderJsp.setInitParameter("validating", "true");//$NON-NLS-1$//$NON-NLS-2$
    holderJsp.setInitParameter("enableTldValidation", "true");//$NON-NLS-1$//$NON-NLS-2$
    holderJsp.setInitParameter("trimSpaces", "true");//$NON-NLS-1$//$NON-NLS-2$
    holderJsp.setInitParameter("keepgenerated", "false");//$NON-NLS-1$//$NON-NLS-2$
    holderJsp.setInitParameter("saveByteCode", "false");//$NON-NLS-1$//$NON-NLS-2$
    return holderJsp;
  }

  /**
   * Create Default Servlet (must be named "default")
   *
   * @param baseUri
   *          the base uri
   * @return the servlet
   */
  private static final ServletHolder __defaultServletHolder(
      final URI baseUri) {
    ServletHolder holderDefault;

    holderDefault = new ServletHolder("default", //$NON-NLS-1$
        DefaultServlet.class);

    holderDefault.setInitParameter(//
        "resourceBase", baseUri.toASCIIString());//$NON-NLS-1$
    holderDefault.setInitParameter("dirAllowed", "false");//$NON-NLS-1$//$NON-NLS-2$
    return holderDefault;
  }
}
