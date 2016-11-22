## Introduction

This program effectively models a graph of Khan Academy users, and their relationships with eachother. 
* Users are represented by nodes, which hold many attributes (such as the current website version they see, who they're connected to, their degree, and more.
* Edges between nodes are always directed from the parent/teacher node to the student node. That is, the program uses the heuristic where a teacher generally has more students than a student has teachers. The same goes for parents.
* Both versions of the program (total_infection and limited_infection) initiate a Depth-First Search 

One thing I wanted to accomplish was modelling a real-world visualization. Types of users we have are: 
* Single users with no connections (degree = 0) 
    * These can be infected immediately
* Clusters of users with some connections (degree = 1-20?)
    * Might want to try visualizing these clusters
    * They can be completely separate (in which case you can infect the whole group)
* If there is only one linkage between someone in one group and another, 
 we can risk that linkage with the discrepancy in two sites

## Getting Started
If you are using Eclipse, you can open the project in there, and run it (after adding libraries as class variables).
Otherwise, double clicking on the .jar file should run the graphics. 
