package warehouse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FulfillmentCenterContainer {
    Map<String, FulfillmentCenter> mapWarehaus;

    public FulfillmentCenterContainer() {
        this.mapWarehaus = new HashMap<>();
    }

    public void addCenter(String name, double maxLoad){
        FulfillmentCenter fulfillmentCenter = new FulfillmentCenter(name,maxLoad);
        mapWarehaus.put(name,fulfillmentCenter);
        System.out.println("The addition was successful!");
    }

//    public void removeCenter(String nameWarehaus){
//        if(mapWarehaus.containsKey(nameWarehaus))
//        {
//            mapWarehaus.remove(nameWarehaus);
//            System.out.println("The delete was successful!");
//        }
//        else{
//            System.out.println("Ther is no waryhause!");
//        }
//    }

    public List<FulfillmentCenter>findEmpty(){
        List<FulfillmentCenter> result = new ArrayList<>();
        for(String s : mapWarehaus.keySet()){
            if(mapWarehaus.get(s).summaryLoad()==0) result.add(mapWarehaus.get(s));
        }
        return result;
    }
//
//    public void summary() {
//        if(mapWarehaus.isEmpty()){
//            System.out.println("List is empty!");
//            return;
//        }
//        //System.out.print(mapWarehaus.size());
//        for(String s : mapWarehaus.keySet()){
//            System.out.print(mapWarehaus.get(s).name + "   ");
//            System.out.print(mapWarehaus.get(s).summaryLoad()/mapWarehaus.get(s).maxLoad*100);
//            System.out.println(" %");
//            }
//    }
}
