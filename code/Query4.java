import java.util.*;
public class Query4 {
    private Select select;
    private From from;
    private Where where;
    private Operator2 operator;
    private int f1r;
    private int f2r;
    private int f3r;
    private String st;
    public Query4()
    {

    }
    public Query4(Select select, From from, Where where, Operator2 operator, int f1r, int f2r, int f3r, String st)
    {
        this.select=select;
        this.from=from;
        this.where=where;
        this.operator=operator;
        this.f1r=f1r;
        this.f2r=f2r;
        this.f3r=f3r;
        this.st=st;
    }
    public Select getSelect()
    {
        return select;
    }
    public void setSelect(Select select)
    {
        this.select=select;
    }
    public From getFrom()
    {
        return from;
    }
    public void setFrom(From from)
    {
        this.from=from;
    }
    public Where getWhere()
    {
        return where;
    }
    public void setWhere(Where where)
    {
        this.where=where;
    }
    public Operator2 getOperator()
    {
        return operator;
    }
    public void setOperator(Operator2 operator)
    {
        this.operator=operator;
    }
    public int getF1r()
    {
        return f1r;
    }
    public void setF1r(int f1r)
    {
        this.f1r=f1r;
    }
    public int getF2r()
    {
        return f2r;
    }
    public void setF2r(int f2r)
    {
        this.f2r=f2r;
    }
    public int getF3r()
    {
        return f3r;
    }
    public void setF3r(int f3r)
    {
        this.f3r=f3r;
    }
    public String getSt()
    {
        return st;
    }
    public void setSt(String st)
    {
        this.st=st;
    }

}

