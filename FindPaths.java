import java.util.*;
import java.io.*;

/*
 * TCSS 342 - Winter 2017 Group Project #3 - Graphs and shortest paths.
 */
/**
 * A representation of a graph. Assumes that we do not have negative cost edges
 * in the graph.
 * 
 * @author Hui Ting Cai, Phu-Lam Pham
 * @version 02/10/2017 Driver program that reads in a graph and prompts user for
 *          shortests paths in the graph. (Intentionally without comments. Read
 *          through the code to understand what it does.)
 */

public class FindPaths {
	public static void main(String[] args) {
		if (args.length != 2) {
			System.err.println("USAGE: java FindPaths <vertex_file> <edge_file>");
			System.exit(1);
		}
		
		MyGraph g = readGraph(args[0], args[1]);

		Scanner console = new Scanner(System.in);
		Collection<Vertex> v = g.vertices();
		Collection<Edge> e = g.edges();
		
		System.out.println("The vertices are " + v);
		System.out.println("The edges are " + e);
		System.out.println();
		
		boolean run = true;
		while (run) {
			System.out.print("Find path? ('y' to continue, 'n' to exit)? ");
			String status = console.nextLine();
			if (status.startsWith("n")) {
				run = false;
				console.close();
			} else if (status.startsWith("y")) {
				System.out.print("Starting vertex? ");
				Vertex a = new Vertex(console.nextLine());
				if (!v.contains(a)) {
					System.out.println("No such vertex exist...");
					System.exit(1);
				}
	
				System.out.print("Destination vertex? ");
				Vertex b = new Vertex(console.nextLine());
				if (!v.contains(b)) {
					System.out.println("No such vertex exist...");
					System.exit(1);
				}
				
				// YOUR CODE HERE: call shortestPath and print
				// out the result
				System.out.println("Shortest path from " + a + " to " + b);
				Path thePath = g.shortestPath(a, b);
	
				// If there is no path from the start to end vertex.
				if (thePath == null) {
					System.out.println("does not exist");
				} else {
					/*
					 * Print out tow lines. The first line prints the path with
					 * vertices separated by space. The second line prints the cost
					 * of the path
					 */
					List<Vertex> theVertexList = thePath.vertices;
					
//					System.out.println(theVertexList);
					StringBuilder sb = new StringBuilder();
					sb.append(theVertexList.get(0).getLabel());
					for (int i = 1; i < theVertexList.size(); i++) {
						sb.append(" -> " + theVertexList.get(i).getLabel());
					}
					System.out.println(sb.toString());
					System.out.println("The cost of path: " + thePath.cost);
					System.out.println();
				}
			}
		}
	}

	public static MyGraph readGraph(String f1, String f2) {
		Scanner s = null;
		try {
			s = new Scanner(new File(f1));
		} catch (FileNotFoundException e1) {
			System.err.println("FILE NOT FOUND: " + f1);
			System.exit(2);
		}

		Collection<Vertex> v = new ArrayList<Vertex>();
		while (s.hasNext())
			v.add(new Vertex(s.next()));

		try {
			s = new Scanner(new File(f2));
		} catch (FileNotFoundException e1) {
			System.err.println("FILE NOT FOUND: " + f2);
			System.exit(2);
		}

		Collection<Edge> e = new ArrayList<Edge>();
		while (s.hasNext()) {
			try {
				Vertex a = new Vertex(s.next());
				Vertex b = new Vertex(s.next());
				int w = s.nextInt();
				e.add(new Edge(a, b, w));
			} catch (NoSuchElementException e2) {
				System.err.println("EDGE FILE FORMAT INCORRECT");
				System.exit(3);
			}
		}
		return new MyGraph(v, e);
	}
}
