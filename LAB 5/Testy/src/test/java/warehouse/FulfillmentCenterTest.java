package warehouse;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

public class FulfillmentCenterTest {

    private FulfillmentCenter fulfillmentCenter;
    private Item itemAtList;
    private Item itemAtList2;

    @Before
    public void setUp() {
        try {
            itemAtList=new Item("Item",ItemCondition.NEW,3.0,4);
            itemAtList2=new Item("A",ItemCondition.NEW,3.0,6);
        } catch (Exception e) {
            e.printStackTrace();
        }
        fulfillmentCenter=new FulfillmentCenter("name",300.0);
        fulfillmentCenter.addProduct(itemAtList);
        try {
            fulfillmentCenter.addProduct(itemAtList2);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Test
    public void summaryLoadTest() {
        double result=fulfillmentCenter.summaryLoad();
        assertTrue(result==30.0);
    }

    @Test
    public void addProductTestShouldntAdd() {
        int size=fulfillmentCenter.listItems.size();
        try {
            Item itemTest=new Item("Item",ItemCondition.NEW,3.0,-1);
            fulfillmentCenter.addProduct(itemTest);
        } catch (Exception e) {
            e.printStackTrace();
        }
        int newSize=fulfillmentCenter.listItems.size();
        assertEquals(fulfillmentCenter.listItems.size(),newSize);
    }
    @Test
    public void addProductTestShouldAdd() {
        int size=fulfillmentCenter.listItems.size();
        try {
            Item itemTest=new Item("Item",ItemCondition.NEW,3.0,4);
            fulfillmentCenter.addProduct(itemTest);
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertEquals(fulfillmentCenter.listItems.size(),size+1);
    }

    @Test
    public void getProductTest() {
      //Item itemTest=new Item("Item",ItemCondition.NEW,3.0,4);
      //itemTest=fulfillmentCenter.getProduct(itemAtList);

    }

    @Test(expected = IllegalArgumentException.class)
    public void removeProductTestShouldThrowsException() {
        assertTrue(fulfillmentCenter.removeProduct(itemAtList2));
        fulfillmentCenter.removeProduct(itemAtList2);
    }
    @Test
    public void removeProductTestShouldBeTrue() {
        assertTrue(fulfillmentCenter.removeProduct(itemAtList2));
    }

    @Test
    public void searchTest() {

        try {
            Item itemTest=fulfillmentCenter.search("Item");
            assertEquals(itemTest, itemAtList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void searchPartialTest() {
        List<Item> results;
        results=fulfillmentCenter.searchPartial("Ite");
        assertEquals(results.get(0), itemAtList);
    }

//    @Test
//    public void summaryTest() {
//    }

    @Test
    public void countByConditionTestShouldBeEquals() {
        int result = fulfillmentCenter.countByCondition(ItemCondition.NEW);
        assertEquals(result, 10);
    }
    @Test
    public void countByConditionTestShouldByNoEquals() {
        int result = fulfillmentCenter.countByCondition(ItemCondition.NEW);
        assertNotEquals(result, 2);
    }

    @Test
    public void sortByNameTest() {
        List<Item> results;
        results=fulfillmentCenter.sortByName();
        List<Item> listSort=new ArrayList<>();
        try {
            listSort.add(itemAtList2);
            listSort.add(itemAtList);
            for(int i=0; i<listSort.size();i++) {
                assertEquals(results.get(i), listSort.get(i));

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void sortByAmountTest() {
        List<Item> results;
        results=fulfillmentCenter.sortByName();
        List<Item> listSort=new ArrayList<>();
        try {
            listSort.add(itemAtList2);
            listSort.add(itemAtList);
            for(int i=0; i<listSort.size();i++) {
                assertEquals(results.get(i), listSort.get(i));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void maxTest() {
        Item result=fulfillmentCenter.max();
        assertEquals(result,itemAtList);
    }

}