import java.nio.file.*;
import java.io.*;
import java.util.*;
import java.time.Duration;
import java.time.Instant;

public class ParserObj4
{

    //  method that reads a file name as a string 
    public static String readFileAsString(String fileName)throws Exception
    {
      String data = "";
      data = new String(Files.readAllBytes(Paths.get(fileName)));
      return data;
    }

    // method that checks if a query os declarative or not
    public static int declarative(String data)
    {
      int c=0;
      int index=data.indexOf("INSERT");
      int index1=data.indexOf("UPDATE");
      int index2=data.indexOf("DELETE");
      if(index!=-1 || index1!=-1 || index2!=-1)
      {
        c=1;
      }
      return c;
    }
  
    // method that removes order by, group by and having
    public static String orderbyGroupbyHaving(String data)
    {
      String data1;
      int index=data.indexOf("ORDER");
      if(index!=-1)
      {
        data1=data.substring(0, index-1);
      }
      else
      {
        index=data.indexOf("GROUP");
        if(index!=-1)
        {
          data1=data.substring(0, index-1);
        }
        else
        {
          data1=data;
        }
      }
      return data1;
    }

    // method that contains sql queries with no where condition
    public static int noWhere(String data)
    {
      // select Name, Age from Employees
      int index=0;
      index=data.indexOf("WHERE");
      return index;
    }

    public static List<String> andOr(String data)
    {
        // SELECT Name, Age, Salary FROM Employees WHERE Age ≤ 40
        // select attributes from table_name where 1st predicate and/or 2nd predicate
        // System.out.println("AND");
        String s1="",s2="";
        int index,index1,index2;
        index=data.indexOf("AND");
        index1=data.indexOf("OR");
        index2=data.indexOf("WHERE");
        if(index!=-1)
        {
            s1=data.substring(0, index-1);
            s2=data.substring(0,index2+5);
            s2=s2.concat(data.substring(index+3));
        }
        if(index1!=-1)
        {
            s1=data.substring(0, index1-1);
            s2=data.substring(0,index2+5);
            s2=s2.concat(data.substring(index1+2));
        }
        List<String> l=new ArrayList<String>();
        l.add(s1);
        l.add(s2);
        return l;
    }

    // method that converts queries to predicates
    //func function makes queries into predicates (NORMAL/AND/OR/NOT/BETWEEN/IN/NOT IN)
    public static List<String> func(String data)
    {
        String s1,s2,s3;
        int index,index1,index2,index3,index4,index5,t1,t;
        List<String> l1=new ArrayList<String>();
        index=data.indexOf("NOT IN");
        index1=data.indexOf("IN");
        index2=data.indexOf("BETWEEN");
        index3=data.indexOf("NOT");
        index4=data.indexOf("AND");
        index5=data.indexOf("OR");
        if(index!=-1)   //NOT IN
        {
          // System.out.println("NOT IN");
            // if there is a NOT IN, changr it to mutiple or predciates that have AND in them
            // SELECT Name, Dept, Salary FROM Instructor WHERE Age NOT IN (10,20,30) is changed to SELECT Name, Age, Salary FROM Instructor WHERE Age < 30 and SELECT Name, Age, Salary FROM Instructor WHERE Age > 10
            s1=data.substring(0,index);
            s2=data.substring(data.indexOf("(")+1, data.indexOf(")"));
            String[] arr=s2.split(",");
            for(int i=0;i<arr.length;i++)
            {
                String temp=s1;
                temp=temp.concat("< ");
                temp=temp.concat(arr[i]);
                temp=temp.concat(" AND ");
                temp=temp.concat(data.substring(data.indexOf("WHERE")+6,index-1));
                temp=temp.concat(" > ");
                temp=temp.concat(arr[i]);
                List<String> l2=andOr(temp);
                l1.addAll(l2);
            }
        }
        if(index1!=-1)  //IN
        {
          // System.out.println("IN");
            // if there is a IN, it has to be taken as multiple OR
            // SELECT Name, Age, Salary FROM Instructor WHERE Age IN (10,20,30) is changed to SELECT Name, Age, Salary FROM Instructor WHERE Age =10 and SELECT Name, Age, Salary FROM Instructor WHERE Age = 20 and SELECT Name, Age, Salary FROM Instructor WHERE Age = 30
            s1=data.substring(data.indexOf("(")+1, data.indexOf(")"));
            String[] arr=s1.split(",");
            s2=data.substring(0, data.indexOf("IN"));
            s2=s2.concat("= ");
            String temp;
            for(int i=0;i<arr.length;i++)
            {
              temp=s2;
              temp=temp.concat(arr[i]);
              l1.add(temp);
            }
        }
        if(index2!=-1)  //BETWEEN
        {
          // System.out.println("BETWEEN");
            // if there is a BETWEEN, it has to be made into 2 predicates
            // SELECT Name, Age, Salary FROM Instructor WHERE Age BETWEEN 20 AND 30 is changed to SELECT Name, Age, Salary FROM Instructor WHERE Age ≤ 20 and SELECT Name, Age, Salary FROM Instructor WHERE Age ≥ 30
            s1=data.substring(0,index2);
            s2=s1;
            s2=s2.concat(">= ");
            s2=s2.concat(data.substring(index2+8,data.indexOf("AND")-1));
            s3=s1;
            s3=s3.concat("<= ");
            s3=s3.concat(data.substring(data.indexOf("AND")+4));
            l1.add(s2);
            l1.add(s3);
        }
        if(index3!=-1 && index==-1)  //NOT
        {
          // System.out.println("NOT");
            // if there is a NOT, change the sql queries.
            //   Select Name, Age from Employees where NOT Age = 40 is changed to Select Name, Age from Employees where Age < 10 AND Age > 10
            s1=data.substring(0,index3);
            t=data.indexOf("=");
            s1=s1.concat(data.substring(index3+4,t-1));
            s2=s1;
            s2=s2.concat(" > ");
            s2=s2.concat(data.substring(t+2));
            s3=s1;
            s3=s3.concat(" < ");
            s3=s3.concat(data.substring(t+2));
            l1.add(s2);
            l1.add(s3);
        }
        if(index2==-1 && index3==-1 && index1==-1 && index==-1) 
        {
          if(index4!=-1 || index5!=-1)
          {
            List<String> l2=andOr(data);
            l1.addAll(l2);
          }
        }
        if(index==-1 && index1==-1 && index2==-1 && index3==-1 && index4==-1 && index5==-1 )
        {
            l1.add(data);
        }
        // System.out.println("predicates");
        // for(String a: l1)
        // {
        //     System.out.println(a);
        // }
        return l1;  //a string array is returned
    }
   
