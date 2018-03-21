import java.util.Comparator;
import java.util.InputMismatchException;

public class Item implements Print, Comparable<Item> {
    String name;
    ItemCondition condition;
    Double mass;
    Integer quantity;

    public Item(String name, ItemCondition condition, Double mass, Integer quantity) throws Exception{

            if(mass<=0) throw new Exception("ERROR INPUT");
            this.name = name;
            this.condition = condition;
            this.mass = mass;
            this.quantity = quantity;
    }

    public Item(Item item) {
        this.name = item.name;
        this.condition = item.condition;
        this.quantity = item.quantity;
        this.mass = item.mass;
    }

    @Override
    public void print() {
        if(this.quantity == 0)
            System.out.println("There is no such item!");
        else {
            System.out.println("Product name: " + name +
                               "\nCondition: " + condition +
                               "\nMass: " + mass +
                               "\nQuantity: " + quantity);
        }
    }

    @Override
    public int compareTo(Item o) {
        return this.name.compareTo(o.name);
    }
}
