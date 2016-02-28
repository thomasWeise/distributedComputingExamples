package myServlets;

import java.io.IOException;            import javax.servlet.http.HttpServletResponse;  
import java.io.PrintWriter;            import javax.servlet.http.HttpServletRequest;
import javax.servlet.ServletException; import javax.servlet.http.HttpServlet;


public class HelloWorldServlet extends HttpServlet {

  // this method is called by the servlet container for incoming GET requests
  @Override
  public void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {

    resp.setContentType("text/plain"); // state that we will send a normal text file
    PrintWriter out = resp.getWriter(); // get the writer to write our response

    out.write("Hello to " + req.getRemoteHost() + ":" + req.getRemotePort()
        + " from " + req.getLocalName() + ":" + req.getLocalPort() + ".");
  }
}
