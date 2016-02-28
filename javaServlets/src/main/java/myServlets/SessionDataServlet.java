package myServlets;

import java.io.IOException; import java.util.Enumeration; import javax.servlet.http.HttpSession;
import java.io.PrintWriter; import java.util.Date;        import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest; import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;

public class SessionDataServlet extends HttpServlet {
  @Override
  public void service(HttpServletRequest req, HttpServletResponse res)
      throws ServletException, IOException {
    res.setContentType("text/html"); // set answer format to HTML format
    PrintWriter out = res.getWriter();

    out.println("<html><body><pre>"); // print HTML header

    HttpSession s = req.getSession(true); // get/create session
    out.println("Session: " + s.getId()); // print session id
    out.println("created: " + new Date(s.getCreationTime())); //...and info
    out.println("last access: " + new Date(s.getLastAccessedTime()));

    Enumeration e = s.getAttributeNames(); //get attributes of sessions
    while (e.hasMoreElements()) {          //and print them
      String name = (String) e.nextElement();
      String value = s.getAttribute(name).toString();
      out.println(name + "=" + value);
    }

    s.setAttribute("MyAttribute", "MyValue"); //set attribute "MyAttribute" of session
    s.setAttribute("MyAttribute2", s.getLastAccessedTime());

    out.println("</pre></body></html>"); // print HTML footer
  }
}