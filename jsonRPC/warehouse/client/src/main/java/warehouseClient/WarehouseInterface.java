package warehouseClient;

public interface WarehouseInterface {

  public abstract int getStock(final String type);

  public abstract void changeStock(final String type, final int add);
}