    // method that converts array to list
    public static <T> List<T> convertArrayToList(T array[])
    {
  
        // Create the List by passing the Array
        // as parameter in the constructor
        List<T> list = Arrays.asList(array);
        // Return the converted List
        return list;
    }

    private static void order(List<Query4> query) 
    {
      Collections.sort(query, new Comparator() 
      {
          public int compare(Object o1, Object o2) 
          {
              Integer x1 = Integer.valueOf(((Query4) o1).getF1r());
              Integer x2 = Integer.valueOf(((Query4) o2).getF1r());
              int sComp = x1.compareTo(x2);
              if (sComp != 0) {
                 return sComp;
              }
              x1 = Integer.valueOf(((Query4) o1).getF2r());
              x2 = Integer.valueOf(((Query4) o2).getF2r());
              int sComp2 = x1.compareTo(x2);
              if (sComp2 != 0) {
                 return sComp2;
              }
              x1 = Integer.valueOf(((Query4) o1).getF3r());
              x2 = Integer.valueOf(((Query4) o2).getF3r());
              return x1.compareTo(x2);
      }});
  }

  private static void order2(List<Query4> query) 
  {
    Collections.sort(query, new Comparator() 
      {
        public int compare(Object o1, Object o2) 
        {
          Integer x1 = Integer.valueOf(((Query4) o1).getOperator().getVal());
          Integer x2 = Integer.valueOf(((Query4) o2).getOperator().getVal());
          return x1.compareTo(x2);
      }});
  }

//   for strings comparision
  private static void order3(List<Query4> query) 
  {
    Collections.sort(query, new Comparator() 
      {
        public int compare(Object o1, Object o2) 
        {
          String x1 = String.valueOf(((Query4) o1).getOperator().getVal());
          String x2 = String.valueOf(((Query4) o2).getOperator().getVal());
          return x1.compareTo(x2);
      }});
  }

//   Function to check if a string contains only digits
  public static boolean onlyDigits(String str, int n)
  {
        // Traverse the string from start to end
        for (int i = 0; i < n; i++) 
        {
            // Check if the sepecified character is a not digit then return false, else return false
            if (!Character.isDigit(str.charAt(i))) 
            {
                return false;
            }
        }
          // If we reach here that means all the characters were digits, so we return true
        return true;
    }

