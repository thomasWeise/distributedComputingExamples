package warehouseServer;

import java.util.HashMap;         import java.util.Map;

public class Warehouse {
  private final Map<String,Integer> stock;
  
  public Warehouse(){
    super();
    this.stock = new HashMap<>();
  }
  
  public synchronized Integer getStock(final String type) {
    Integer i = stock.get(type);
    if(i == null) { return Integer.valueOf(0); }    
    return i;
  }
  
  public synchronized void changeStock(final String type, final Integer add) {
    Integer i = this.stock.get(type);
    if(i != null) { i = Integer.valueOf(i.intValue() + add.intValue()); }
    else          { i = add; }    
    this.stock.put(type,  i);
  }  
}  
