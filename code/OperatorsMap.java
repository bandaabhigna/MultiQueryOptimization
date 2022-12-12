import java.util.*;
public class OperatorsMap {
    Map<String, Integer> map=new HashMap<String, Integer>();  
    public OperatorsMap()
    {   
        map.put("<",0);
        map.put(">",1);
        map.put("<=",0);
        map.put(">=",1);
        map.put("=",2);
    }
    public int getRank(String name)
    {
        return map.get(name);
    }
}