  private static List<String> superQueries(Map<Integer,List<Query4>> mapQ)
  {
      // now go through the map and find superQuery for every group
      List<String> superPredicate=new ArrayList<>();
      String t="";
      // grps list
      for (List<Query4> q : mapQ.values())
      {
        
        // for each grp: q2 is a single grp list
        // if the operator is '=', just add it to the superQuery list
        System.out.println("super queries");
        if(q.get(0).getF3r()==2)
        {
            for(int i=0;i<q.size();i++)
              superPredicate.add(q.get(i).getSt());
        }
        else
        {
                // sort the q list based on val
                // if the val is a string we have to it lexographically so only order2 func has to different
                String str = q.get(0).getSt();
                int len = str.length();
                boolean d1=onlyDigits(str, len);
                if(d1==false)
                    order3(q);  //sort lexicographically
                else
                    order2(q);
                t="";
                Vector<String> s1=q.get(0).getSelect().getSecondaryAttributes();
                Set<String> words=new LinkedHashSet<>(s1);
                s1.clear();
                for(int i=1;i<q.size();i++)
                {
                // for each query, get the secondary attributes and add the unique values to a list
                s1=q.get(i).getSelect().getSecondaryAttributes();
                words.addAll(s1);
                s1.clear();
                }
                Iterator value = words.iterator();
                // form the string with the set of secondary attributes
                if(words.contains("*"))
                {
                t="*";
                }
                else
                {
                  while (value.hasNext()) 
                  {
                    t=t.concat(value.next().toString());
                    t=t.concat(", ");
                  }
                  t=t.substring(0,t.length()-2);
                }
                String superQ="SELECT ";
                superQ=superQ.concat(t);
                superQ=superQ.concat(" FROM ");
                Vector<String> from1=q.get(0).getFrom().getTablenames();
                String temp=from1.get(0);
                for(int j=1;j<from1.size();j++)
                {
                temp=temp.concat(", ");
                temp=temp.concat(from1.get(j));
                }
                superQ=superQ.concat(temp);
                superQ=superQ.concat(" WHERE ");
                superQ=superQ.concat(q.get(0).getWhere().getPrimaryAttribute());
                if(q.get(0).getF3r()==0)
                {
                    superQ=superQ.concat(" < ");
                    superQ=superQ.concat(String.valueOf(q.get(q.size()-1).getOperator().getVal()));
                }
                else
                {
                    superQ=superQ.concat(" > ");
                    superQ=superQ.concat(String.valueOf(q.get(0).getOperator().getVal())); //---took the first element
                }
                superPredicate.add(superQ);
                System.out.println(superQ);
                s1.clear();
                words.clear();
        }
      }
      return superPredicate;
  }

  private static Map<Integer,List<Query4>> groupingQueries(List<Query4> queryList)
  {
    int c=1;
    Map<Integer,List<Query4>> mapQ = new LinkedHashMap<Integer,List<Query4>>();
    List<Query4> q2=new ArrayList<>();
    List<Query4> q3=new ArrayList<>();
    q2.add(queryList.get(0));
    int i=1;
    while(i<queryList.size())
    {
      if(queryList.get(i).getF1r()==queryList.get(i-1).getF1r() && queryList.get(i).getF2r()==queryList.get(i-1).getF2r() && queryList.get(i).getF3r()==queryList.get(i-1).getF3r())
      {
        q2.add(queryList.get(i));
        i++;
      }
      else
      {
        q3=new ArrayList<>(q2);
        mapQ.put(c++,q3);
        q2.clear();
        q2.add(queryList.get(i));
        ++i;
      }
    }
    mapQ.put(c++,q2);        
    System.out.println("The size of map is "+mapQ.size());
    for(Map.Entry mp : mapQ.entrySet())
    {
      System.out.println(mp.getKey() +" "+ mp.getValue());
    }
    return mapQ;
  }

  // for secondary attribute
  public static Vector<String> filter4(String working1)
  {
            int index, index2, index3, index4, index5, index6;
            String temp;
            // parse for the secondary attribute (look out for alias, etc)
            // select * from 
            index=working1.indexOf("SELECT ");
            index2=working1.indexOf("FROM");
            working1=working1.substring(index+7,index2-1);
            // "name as n1, height"
            // first look out for ',' to get the number of secondary attrbutes then look out for alias part
            index3 = working1.indexOf(',');
            Vector<String> temp_names= new Vector<>();
            if(index3==-1) //it means that there is no ',' so just one secondary attribute
            {
              // we can have alias so look out for 'as' keyword
              index4 = working1.indexOf("as");
              if(index4==-1) //it means that there is no alias
              {
                temp_names.add(working1);
              }
              else
              {
                temp_names.add(working1.substring(0,index4-1));
              }
            }
            else //multiple secondary attributes
            {
              temp=working1.substring(0,index3);  //select age,name from  emp  (no space before ',')
              index5=temp.indexOf("as ");
              if(index5==-1) //no alias for this particular secondary attribute
              {
                temp_names.add(temp);
              }
              else
              {
                temp_names.add(temp.substring(0,index5-1));
              }
              index6 = working1.indexOf(',',index3+1);
              while(index3>=0)
              {
                if(index6==-1)
                  temp=working1.substring(index3+2); 
                else
                  temp=working1.substring(index3+2,index6);  
                index5=temp.indexOf("as ");
                if(index5==-1) //no alias for this particular secondary attribute
                {
                  temp_names.add(temp);
                }
                else
                {
                  temp_names.add(temp.substring(0,index5-1));
                }
                index3=index6;
                index6 = working1.indexOf(',',index6+1);
              }
            }
            return temp_names;

  }

