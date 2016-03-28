package warehouseClient;

public class ChangeStockTest {
  public static void main(String[] args) {
    WarehouseServiceStub stub;        WarehouseServiceStub.ChangeStock request;

    try {
      stub = new WarehouseServiceStub(); // Creating client stub.

      request = new WarehouseServiceStub.ChangeStock();   // prepare "changeStock" call
      request.setType("cars");
      request.setAdd(100);

      stub.changeStock(request);     // make service call
    } catch (Throwable f) {
      f.printStackTrace();
    }
  }
}
