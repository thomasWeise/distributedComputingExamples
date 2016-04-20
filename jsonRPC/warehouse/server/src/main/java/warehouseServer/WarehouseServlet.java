package warehouseServer;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.googlecode.jsonrpc4j.JsonRpcServer;

public class WarehouseServlet extends HttpServlet {

  private Warehouse service;
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
    this.service = new Warehouse();
    this.jsonRpcServer = new JsonRpcServer(new ObjectMapper(),
        this.service, WarehouseInterface.class);
  }
}
