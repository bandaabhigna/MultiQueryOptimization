import java.util.*;
public class TablenameMap {
    public Map<String, Integer> map=new HashMap<String, Integer>();  
    // public TablenameMap()
    // {

    // }
    // public TablenameMap(Map<String, Integer> map)
    // {   
    //     this.map=map;
    // }

    public TablenameMap()
    {
        map.put("Instructor",1);
        map.put("Student",2);
        map.put("Course",3);
        map.put("Instructor, Student",4);
        map.put("Course, Student",5);
        map.put("Course, Instructor",6);
        map.put("Course, Instructor, Student",7);
    }
    public int getRank(String name)
    {
        return map.get(name);
    }
    
}