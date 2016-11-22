// GraphStream Utilities
import org.graphstream.algorithm.generator.*;
import org.graphstream.graph.*;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.ui.view.Viewer;

// Java Utilities
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 *	One thing we really need is a real-world visualization. Types of users we have: 
 *		- Single users with absolutely no connections (degree = 0) 
 *			-> These can be infected immediately
 *		- Clusters of users with some connections (degree = 1-20?)
 *			-> Might want to try visualizing these clusters
 *			-> They can be completely separate (in which case you can infect the whole group)
 *		- If there is only one linkage between someone in one group and another, 
 *			we can risk that linkage with the discrepancy in two sites
 */
public class total_infection {
	
	static Graph graph;
	static HashMap<Node, User> users;
	public enum visited { YES, VISITING, NO };
	
	public static void main(String args[]) {
				
		graph = new SingleGraph("TotalInfection");
		users = new HashMap<Node, User>();
		
        graph.addAttribute("ui.quality");
        graph.addAttribute("ui.antialias");
		graph.setStrict(false);
		graph.setAutoCreate(true);
		graph.addAttribute("ui.stylesheet", "url('./lib/style.css')");
		generateRandomGraph(1000);
		
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
			users.put(node, new User(1, node));
		}
		
		// Sort all nodes by degree (largest first)
		List<Node> sortedList = new ArrayList<Node>(users.keySet());
		Collections.sort(sortedList, nodeComparator);
		
		System.setProperty("gs.graphstream.ui.renderer", "org.graphstream.ui.j2dviewer.J2DGraphRenderer");
		Viewer viewer = graph.display(true);
		infectAllFromUser(sortedList.get(0));
	}
	
	public static void generateRandomGraph(int numNodes) {
 
		// Between 1 and 3 new links per node added.
		Generator gen = new BarabasiAlbertGenerator(1);
		
		gen.addSink(graph); 
		gen.begin();
		for(int i=0; i<numNodes; i++) {
			gen.nextEvents();
		}
		gen.end();
		
//		// Single users
//		for (int i = numNodes+1; i < numNodes+7; i++){
//			graph.addNode(String.valueOf(i));
//		}
	}
	
	public void setCoachingRelations(){
		for(Edge e : graph.getEachEdge()) {
			Node start = e.getNode0();
			Node end = e.getNode1();
			
			graph.removeEdge(e);
			
			// In this case, node with more connections is the teacher,
			// node with less connections is the student
			Edge edge;
			if (start.getDegree() > end.getDegree()){
				edge = graph.addEdge(e.getId(), start.getId(), end.getId(), true);
			}
			else {
				edge = graph.addEdge(e.getId(), end.getId(), start.getId(), true);
			}
			edge.setAttribute("ui.style", "edge {arrow-shape: arrow; arrow-size: 15px, 15px;}");
		}
	}
	
	/**
	 * Infects one branch at a time.
	 * @param node
	 */
	public static void infectAllFromUser(Node node){
		
		// BASE CASE
		if (node == null) return;
		
		// VISIT NODE
		infect(node);
		Node next;
				
		// RECURSIVE CASE - visit all neighbors
		Iterator<Node> iter = node.getNeighborNodeIterator();
		while (iter.hasNext()){
			next = iter.next();
			if (next.getAttribute("visited") != visited.YES)
				infectAllFromUser(next);
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
		node.setAttribute("visited", visited.YES);
	}
	
	private static Comparator<Node> nodeComparator = new Comparator<Node>() { 
        
		@Override         
		public int compare(Node n1, Node n2) {             
			return (n1.getDegree() < n2.getDegree() ? 1 :                     
				(n1.getDegree() == n2.getDegree() ? 0 : -1));           
		}     
	};  
}