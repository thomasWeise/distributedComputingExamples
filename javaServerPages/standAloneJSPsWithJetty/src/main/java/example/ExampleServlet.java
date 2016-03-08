package example;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/** An example for a Java Servlet. */
@SuppressWarnings("serial")
public class ExampleServlet extends HttpServlet {
  /** {@inheritDoc} */
  @SuppressWarnings("resource")
  @Override
  protected void doGet(final HttpServletRequest req,
      final HttpServletResponse resp) throws ServletException, IOException {
    final PrintWriter out = resp.getWriter();
    out.println("<html><head><title>Cool Servlet!</title></head><body>"); //$NON-NLS-1$
    out.println("<h1>Cool Servlet!</h1><pre>"); //$NON-NLS-1$
    out.println("Method=" + req.getMethod());//$NON-NLS-1$
    out.println("URI=" + req.getRequestURI());//$NON-NLS-1$
    out.println("RemoteAddr=" + req.getRemoteAddr());//$NON-NLS-1$

    out.println("\nRequest headers:");//$NON-NLS-1$
    Enumeration<String> e = req.getHeaderNames();
    while (e.hasMoreElements()) {
      final String name = e.nextElement();
      out.println(name + "=" + req.getHeader(name));//$NON-NLS-1$
    }

    out.println("\nForm data:");//$NON-NLS-1$
    e = req.getParameterNames();
    while (e.hasMoreElements()) {
      final String name = e.nextElement();
      out.println(name + "=" + req.getParameter(name));//$NON-NLS-1$
    }

    out.println("\nCoockies:");//$NON-NLS-1$
    final Cookie[] cookies = req.getCookies();
    if (cookies != null) {
      for (final Cookie c : cookies) {
        out.println(c.getName() + "=" + c.getValue());//$NON-NLS-1$
      }
    }

    final Cookie cn = new Cookie("Customer", "0815");//$NON-NLS-1$//$NON-NLS-2$
    resp.addCookie(cn);
    out.println("</pre><a href=\"/\">back</a></body></html>");//$NON-NLS-1$
  }
}
