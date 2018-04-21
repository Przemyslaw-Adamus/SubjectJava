package warehouse;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;

import static org.junit.Assert.*;

public class ItemTest {
    Item item1;
    Item item2;
    Item item3;

    @Before
    public void setUp(){
        try {
            item3=new Item("Item3",ItemCondition.NEW,3.0,4);
            item2=new Item("Item",ItemCondition.NEW,3.0,4);
            item1=new Item("Item",ItemCondition.NEW,3.0,4);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Test
    public void print() {
        item1.print();
    }

    @Test
    public void compareToTestShouldBeGood() {
        int result=item1.name.compareTo(item2.name);
        assertEquals(result, 0);
    }

    @Test
    public void compareToTestShouldBeBad() {
        int result=item1.name.compareTo(item3.name);
        assertEquals(result, -1);
    }

    @Test
    public void getNameTest() {
        String result=item1.getName();
        assertEquals(result,item1.name);
    }

    @Test
    public void getConditionTest() {
        ItemCondition result=item1.getCondition();
        assertEquals(result,item1.condition);
    }

    @Test
    public void getMassTest () {
        double result= item1.getMass();
        if(result==3.0) assertTrue(true);
    }

    @Test
    public void getQuantityTest() {
        int result=item1.getQuantity();
        assertEquals(result,4);
    }
}