import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

/*
 * TCSS 342 - Winter 2017 Group Project #3 - Graphs and shortest paths.
 */
/**
 * A representation of a graph. Assumes that we do not have negative cost edges
 * in the graph.
 * 
 * @author Hui Ting Cai, Phu-Lam Pham
 * @version 02/10/2017
 */
public class MyGraph implements Graph {
	// you will need some private fields to represent the graph
	// you are also likely to want some private helper methods

	// YOUR CODE HERE
	/** The set contains all the vertexes. */
	private Set<Vertex> myVertices;

	/** The set contains all the edges. */
	private Set<Edge> myEdges;

	/**
	 * The key of map is vertex. The value of map are all the edges connect with
	 * this vertex and use a set to stored those edges (Adjacency list).
	 */
	private Map<Vertex, Set<Edge>> myGraphs;

	/**
	 * Creates a MyGraph object with the given collection of vertices and the
	 * given collection of edges.
	 * 
	 * @param v a collection of the vertices in this graph
	 * @param e a collection of the edges in this graph
	 * @exception IllegalArgumentException if either of input collections are empty or null.
	 */
	public MyGraph(Collection<Vertex> v, Collection<Edge> e) {
		if (v == null || e == null) {
			throw new IllegalArgumentException();
		}

		// YOUR CODE HERE
		myVertices = new HashSet<Vertex>();
		myEdges = new HashSet<Edge>();
		myGraphs = new HashMap<Vertex, Set<Edge>>();

		for(Vertex vertex: v){
			myVertices.add(vertex);
		}
		

		// put all the vertex into myGraphs with new a set of edges.
		for (Vertex vertex : v) {
			myGraphs.put(vertex, new HashSet<Edge>());
		}

		/*
		 * check the edges, If the edges are appropriate then add into myGraps
		 * and collection of edges.
		 */
		checkEdges(e);

//		System.out.println(myGraphs);
		
		
//		testVertexAndEdges();
	}
	

	/**
	 * Return the collection of vertices of this graph.
	 * 
	 * @return the vertices as a collection (which is anything iterable)
	 */
	@Override
	public Collection<Vertex> vertices() {
		// YOUR CODE HERE
		return myVertices;

	}

	/**
	 * Return the collection of edges of this graph.
	 * 
	 * @return the edges as a collection (which is anything iterable)
	 */
	@Override
	public Collection<Edge> edges() {
		// YOUR CODE HERE
		return myEdges;

	}

	/**
	 * Return a collection of vertices adjacent to a given vertex v. i.e., the
	 * set of all vertices w where edges v -> w exist in the graph. Return an
	 * empty collection if there are no adjacent vertices.
	 * 
	 * @param v
	 *            one of the vertices in the graph
	 * @return an iterable collection of vertices adjacent to v in the graph
	 * @throws IllegalArgumentException
	 *             if v does not exist.
	 */
	@Override
	public Collection<Vertex> adjacentVertices(Vertex v) {

		// YOUR CODE HERE
		// If v does not exist.
		if (!myGraphs.containsKey(v)) {
			throw new IllegalArgumentException();
		}

		// Using a set to avoid duplicates
		Set<Vertex> theAdjacentVertices = new HashSet<Vertex>();

		// Using myGraphs to find all vertices adjacent
		for (Edge edge : myGraphs.get(v)) {

			// Add the Vertex that is the destination of the edge
			theAdjacentVertices.add(edge.getDestination());
		}
		return theAdjacentVertices;
	}

	/**
	 * Test whether vertex b is adjacent to vertex a (i.e. a -> b) in a directed
	 * graph. Assumes that we do not have negative cost edges in the graph.
	 * 
	 * @param a
	 *            one vertex
	 * @param b
	 *            another vertex
	 * @return cost of edge if there is a directed edge from a to b in the
	 *         graph, return -1 otherwise.
	 * @throws IllegalArgumentException
	 *             if a or b do not exist.
	 */
	@Override
	public int edgeCost(Vertex a, Vertex b) {

		// YOUR CODE HERE
		// If a or b do not exist, throw an exception.
		if (!myGraphs.containsKey(a) || !myGraphs.containsKey(b)) {
			throw new IllegalArgumentException();
		}
		int theWeight = -1;
		for (Edge edge : myGraphs.get(a)) {
			// if the Vertex that is the destination of the edge is equal to b
			if (edge.getDestination().equals(b)) {
				theWeight = edge.getWeight();
			}
		}
		return theWeight;
	}

