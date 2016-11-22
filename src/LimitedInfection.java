// GraphStream Utilities
import org.graphstream.algorithm.generator.*;
import org.graphstream.graph.*;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.ui.view.Viewer;

// Java Utilities
import java.util.*;

public class LimitedInfection {
	
	static Graph graph;
	static HashSet<Node> nodes;
	
	public static void main(String args[]) {
				
		graph = new SingleGraph("Tutorial 1");
		nodes = new HashSet<Node>();
		
		graph.setStrict(false);
		graph.setAutoCreate(true);
		graph.addAttribute("ui.stylesheet", "url('./lib/style.css')");
		generateRandomGraph(1000);
		
		int deg = 0;
		String style = "";
		for (Node node : graph){
			deg = node.getDegree() % 256;
			if (deg == 0) deg++;
			style = String.format("fill-color: rgb(%d,77,77);", Math.abs(255 - (255/deg) % 256));
			node.addAttribute("ui.style", style);
		}
		
		System.setProperty("gs.graphstream.ui.renderer", "org.graphstream.ui.j2dviewer.J2DGraphRenderer");
		Viewer viewer = graph.display(true);
	}
	
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
	public static void generateRandomGraph(int numNodes) {
 
		// Between 1 and 3 new links per node added.
		Generator gen = new BarabasiAlbertGenerator(1);
		
		gen.addSink(graph); 
		gen.begin();
		for(int i=0; i<numNodes; i++) {
			gen.nextEvents();
		}
		gen.end();
	}
}
