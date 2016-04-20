package warehouseServer;

import java.util.HashMap;
import java.util.Map;

public class Warehouse implements WarehouseInterface {
  private final Map<String, Integer> stock;

  public Warehouse() {
    super();
    this.stock = new HashMap<>();
  }

  public synchronized int getStock(final String type) {
    Integer i = stock.get(type);
    return (i == null) ? 0 : i.intValue();
  }

  public synchronized void changeStock(final String type, final int add) {
    Integer i = this.stock.get(type);
    i = Integer.valueOf(((i != null) ? i.intValue() : 0) + add);
    this.stock.put(type, i);
  }
}
