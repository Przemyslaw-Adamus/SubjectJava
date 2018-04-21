package warehouse;

import java.util.*;

public class FulfillmentCenter {
    String name;
    List<Item> listItems;
    Double maxLoad;

    public FulfillmentCenter(String name, Double maxLoad) {
        this.name = name;
        this.listItems = new ArrayList<Item>();
        this.maxLoad = maxLoad;
    }

    public double summaryLoad() {
        double summaryLoad=0;
        for (Item items : listItems) {
            summaryLoad += items.mass * items.quantity;
        }
        return summaryLoad;
    }
    public void addProduct(Item item) {
        double summaryLoad = summaryLoad();
        if(summaryLoad+item.mass*item.quantity <= maxLoad){
            int index = listItems.indexOf(item);
            if(item.quantity>=0)
            {
                if(index==-1) {
                    System.out.println("Add new item: "+item.name);
                    listItems.add(item);
                }
                else {
                    //System.err.println("Increased amount "+listItems.get(index).name);
                    System.out.println("Increased amount "+listItems.get(index).name);
                    listItems.get(index).quantity+=item.quantity;
                }
            }
        }
        else System.out.println("Full warehouse");
    }

    public Item getProduct(Item item) throws Exception {
        Iterator<Item> iterator = listItems.iterator();
        while (iterator.hasNext()) {
            Item tmpItem = iterator.next();
            if (tmpItem.compareTo(item) == 0) {
                tmpItem.quantity -= 1;
                if (tmpItem.quantity == 0)
                    iterator.remove();
                System.out.println("Get " + tmpItem.name);
                return new Item(tmpItem.name, tmpItem.condition, tmpItem.mass, 1);
            }
        }
        return new Item(item.name, item.condition, item.mass, 0);
    }

    public boolean removeProduct(Item item) throws IllegalArgumentException {
        boolean firstItem=false;
        Iterator<Item> iterator = listItems.iterator();
        while (iterator.hasNext()) {
            Item tmpItem = iterator.next();
            if (tmpItem.compareTo(item) == 0) {
                iterator.remove();
                System.out.println(tmpItem.name + " successfully removed!");
                firstItem=true;
            }
        }
        if(firstItem==false){
            throw new IllegalArgumentException("There is any product tike this");
        }
        System.out.println("There is no such object!");
        return true;
    }

//    public Item search(String name) {
//        Iterator<Item> iterator = listItems.iterator();
//        Item itemName;
//        itemName = new Item(name, null, null, null);
//        while (iterator.hasNext()) {
//            Item tmpItem = iterator.next();
//            if (tmpItem.compareTo(itemName) == 0) {
//                return tmpItem;
//            }
//        }
//        return null;
//    }

    public Item search(String productName) throws Exception {
        Item searchName;                          //
        List<Item> copyList = new ArrayList<>();
        for (Item element : listItems) {
            Item copy = new Item(element);
            copy.name = copy.name.toLowerCase();
            copyList.add(copy);
        }
        Collections.sort(copyList);

        int index = Collections.binarySearch(copyList, new Item(productName, null, null, null),
                new Comparator<Item>() {
                    @Override
                    public int compare(Item o1, Item o2) {
                        return o1.compareTo(o2);
                    }
                });
        if (index >= 0)
            searchName = copyList.get(index);
        else
            searchName = new Item(null, null, null, 0);
        return searchName;
    }

    public List<Item> searchPartial(String name){
        List<Item> results = new ArrayList();
        Iterator<Item> iterator = listItems.iterator();
        while (iterator.hasNext()) {
            Item tmpItem = iterator.next();
            if (tmpItem.name.contains(name)) {
                results.add(tmpItem);
            }
        }
        return results;
    }

//    public void summary() {
//        System.out.println("WAREHAUSE:");
//        for(Item items  : listItems) {
//            items.print();
//        }
//    }

    public int countByCondition(ItemCondition itemCondition) {
        int result = 0;
        for (Item items  : listItems) {
            if(itemCondition == items.condition)
                result += items.quantity;
        }
        return result;
    }

    public List<Item> sortByName() {
      //  List<Item> result = new ArrayList<>();
        //for (Item items  : listItems)
          //  result.add(new Item(items));
        Collections.sort(listItems);
        return listItems;
    }

    public List<Item> sortByAmount() {
        //List<Item> result = new ArrayList<>();
        //for (Item items  : listItems)
          //  result.add(new Item(items));
        Collections.sort(listItems, new ItemComparator());
        return listItems;
    }

    public Item max(){
        return Collections.max(listItems);
    }
}
