// GraphStream Utilities
import org.graphstream.algorithm.generator.*;
import org.graphstream.graph.*;
import org.graphstream.graph.implementations.SingleGraph;

// Java Utilities
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * This class models a limited infection among Khan Academy users.
 * Default number of users is 1000, and the "number to infect" is 300,
 * but these numbers can be adjusted as well.
 * @author alexmadrzyk
 */
public class limited_infection {
	
	static Graph graph;
	static HashSet<Node> users;
	
	static int infectedCount = 0;				// Counter variable
	static int numUsers = 1000;
	static int maxInfected;				 		// How many users to infect?
	static boolean hasSingleUsers = true;		// Should the graph contain single users with no edges?
	public enum visited {YES, NO};
	
	public static void main(String args[]) {
		
		// OPTIONAL : If you want to ask the user for number of users to infect
//		Scanner scanner = new Scanner(System.in);
//		System.out.printf("How many users, out of %d, to infect?\n", numUsers);
//		maxInfected = scanner.nextInt();
//		scanner.nextLine();
//		System.out.println("Should we show single users with no connections present? (y/n)");
//		String ans = scanner.nextLine().trim();
//		if (ans.toLowerCase().equals("y")) hasSingleUsers = true;
//		else hasSingleUsers = false;
//		scanner.close();
		hasSingleUsers = false;
		maxInfected = 300;
				
		// Instantiate graph and users 
		graph = new SingleGraph("TotalInfection");
		System.setProperty("org.graphstream.ui.renderer", "org.graphstream.ui.j2dviewer.J2DGraphRenderer");
		users = new HashSet<Node>();
		
		// Graph-specific initializations
        graph.addAttribute("ui.quality");
        graph.addAttribute("ui.antialias");
		graph.setStrict(false);
		graph.setAutoCreate(true);
		graph.removeAttribute("ui.stylesheet");
		graph.removeAttribute("ui.style");
		graph.addAttribute("ui.stylesheet", "url('./lib/style.css')");
		generateRandomGraph(numUsers);
		setCoachingRelations();
		
		// First, infect the nodes with degree of 0 (no neighbors), if any
		List<Node> list = new ArrayList<Node>(users);
		Node maxNode = list.get(0);
		for (Node n : list){
			// If a node is single, infect it
			if (n.getDegree() < 1){
				infect(n);
				n.setAttribute("visited", visited.YES);
			}
			// At the same time, find node with highest degree
			if (n.getDegree() > maxNode.getDegree()) maxNode = n;
		}
				
		// Display graph
		graph.display(true);
		
		// Begin infection from most central node
		infectAllFromUser(maxNode);
		
		System.out.printf("Successfully infected %d users.\n", infectedCount);
	}
	
	public static void generateRandomGraph(int numNodes) {
 
		// Between 1 and 3 new links per node added.
		Generator gen = new BarabasiAlbertGenerator(1);
		gen.addSink(graph); 
		gen.begin();
		for(int i = 0; i < numNodes; i++) {
			gen.nextEvents();
		}
		gen.end();
		
		// Style graph
		int deg = 0, r = 0;
		String style = "";
		for (Node node : graph){
			deg = node.getDegree() % 256;
			if (deg == 0) deg++;
			r = Math.abs(255 - (255/deg) % 256);
			style = String.format("fill-color: rgb(%d,77,77);", r);
			node.addAttribute("ui.style", style);
			node.addAttribute("visited", visited.NO);
			if (deg > 10)
				node.setAttribute("ui.label","TEACHER");
			users.add(node);
		}
		
		// Add single users
		if (hasSingleUsers){
			for (int i = numNodes+1; i < numNodes+7; i++){
				Node curr = graph.addNode(String.valueOf(i));
				users.add(curr);
			}
		}
	}
	
	public static void setCoachingRelations(){
		for(Edge e : graph.getEachEdge()) {
			Node start = e.getNode0();
			Node end = e.getNode1();
			
			graph.removeEdge(e);
			
			// In this case, node with more connections is the teacher,
			// node with less connections is the student
			if (start.getDegree() > end.getDegree()){
				graph.addEdge(e.getId(), start.getId(), end.getId(), true);
			}
			else {
				graph.addEdge(e.getId(), end.getId(), start.getId(), true);
			}
		}
	}

	public static void infectAllFromUser(Node node){
		
		LinkedList<Node> queue = new LinkedList<Node>();
		queue.addLast(node);
		infect(node);
		node.setAttribute("visited", visited.YES);
		
		while(!queue.isEmpty()) {
			Node curr = queue.removeFirst();
			if (infectedCount >= maxInfected) return;
			infect(curr);
			
			
			Iterator<Node> iter = curr.getNeighborNodeIterator();
			Iterator<Node> iter2;
			List<Node> neighbors = new ArrayList<Node>();
			
			// Immediate neighbors
			while (iter.hasNext()){
				Node next = iter.next();
				neighbors.add(next);
				
				// Add all second-gen neighbors into the queue for later
				iter2 = next.getNeighborNodeIterator();
				while (iter2.hasNext()){
					Node next2 = iter2.next();
					if (next2.getAttribute("visited") == visited.NO && infectedCount < maxInfected){
						next2.setAttribute("visited", visited.YES);
						queue.addLast(next2);
					}
				}
			}
			
			// Infect all immediate neighbors
			for (Node usr : neighbors){
				if (infectedCount >= maxInfected) return;
				infect(usr);
			}
			
			neighbors.clear();
		}
	}
	
	private static void infect(Node node){
		node.removeAttribute("ui.style");
		node.addAttribute("ui.style", "fill-color: rgb(162,255,255);");
		try {
			TimeUnit.MILLISECONDS.sleep(30);
		} catch (InterruptedException e) {
			e.getMessage();
		}
		infectedCount++;
	}
}
