import java.util.*;
public class PrimaryAttributesMap {
    Map<String, Integer> map=new HashMap<String, Integer>();  
    public PrimaryAttributesMap()
    {   
        map.put("ID",1);
        map.put("Name",2);
        map.put("Age",3);
        map.put("Weight",4);
        map.put("Dept",5);
        map.put("Salary",6);
        map.put("GPA",7);
        map.put("Title",8);
        map.put("Semester",9);
    }
    public int getRank(String name)
    {
        return map.get(name);
    }
    
}