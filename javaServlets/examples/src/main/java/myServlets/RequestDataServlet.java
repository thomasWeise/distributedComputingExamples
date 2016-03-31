package myServlets;

import java.io.IOException; import javax.servlet.ServletException; import java.util.Enumeration;
import java.io.PrintWriter; import javax.servlet.http.HttpServlet; import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest; import javax.servlet.http.HttpServletResponse;

public class RequestDataServlet extends HttpServlet {//extend HTTP Servlet base class

  @Override //implement the HTTP GET request handler method
  public void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    resp.setContentType("text/html"); // set answer format to HTML format

    PrintWriter out = resp.getWriter();
    out.println("<html><body><pre>"); // print HTML header
    out.println("Method=" + req.getMethod()); // print the used HTTP method
    out.println("URI=" + req.getRequestURI()); // print the requested URI
    out.println("RemoteAddr=" + req.getRemoteAddr()); //print client's address

    out.println("\nRequest headers:"); // print all the request headers
    Enumeration e = req.getHeaderNames();
    while (e.hasMoreElements()) {
      String name = ((String) (e.nextElement()));
      out.println(name + "=" + req.getHeader(name));
    }

    out.println("\nForm data:"); // print all form data/dynamic query components
    e = req.getParameterNames();
    while (e.hasMoreElements()) {
      String name = (String) (e.nextElement());
      out.println(name + "=" + req.getParameter(name));
    }

    out.println("\nCoockies:"); // print all cookies
    Cookie[] cookies = req.getCookies();
    if (cookies != null) {
      for (int i = 0; i < cookies.length; i++) {
        Cookie c = cookies[i];
        out.println(c.getName() + "=" + c.getValue());
      }
    }

    Cookie cn = new Cookie("Customer", "0815");// add a new cookie
    resp.addCookie(cn); // next time we open this page, it will be printed
    out.println("</pre></body></html>"); // print HTML footer
  }
}
