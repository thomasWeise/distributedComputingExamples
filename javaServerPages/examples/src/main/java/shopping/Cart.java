package shopping;

import java.io.Serializable; import java.util.ArrayList;

public class Cart implements Serializable {
  private ArrayList<String> contents; // the item list

  public Cart() {
    this.contents = new ArrayList<>();
  }

  /** write-only property "add" */
  public void setAdd(String name) {
    this.contents.add(name);
  }

  /** write-only property "remove" */
  public void setRemove(String name) {
    this.contents.remove(name);
  }

  /** create read-only property "items" */
  public String[] getItems() {
    return this.contents.toArray(new String[this.contents.size()]);
  }
}