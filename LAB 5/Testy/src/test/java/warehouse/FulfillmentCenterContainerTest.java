package warehouse;

import org.junit.Test;
import org.junit.Before;

import static org.junit.Assert.*;

public class FulfillmentCenterContainerTest {

    FulfillmentCenterContainer fulfillmentCenterContainer;
    @Before
    public void setUp(){
      //fulfillmentCenterContainer.addCenter("Magazyn",1000);
//        fulfillmentCenterContainer.addCenter("Krystyna",1000);
            try {
                Item wheat = new Item("Wheat", ItemCondition.NEW, 25.0, 1);
                fulfillmentCenterContainer.mapWarehaus.get("Magazyn").addProduct(wheat);
            } catch (Exception e) {
                e.printStackTrace();
            }
    }

    @Test
    public void addCenter() {
//        int size=fulfillmentCenterContainer.mapWarehaus.size();
//        //fulfillmentCenterContainer.addCenter("3",30.0);
//        assertEquals(fulfillmentCenterContainer.mapWarehaus.size(),size+1);
    }

//    @Test
//    public void removeCenter() {
//    }

    @Test
    public void findEmpty() {
    }

//    @Test
//    public void summary() {
//    }
}