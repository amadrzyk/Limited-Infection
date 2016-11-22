import java.util.HashMap;

/**
 * This class represents a user profile on Khan Academy's network.
 * Users can have different features activated at different times.
 * 
 * FeatureId here is only used to check whether a specific user has a specific feature active or not.
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
	private HashMap<Integer, Boolean> features;
	
	public User(){
		features = new HashMap<Integer, Boolean>();
	}
	
	public boolean isActive(int featureId){
		return this.features.get(featureId);
	}
	
	public boolean activate(int featureId){
		this.features.put(featureId, true);
		
		// Will return true if activation worked
		return this.features.get(featureId);
	}
	
	public boolean deactivate(int featureId){
		this.features.put(featureId, false);
		
		// Will return false if activation worked
		return this.features.get(featureId);
	}
}
