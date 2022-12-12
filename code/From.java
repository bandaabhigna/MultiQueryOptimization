import java.util.*;
public class From {
    private Vector<String> tablenames;
    public From()
    {

    }
    public From(Vector<String> tablenames)
    {
        this.tablenames=tablenames;
        // int i;
        // for(i=0;i<tablenames.size();i++)
        // {
        //     this.tablenames.add(tablenames.get(i));
        // } 
    }
    public Vector<String> getTablenames()
    {
        return tablenames;
    }
    public void setTablenames(Vector<String> tablenames)
    {
        this.tablenames=tablenames;
        // int i;
        // for(i=0;i<tablenames.size();i++)
        // {
        //     this.tablenames.add(tablenames.get(i));
        // } 
    }
}