    // filetr 3 - for operator
    public static String filter3(String a)
    {
      int index,index1,index2,index3,index4;
      String temp="";
      index=a.indexOf("<");
      index1=a.indexOf("<=");
      index2=a.indexOf(">");
      index3=a.indexOf(">=");
      index4=a.indexOf("=");
        if(index!=-1)
            temp=a.substring(index,index+1);
        else if(index1 !=-1)
            temp=a.substring(index1,index1+2);    
        else if(index2 !=-1)
            temp=a.substring(index2,index2+1);
        else if(index3 !=-1)
            temp=a.substring(index3,index3+2);
        else if(index4 !=-1)
            temp=a.substring(index4,index4+1);
      return temp;
    }

    // filter3a - for value that is there after the operator
    public static String filter3a(String a)
    {
      int index,index1,index2,index3,index4;
      String t1,t2="";
      index=a.indexOf("<");
      index1=a.indexOf("<=");
      index2=a.indexOf(">");
      index3=a.indexOf(">=");
      index4=a.indexOf("=");
    t1="";
    if(index!=-1)
        t1=a.substring(index+2);
    else if(index1 !=-1)
        t1=a.substring(index1+3);    
    else if(index2 !=-1)
        t1=a.substring(index2+2);
    else if(index3 !=-1)
        t1=a.substring(index3+3);
    else if(index4 !=-1)
        t1=a.substring(index4+2);
      t2=t1.trim();
      return t2;
    }

    public static int filter3rank(String temp)
    {
      OperatorsMap operatorsMap=new OperatorsMap();
      int f3r=operatorsMap.getRank(temp);
      return f3r; 
    }

  // method for filter 2 for primary attribute
  public static String filter2(String working)
  {
    int index, index1;
    String temp;
    index=working.indexOf("WHERE ");
    index1=working.indexOf(" ",index+6); //looking for space index after 'where attribute' 
    temp=working.substring(index+6, index1);
    return temp;
  }

  public static int filter2rank(String temp)
  {
    PrimaryAttributesMap primaryAttributesMap = new PrimaryAttributesMap();
    int f2r=0;
    f2r=primaryAttributesMap.getRank(temp);
    return f2r; 
  }

    // method for filter 1 - tablenames
  public static Vector<String> filter1(String working)
  {
    int i,j,c=0;
    String temp;
    int index,index2,index1,index3,index4,index5,index6;
    Vector<String> temp_names= new Vector<>();
    index = working.indexOf("FROM ");
    index1 = working.indexOf("WHERE ");
    working=working.substring(index+5,index1-1);
    index2=working.indexOf(';');
    // we can have more than one table so look out for , after 'from' parameter
    index3 = working.indexOf(',');
    if(index3==-1) //it means that there is no ',' so just one table
    {
      // we can have alias so look out for 'as' keyword
      index4 = working.indexOf("as ");
      if(index4==-1) //it means that there is no alias
      {
        temp=working;
      }
      else
      {
        temp=working.substring(0,index4-1);
      }
      temp_names.add(temp);
    }
    else //multiple tables
    {
      temp=working.substring(0,index3);  //select * from dept, emp  (no space before ',')  
      index5=temp.indexOf("as ");
      if(index5==-1) //no alias for this particular table name
      {
        temp_names.add(temp);
      }
      else
      {
        temp_names.add(temp.substring(0,index5-1));
      }
      index6 = working.indexOf(',');
      while(index3>=0)
      {
        index6 = working.indexOf(',',index3+1);
        if(index6==-1)
          temp=working.substring(index3+2);  //select * from dept, emp  (no space before ',')
        else
          temp=working.substring(index3+2,index6);
        index5=temp.indexOf("as ");
        if(index5==-1) //no alias for this particular table name
        {
          temp_names.add(temp);
        }
        else
        {
          temp_names.add(temp.substring(0,index5-1));
        }
        index3=index6;
        index6 = working.indexOf(',',index6+1);
      }
    }
    return temp_names;
  }

