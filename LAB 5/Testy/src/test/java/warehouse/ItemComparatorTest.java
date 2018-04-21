package warehouse;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ItemComparatorTest {
    ItemComparator itemComparator;
    Item item1;
    Item item2;
   // Item item3;
    @Before
    public void setUp(){
        try {
            item1=new Item("Item1",ItemCondition.NEW,3.0,4);
            item2=new Item("Item1",ItemCondition.NEW,3.0,4);
            itemComparator=new ItemComparator();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Test
    public void compareTest() {
       int result=itemComparator.compare(item1,item2);
       assertEquals(result,0);
    }
    @Test
    public void compareTestTrue() {
        int result=itemComparator.compare(item1,item2);
        assertTrue(result==0);
    }
}