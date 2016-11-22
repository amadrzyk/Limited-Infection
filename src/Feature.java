/**
 * This class contains information on the feature being rolled out
 * @author alexmadrzyk
 *
 */
public class Feature {
	private int featureId;
	
	public Feature(int id){
		this.featureId = id;
	}
	
	public int getId(){
		return this.featureId;
	}
}
