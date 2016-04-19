package calculator;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.googlecode.jsonrpc4j.JsonRpcServer;

public class CalculatorServlet extends HttpServlet {

  private Calculator userService;
  private JsonRpcServer jsonRpcServer;

  @Override
  protected void doPost(final HttpServletRequest req,
      final HttpServletResponse resp) throws IOException {
    this.jsonRpcServer.handle(req, resp);
  }

  @Override
  protected void doGet(final HttpServletRequest req,
      final HttpServletResponse resp) throws IOException {
    this.jsonRpcServer.handle(req, resp);
  }

  @Override
  public void init(final ServletConfig config) {
    this.userService = new Calculator();
    this.jsonRpcServer = new JsonRpcServer(new ObjectMapper(),
        this.userService, CalculatorInterface.class);
  }
}
