import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws Exception {
        FulfillmentCenter fulfillmentCenter = new FulfillmentCenter("Grażyna", 200.0);
        try{
            Item wheat = new Item("Wheat", ItemCondition.NEW, 25.0, 1);
            fulfillmentCenter.addProduct(wheat);
            fulfillmentCenter.addProduct(wheat);
            fulfillmentCenter.addProduct(wheat);
        }catch(Exception e){
            e.getMessage();
        }
        Item rye = new Item("Rye", ItemCondition.NEW, 20.0, 1);
        Item corn = new Item("Corn", ItemCondition.NEW, 30.0, 1);
        // TEST addProduct() - Add 3 wheat and 1 rye and 3 corn. Last corn should not add.
        System.out.println("_________________________________________");

        fulfillmentCenter.addProduct(rye);
        fulfillmentCenter.addProduct(corn);
        fulfillmentCenter.addProduct(corn);
        System.out.println(fulfillmentCenter.summaryLoad());
        fulfillmentCenter.addProduct(corn); //Last corn should not add.
        //TEST getProduct()
        System.out.println("_________________________________________");
        //Item wheatTest = fulfillmentCenter.getProduct(wheat);
        System.out.println(fulfillmentCenter.summaryLoad());
        //TEST removeProduct()
        Item itemDelete = new Item("Corn", ItemCondition.NEW, 30.0, 1);
        fulfillmentCenter.removeProduct(itemDelete);
        fulfillmentCenter.addProduct(corn);
        //TEST Search()
        System.out.println("_________________________________________");
        Item ryeTest = fulfillmentCenter.search("Rye");
        ryeTest.print();
        //TEST SearchPartial()
        System.out.println("_________________________________________");
        List<Item> listResultsTest = new ArrayList();
        listResultsTest=fulfillmentCenter.searchPartial("Co");
        for(Item results : listResultsTest) results.print();
        //TEST Summary()
        System.out.println("_________________________________________");
        fulfillmentCenter.summary();
        //TEST countByCondition()
        System.out.println("_________________________________________");
        System.out.println("Amount NEW items: "+fulfillmentCenter.countByCondition(ItemCondition.NEW));
        //TEST sortByName()
        System.out.println("_________________________________________");
        List<Item> listSortByName = new ArrayList();
        listSortByName=fulfillmentCenter.sortByName();
        for(Item results : listSortByName) results.print();
        System.out.println();
        fulfillmentCenter.summary();
        //TEST sortByAmount()
        System.out.println("_________________________________________");
        List<Item> listsortByAmount = new ArrayList();
        listsortByAmount=fulfillmentCenter.sortByAmount();
        for(Item results : listsortByAmount) results.print();
        System.out.println();
        fulfillmentCenter.summary();
        //TEST max()
        System.out.println("_________________________________________");
        fulfillmentCenter.max().print();


        FulfillmentCenterContainer fulfillmentCenterContainer = new FulfillmentCenterContainer();
        //TEST addCenter()
        System.out.println("_________________________________________");
        fulfillmentCenterContainer.addCenter("Magazyn",1000);
        fulfillmentCenterContainer.addCenter("Krystyna",1000);
       // fulfillmentCenterContainer.mapWarehaus.get("Magazyn").addProduct(wheat);
        fulfillmentCenterContainer.mapWarehaus.get("Magazyn").addProduct(corn);
        //TEST summary()
        System.out.println("_________________________________________");
        fulfillmentCenterContainer.summary();
        //TEST findEmpty()()
        System.out.println("_________________________________________");
        List<FulfillmentCenter> result = new ArrayList<>();
        result=fulfillmentCenterContainer.findEmpty();
        for( FulfillmentCenter r : result) System.out.println(r.name);
        //TEST removeCenter()
        System.out.println("_________________________________________");
        fulfillmentCenterContainer.removeCenter("Krystyna");
        fulfillmentCenterContainer.summary();

    }
}
