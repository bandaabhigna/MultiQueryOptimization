import java.util.*;
public class Select {
    private Vector<String> secondaryAttributes;
    public Select()
    {

    }
    public Select(Vector<String> secondaryAttributes)
    {
        // int i;
        this.secondaryAttributes=secondaryAttributes;
        // for(i=0;i<secondaryAttributes.size();i++)
        // {
        //     this.secondaryAttributes.add(secondaryAttributes.get(i));
        // } 
    }
    public Vector<String> getSecondaryAttributes()
    {
        return secondaryAttributes;
    }
    public void setSecondaryAttributes(Vector<String> secondaryAttributes)
    {
        // int i;
        this.secondaryAttributes=secondaryAttributes;
        // for(i=0;i<secondaryAttributes.size();i++)
        // {
        //     this.secondaryAttributes.add(secondaryAttributes.get(i));
        // } 
    }
}
