package warehouseClient;

public class GetStockTest {
  public static void main(String[] args) {
    WarehouseServiceStub stub;   WarehouseServiceStub.GetStock request;
    WarehouseServiceStub.GetStockResponse response;

    try {
      stub = new WarehouseServiceStub();             // Creating client stub.

      request = new WarehouseServiceStub.GetStock();                  // prepare "getStock" call
      request.setType("cars");

      response = stub.getStock(request);         // execute call
      System.out.println(response.get_return()); // print result
    } catch (Throwable f) {
      f.printStackTrace();
    }
  }
}