  public static int filter1rank(Vector<String> temp_names)
  {
    String temp;
     // arrange the temp_name in alphabetical order after converting the vector to an array
    String[] array = temp_names.toArray(new String[temp_names.size()]);
    Arrays.sort(array);
    temp=array[0];
    for(int j=1;j<temp_names.size();j++)
    {
      temp=temp.concat(", "+array[j]);
    }
    TablenameMap tablenameMap = new TablenameMap();
    int f1r=0;
    f1r=tablenameMap.getRank(temp);
    return f1r;
  }
    public static void main(String[] args) throws Exception 
    {
      Instant start = Instant.now();
        String filenames="/Users/abhignabanda/Documents/cloud/code/";
        List<String> plist = new ArrayList<String>();
        //   plist2 if for queries with no where condition
        List<String> plist2 = new ArrayList<String>();
        //   plist3 is the list for declarative queries
        List<String> plist3 = new ArrayList<String>();
        // plist 4 is for queries that have equalto sign
        List<String> plist4 = new ArrayList<String>();
        List<Query4> queryList = new ArrayList<>();
        for(int i=0;i<args.length;i++)  
        {
            String filename1=filenames.concat(args[i]);
            //read the txt files as string
            String data1=readFileAsString(filename1);  
            int c1=declarative(data1);
            if(c1==1)
            {
                plist3.add(data1);
            }
            else
            {
                // this function removes the order/groupby/having part from the query
                data1=orderbyGroupbyHaving(data1);
                int d1=noWhere(data1);
                if(d1==-1)
                {
                    plist2.add(data1);
                }
                else
                {
                    //func function makes queries into predicates (NORMAL/AND/OR/NOT/BETWEEN/IN/NOT IN)
                    List<String> list1 = func(data1);
                    plist.addAll(list1);
                }
            }
        }
        if(plist.size()==0)
        {
            System.out.println("no queries that could be optimized");
        }
        for (String a : plist)
        {
                    System.out.println(a);
                    Query4 query1=new Query4();
                    // GIVE RANKS SIMULTANEOUSLY
                    Vector<String> s1=filter4(a);
                    Select select1 = new Select();
                    select1.setSecondaryAttributes(s1);
                    Vector<String> f2=filter1(a);
                    From from1 = new From();
                    from1.setTablenames(f2);
                    String w1=filter2(a);
                    Where where1 = new Where();
                    where1.setPrimaryAttribute(w1);
                    String op1=filter3(a);
                    Operator2 operator1=new Operator2();
                    operator1.setOp(op1);
                    operator1.setVal(filter3a(a));
                    query1.setSelect(select1);
                    query1.setFrom(from1);
                    query1.setWhere(where1);
                    query1.setOperator(operator1);
                    query1.setF1r(filter1rank(from1.getTablenames()));
                    query1.setF2r(filter2rank(where1.getPrimaryAttribute()));
                    query1.setF3r(filter3rank(operator1.getOp()));
                    query1.setSt(a);
                    queryList.add(query1);
                    query1=null;
                    select1=null;
                    from1=null;
                    where1=null;
                    operator1=null;  
        }
    
            
        
        //   after the query object is formed, grouping has to be done on tablename, primary attrubte and the operator
        // should we have predefined structure or do we form ranks then and there
        // when dealing with large no of queries, predefined ranks help otherwise giving ranks while executution works out well.
        // lets do with predefined map for both tablename, primary attribute and operator.
        // for dynamic creation of lists use hashmap
        // normal queries
        if(plist.size()!=0) 
        {
            // sort the list of queries so that mapping becomes easy
            order(queryList);
            Map<Integer,List<Query4>> mapQ = groupingQueries(queryList);
            List<String> superPredicate= superQueries(mapQ);
        } 
        if(plist2.size()!=0)
        {
            System.out.println(" Queries with no where condition are printed as they were");
            for(String a : plist2)
            {
              System.out.println(a);
            }
        }
        if(plist3.size()!=0)
        {
            System.out.println("Declarative Queries");
            for(String a : plist3)
            {
              System.out.println(a);
            }
        }
        if(plist4.size()!=0)
        {
            System.out.println("Queries with equal to sign");
            for(String a : plist4)
            {
              System.out.println(a);
            }
        }
        Instant end = Instant.now();
        Duration timeElapsed = Duration.between(start, end);
        System.out.println(timeElapsed);
    }
}
