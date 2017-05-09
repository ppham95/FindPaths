
/**
 * Representation of a graph vertex
 */
public class Vertex implements Comparable<Vertex>{
	// label attached to this vertex
	private String label;
	private int myCost;
	private boolean myStatus;
	private Vertex myPath;
	

	/**
	 * Construct a new vertex
	 * 
	 * @param label
	 *            the label attached to this vertex
	 */
	public Vertex(String label) {
		if (label == null)
			throw new IllegalArgumentException("null");
		this.label = label;
	}

	/**
	 * Get a vertex label
	 * 
	 * @return the label attached to this vertex
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * A string representation of this object
	 * 
	 * @return the label attached to this vertex
	 */
	public String toString() {
		return label;
	}

	// auto-generated: hashes on label
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((label == null) ? 0 : label.hashCode());
		return result;
	}

	// auto-generated: compares labels
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final Vertex other = (Vertex) obj;
		if (label == null) {
			return other.label == null;
		} else {
			return label.equals(other.label);
		}
	}
	
	/**
	 * Update the cost of this vertex.
	 * @param theCost the cost for this vertex.
	 * 
	 */
	public void updateCost(int theCost){
		myCost=theCost;
		
	}
	
	/**
	 * Return this vertex cost.
	 * @return myCost the cost of this vertex.
	 */
	public int getCost(){
		return myCost;
	}
	
	/**
	 * Update the vertex's status. The vertex know or unknown.
	 * @param theStatus the vertex know or unknown.
	 * 
	 */
	public void updateStatus(boolean theStatus){
		myStatus= theStatus;
		
	}
	
	/**
	 * Return my status 
	 * @return boolean this vertex is know or unknown.
	 */
	public boolean getStatus(){
		return myStatus;
	}
	
	/**
	 * Update this vertex's path.
	 * @param thePath the path.
	 * 
	 */
	public void upPath(Vertex thePath){
		myPath= thePath;
	}

	/**
	 * Get this vertex's path.
	 *@return myPath.
	 */
	public Vertex getPath(){
		return myPath;
	}
	/**
	 * Compare to two vertexes cost. If two vertexes are equal, return
	 * 0. If the cost of 'this' is higher than the cost of 'theVertex',
	 * return 1. If the cost of 'this' is lower than the frequency of
	 * 'theVertex', return -1
	 * 
	 * @param theVertex
	 *            The Vertex
	 * 
	 * @return Return 0 if two cost are equal. Return -1 if this -
	 *         theVertex < 0. Return 1 if this - theVertex > 0.
	 */
	@Override
	public int compareTo(Vertex theVertex) {
		return (int) Math.signum(this.myCost - theVertex.myCost);
	}

}
