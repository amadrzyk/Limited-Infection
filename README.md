# Limited and Total Infection

## Introduction

This program displays an interactive graph of Khan Academy users, and models their relationships with eachother. 
* Users are represented by **nodes**, which can hold many attributes (such as the current website version they see, who they're connected to, their degree, and more).
    * In this case, being "infected" refers to whether or not the user sees the particular updated version compared with everyone else. 
* Edges between nodes are always directed from the parent/teacher node to the student node. That is, the program uses the heuristic where a teacher generally has more students than a student has teachers. The same goes for parent/child edges.
* Both versions of the program (total_infection and limited_infection) initiate a modified version of the Breadth-First Search algorithm.

#### **One thing I wanted to accomplish was modelling a real-world visualization. Types of users we have are:**
1. Single users with no connections (degree = 0) 
    * These can be infected immediately
    * By default, I have disabled single users in my graph visualization, only because they end up shrinking the size of the entire graph.
2. Clusters of users with some connections (degree = 1+)
    * They can be completely separate (in which case you can infect the whole group)
    * When you infect one node, and don't have a limit for number of users infected, then all users linked to that node will become infected.

## Getting Started
If you have Java installed, double-clicking on the .jar file should run the program. Otherwise, all files required to run the project on Eclipse are also present. 
It is recommended to add all three libraries in the `lib` folder to your referenced libraries. One can do so by accessing:
```
Properties < Java Build Path < Libraries
```
and clicking "add Jar". This will add the required graph libraries as classpath variables for the project to run.