	/**
	 * Returns the shortest path from a to b in the graph, or null if there is
	 * no such path. Assumes all edge weights are nonnegative. Uses Dijkstra's
	 * algorithm.
	 * 
	 * @param a
	 *            the starting vertex
	 * @param b
	 *            the destination vertex
	 * @return a Path where the vertices indicate the path from a to b in order
	 *         and contains a (first) and b (last) and the cost is the cost of
	 *         the path. Returns null if b is not reachable from a.
	 * @throws IllegalArgumentException
	 *             if a or b does not exist.
	 */
	public Path shortestPath(Vertex a, Vertex b) {

		// YOUR CODE HERE (you might comment this out this method while doing
		// Part 1)
		
		//If a or b does not exist then throw an IllegalArgumentException.
		if (!myGraphs.containsKey(a) || !myGraphs.containsKey(b)) {
			throw new IllegalArgumentException();
		}
		
		List<Vertex> vertices = new ArrayList<Vertex>();
		
		//If it is self edge then the cost is 0;
		if(a.equals(b)){
			vertices.add(a);
			return new Path(vertices,0);
		}
		
		//For each node: x.myCost=infinity, x.known=false;
		for(Vertex vertex: vertices()){
			vertex.updateCost(Integer.MAX_VALUE);
//			System.out.println(vertex.getLabel()+" cost is " +vertex.getCost());
			vertex.updateStatus(false);
		}
//		for(Vertex vertex: myGraphs.keySet() ){
//			System.out.println(vertex.getLabel()+" a cost is " +vertex.getCost());
//		}
		
		PriorityQueue<Vertex> pQ =new PriorityQueue<Vertex>();
		// Set the cost of start vertex to 0;
		a.updateCost(0);
		pQ.add(a);
		
		// Build_heap with all vertexes
		while(!pQ.isEmpty()){  // heap is not empty
			Vertex target = pQ.poll(); // smallest cost.
			target.updateStatus(true); // Set known.
			
			// find out all the vertex adjacent to target.
//			Collection<Vertex> theAdjacentVertices = this.adjacentVertices(target); 
//			System.out.println("the adjacent vertices of "+ target.getLabel()+" is: " + theAdjacentVertices.toString());
			for(Vertex adjacentVertice : this.adjacentVertices(target)){
				//Only in myVertices list's vertices have cost.
				for(Vertex vertex: vertices()){
					if(vertex.equals(adjacentVertice)){
						adjacentVertice =vertex;
						break;
					}
				}
				// If the vertex is unknown.
				if(!adjacentVertice.getStatus()){
					int newCost=target.getCost()+edgeCost(target,adjacentVertice);
					int oldCost=adjacentVertice.getCost();
//					System.out.println("the new cost "+ newCost);
//					System.out.println("the old cost "+ adjacentVertice.getCost());
					if(newCost<oldCost){
						// update the cost and the path of adjacentVertice.
						pQ.remove(adjacentVertice);
						adjacentVertice.updateCost(newCost);
						adjacentVertice.upPath(target);
						pQ.add(adjacentVertice);
//						System.out.println("the adjacent vertice cost "+ adjacentVertice.getCost());
						
						// Check it the adjacentVertice is b
						if(adjacentVertice.equals(b)){
							b.updateCost(adjacentVertice.getCost());
							b.upPath(adjacentVertice.getPath());
						}
					}
				}
			}			
		}
		
		
		List<Vertex> result = new ArrayList<Vertex>();
		result.add(b);
		Vertex node = b;
		while(node.getPath()!=null){
			result.add(node.getPath());
			node= node.getPath();
		}
		
		Collections.reverse(result);
		return new Path(result, b.getCost());
	}

	/**
	 * The is a helper method to check edges. If the edges are appropriate then
	 * add into myGraps and collection of edges.
	 * 
	 * @param theE
	 *            a collection of the edges in this graph.
	 * @throws IllegalArgumentException
	 *             If edge weights is negative, the edges involve vertices
	 *             without labels, or the same directed edge more than once with
	 *             a different weight.
	 * 
	 */
	private void checkEdges(Collection<Edge> theE) {
		for (Edge thisE : theE) {

			/*
			 * Check if graph contains the two vertices the edge.
			 */
			if (!myGraphs.containsKey(thisE.getSource()) || !myGraphs.containsKey(thisE.getDestination())) {
				throw new IllegalArgumentException("The edge " + thisE.getSource() + " is not in the graph.");
			}

			// Edge weights should not be negative.
			if (thisE.getWeight() < 0) {
				throw new IllegalArgumentException("The edge " + thisE.getSource() + "'s weight is not valid.");
			}

			/*
			 * Check this edge with that edge. If same directed edge with a
			 * different weight throw, otherwise store into map and the
			 * collection of edges
			 */
			for (Edge thatE : theE) {
				if (thisE.getSource().equals(thatE.getSource()) && thisE.getDestination().equals(thatE.getDestination())
						&& thisE.getWeight() != thatE.getWeight()) {
					throw new IllegalArgumentException();
				}
				myEdges.add(thisE);
				myGraphs.get(thisE.getSource()).add(thisE);
			}
		}
	}
	
	@Override
	public String toString() {
		return myGraphs.toString();
	}
	
//	/**
//	 * This method just help us to debug our coding.
//	 */
//	private void testVertexAndEdges(){
//		// print out a collection of the vertices
//		System.out.println("a collection of the vertices in this graph:\n"+myVertices.toString());
//		
//		// Print out a collection of the vertices.
//		System.out.println(" a collection of the edges in this graph:\n"+myEdges.toString());
//	}
}
