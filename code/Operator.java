public class Operator {
    private String op;
    private int val;
    public Operator()
    {

    }
    public Operator(String op, int val)
    {
        this.op=op;
        this.val=val;
    }
    public String getOp()
    {
        return op;
    }
    public void setOp(String op)
    {
        this.op=op;
    }
    public int getVal()
    {
        return val;
    }
    public void setVal(int val)
    {
        this.val=val;
    }

}

