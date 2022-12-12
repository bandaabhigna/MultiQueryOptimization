# MultiQueryOptimization_AIR
We have the following files:
Select.java : It contains the secondary attributes list of each query.
From.java : It contains the table name of each query.
Where.java : It contains the primary attribute of each query.
Operator.java : It contains the operator type (whether it is <,>,<=,>=,...) and value of the primary attribute of each query.
Operator2.java : It is similar to Operator.java just that the value of primary attribute is string whereas in Operator.java is it int.
Query.java : It contains the query.
OperatorsMap.java : Contains the map of the operators.
PrimaryAttributesMap.java : Contains the map of the primary attributes
TablenameMap.java : Contains the map of the table names
ParserObj4.java : This is the main class and has the major part of algorithm.

What all are needed:
java
mysql

How to execute:
Go the code dir and type "javac ParserObj4.java" for compilation
For running, type "java ParserObj4 s1q1.txt s1q2.txt .." 

For finding the execution time, go to mysql and type
1) set profiling =1
2) query
3) show profiles
The last command displays the execution time of sql query.
