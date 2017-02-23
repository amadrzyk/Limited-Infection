# Limited and Total Infection

<p align="center">
  <br>
  <img loop=infinite src="https://github.com/amadrzyk/Achi-Dynamic-Programming/blob/master/PlayAchiLoop.gif" alt="Achi Gameplay"/>
  <br>
</p>

## Introduction

This program displays an interactive graph of a network's users, and models their relationships with each other. The goal of this project was to model the "spread" of a live update to a company's userbase.
* Users are represented by **nodes**, which can hold many attributes (such as the current website version they see, who they're connected to, their degree, and more).
    * In this case, being "infected" refers to whether or not the user sees the particular updated version compared with everyone else. 
* Edges between nodes are always directed from the parent/teacher node to the student node. That is, the program uses the heuristic where a teacher generally has more students than a student has teachers. The same goes for parent/child edges.
* Both versions of the program (total_infection and limited_infection) initiate a modified version of the Breadth-First Search algorithm.
* By default, the limited_infection program only infects 300 users, but that value can be changed in the program's source code.

#### **One thing I wanted to accomplish was modelling a real-world visualization. Types of users we have are:**
1. Single users with no connections (degree = 0) 
    * These can be infected immediately
    * By default, I have disabled single users in my graph visualization, only because they end up shrinking the size of the entire graph.
2. Clusters of users with some connections (degree = 1+)
    * They can be completely separate (in which case you can infect the whole group)
    * When you infect one node, and don't have a limit for number of users infected, then all users linked to that node will become infected.
    
#### **Modified Breadth-First Search algorithm**
I noticed that it was difficult to infect every student of a specific teacher at once. That is why I modified the commonly-known BFS algorithm so that when visiting a node, the algorithm first temporarily saves all immediate neighbors of a given node, then queues all "2nd generation" neighbors (2 edges away), and then infects the immediate neighbors. This causes a great clustering effect that infects an entire classroom, but also queues users that are outside of that classroom to be later infected.

## Getting Started
If you have Java installed, double-clicking on the .jar file will run the program. Otherwise, all files required to run the project on Eclipse are also present. 
It is recommended to add all three libraries in the `lib` folder to your referenced libraries. One can do so by accessing:
```
Properties < Java Build Path < Libraries
```
and clicking "add Jar". This will add the required graph libraries as classpath variables for the project to run.

## Future Extensions
A potential implementation would be to use the ```A*``` algorithm (or a better heuristic) to model an exact infection. The program could then search through every "class" until it finds one that satisfies the conditions required, and infects the entire connected component.

## Final Words
Thank you for reviewing my project, and if you have any questions or issues with running the project, please let me know and I would be happy to help!
