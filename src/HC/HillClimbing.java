package HC;

import java.io.*;
import java.util.*;

public class HillClimbing {
	//Define Output Path
	private static String OutputPath;
	private static String OutputTracePath;
	private static PrintWriter Output;
	private static PrintWriter OutputTrace;
	private static HashSet<Vertex> visited;
	/**
	 * @author chujiechen
	 * @param G: original graph
	 * @param cutoff: time upper limit
	 * @param randomSeed: a seed
	 * @return result vertex cover
	 */
	public static Graph hillClimbing(Graph G, int cutoff, int randomSeed){
		long start_time = System.currentTimeMillis();
		long end_time;
		float run_time = 0;
		// Initial VC
		visited = new HashSet<>();
		Graph current = makeNode(G, randomSeed);
		while(run_time < cutoff) {
			Graph next = findSuccessor(current);
			end_time = System.currentTimeMillis();
			run_time = (end_time-start_time)/1000F;
			
			if(next.getVerticesNum() >= current.getVerticesNum()) {
				return current;
			}
			current = next;
			OutputTrace.printf("%.3f, %d%n", run_time, current.getVerticesNum());
		}
		return current;
	}

	private static Graph findSuccessor(Graph current) {
		// TODO Auto-generated method stub
		Graph next = current.deepClone();
		for(long i: next.getDegreeSortedVertices()) {
			Vertex u = next.getVertex(i);
			if(next.isRemovableVertex(u)) {
				next = next.removeVertex(u);
				break;
			}
		}
		return next;
	}

	private static Graph makeNode(Graph g, int randomSeed) {
		// TODO Auto-generated method stub
		int n = g.getVerticesNum();
		Random rand = new Random();
		rand.setSeed(randomSeed);
		int id = rand.nextInt(n) + 1;
		Vertex u = g.getVertex(id);
		if(g.isRemovableVertex(u)) {
			g.removeVertex(u);
		}
		return g;
	}




	public static void main(String[] args) throws FileNotFoundException {
    	String filename = "power.graph";
    	int cutoff = 600;
    	int randomSeed = 1;
		OutputPath = "output/"+filename +"_hillClimbing_"+ cutoff +"_"+ randomSeed+".sol";
		OutputTracePath = "output/"+filename +"_hillClimbing_" +cutoff+"_"+randomSeed+".trace";
		Output = new PrintWriter(OutputPath);
		OutputTrace = new PrintWriter(OutputTracePath);
		
    	System.out.println("running...");
    	Graph G = Graph.read("src/HC/" + filename);
    	Graph res = hillClimbing(G, cutoff, randomSeed);
    	
    	System.out.println(res);
    	Output.printf(res.toString());
    	System.out.println("Done");
    	Output.close();
    	OutputTrace.close();
    }
}
