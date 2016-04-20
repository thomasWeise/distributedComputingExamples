package warehouseClient;

import java.net.URL;

import com.googlecode.jsonrpc4j.JsonRpcHttpClient;
import com.googlecode.jsonrpc4j.ProxyUtil;

public class WarehouseTest {
  public static void main(String[] args) throws Exception {
    JsonRpcHttpClient client;
    WarehouseInterface warehouse;

    client = new JsonRpcHttpClient(new URL("http://localhost:8080/"));

    warehouse = ProxyUtil.createClientProxy(
        WarehouseTest.class.getClassLoader(), WarehouseInterface.class,
        client);

    System.out.println("# of cars: " + warehouse.getStock("cars"));
    System.out.println("# of cats: " + warehouse.getStock("cats"));
    warehouse.changeStock("cars", 100);
    warehouse.changeStock("cats", 120);
    System.out.println("# of cars: " + warehouse.getStock("cars"));
    System.out.println("# of cats: " + warehouse.getStock("cats"));
    warehouse.changeStock("cats", -12);
    System.out.println("# of cars: " + warehouse.getStock("cars"));
    System.out.println("# of cats: " + warehouse.getStock("cats"));
  }
}
