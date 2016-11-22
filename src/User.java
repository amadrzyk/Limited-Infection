import java.util.*;

import org.graphstream.graph.Node;

/**
 * This class represents a user profile on Khan Academy's network.
 * Users have a version attribute on their profile.
 * 
 * We could always add: <br>
 * <ul>
 * 	<li>Additional information on the environment of the user (influencing whether or not to
 * 		push this feature to their account</li>
 * 	<li></li>
 * 	<li></li>
 * </ul>
 *
 * @author alexmadrzyk
 */
public class User {
	// Map of features, and whether or not they are currently active.
	private int version;
	private Node node;
	
	public User(int v, Node n){
		this.version = v;
		this.node = n;
	}
	
	public int getVersion(){
		return this.version;
	}
	
	public void setVersion(int v){
		this.version = v;
	}
	
	public Node getNode(){
		return this.node;
	}
}
