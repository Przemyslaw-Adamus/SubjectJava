package warehouse;

import java.util.Comparator;

public class ItemComparator implements Comparator<Item> {
    //@Override
    public int compare(Item o1, Item o2) {
        return o2.quantity - o1.quantity;
    }
}