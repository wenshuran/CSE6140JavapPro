package LS1;

import java.util.*;

public class SA {
	
	
	// summary of SimulatedAnnealing opertations
	public static List<Vertex> SimulatedAnnealing(Graph graph, double cutoff, long randSeed){

        // Time Measure
        long start = System.currentTimeMillis();
        long endTime;
        long elapsed = 0;

        float elapsedFinal = 0;


        // Temperature Start and Cooling
        double temp = 10000.0;
        double cooling = 0.99;
        double absTemp = 0.00001;


        // initialize solution: Using Max Degree Greedy Algorithm
        List<Vertex> curr = initialSolution(graph);

        // counter to see how many iterations of loop
        int iter = 0;

        // init Random Generator
        Random randomno = new Random();
        randomno.setSeed(randSeed);

        // init nextSol and deltasize init
        List<Vertex> nextSol = new ArrayList<>();
        
        // Loop using Sim Annealing - temp and as long as time is below cutoff
        while (temp > absTemp && elapsedFinal < cutoff) {

            //Gets the next neighboring Solution
            nextSol = getNextSol(graph, curr, randomno);

            // Move current solution to nex solution with a probability!
            // Cost: vertex size of solution
            long cost1 = curr.size();
            long cost2 = nextSol.size();
            
            System.out.println("curr solution's cost:"+curr.size()+ " " +"next solution's cost:"+nextSol.size());
            
            double p = probability(cost1, cost2, temp);
            if(randomno.nextDouble() <= p) {
            	// move to next solution!
            	curr = nextSol;
            }
            
            

            // Cool the Temperature
            temp *= cooling;

            iter++;

            // Time Measure
            endTime = System.currentTimeMillis();
            elapsed =  endTime - start;
            elapsedFinal = elapsed / 1000F;

            // If a Real VC then print it
            
        }


        System.out.println("Total time:	"  + elapsedFinal + "  VC size: " + curr.size());

        // Return final VC
        return curr;
    }    

	
	
	// generate initial solution with greedy
	public static List<Vertex> initialSolution(Graph graph) {
		//record initialization time
		long start_time=System.currentTimeMillis();
		long end_time;
		float run_time= 0;
		
		List<Vertex> solutionList = new ArrayList<>();
		
		ArrayList<Vertex> pq = new ArrayList<>();
		for (Map.Entry<Long, Vertex> entry : graph.getVertices().entrySet()) {
			Vertex newVertex = entry.getValue();
			pq.add(newVertex);
		}
		// sort vertex by vertices they can cover (greedy strategy)
		pq.sort((x,y) -> y.getSize()-x.getSize());
		
		// use a VertexList to track if all the vertices be covered
		List<Long> vertexList = graph.getList();
		while(!vertexList.isEmpty()) {
			Vertex addVertex = pq.remove(0);
			solutionList.add(addVertex);
			if (vertexList.contains(addVertex.getId())) {
				vertexList.remove(Long.valueOf(addVertex.getId()));
			}
			for(Vertex i:addVertex.getVertexIds()) {
				if (vertexList.contains(i.getId())) {
					vertexList.remove(Long.valueOf(i.getId()));
				}
			}
		}
		
		end_time = System.currentTimeMillis();
		run_time = (end_time-start_time)/1000F;
		System.out.printf("%.3f, %d%n", run_time, solutionList.size());
		
		return solutionList;
		
	}
	
	
	
	
	// calculate probability for accepting new solution
	public static double probability(double cost1, double cost2, double temp) {
        if (cost2 < cost1) 
        	return 1;
        return Math.exp((cost1 - cost2) / temp);
    }
	
	
	
	
	public static List<Vertex> getNextSol(Graph graph, List<Vertex> curr, Random random) {
		
		 // (1) make a copy of the curr solution
		List<Vertex> nextSol = new ArrayList<Vertex>(curr);
		
		//(2) pick a random vertex from current solution for removing
		int removeIdx = random.nextInt(curr.size());
		Vertex removeVex = curr.get(removeIdx);
		
		nextSol.remove(removeIdx);
		
		// (3) Make sure the nextSol is still a full VC after removal
		// use a VertexList to track if all the vertices be covered
		List<Long> vertexList = graph.getList();
		List<Vertex> newSol = new ArrayList<>();
		for (Vertex i:nextSol) {
			newSol.add(i);
			if (vertexList.contains(i.getId())) {
				vertexList.remove(Long.valueOf(i.getId()));
			}
			for(Vertex j:i.getVertexIds()) {
				if (vertexList.contains(j.getId())) {
					vertexList.remove(Long.valueOf(j.getId()));
				}
			}
			if(vertexList.isEmpty()) {
				break;
			}
		}
		// if not full VC after removal, add remaining vertex to maintain full VC
		while(!vertexList.isEmpty()) {
			Long addIdx = vertexList.remove(0);
			Vertex addVex = graph.getVertices().get(addIdx);
			newSol.add(addVex);
        }
		// sort new solution by size of adjacent vertices 
		newSol.sort((x,y) -> y.getSize()-x.getSize());
		
		return newSol;
		}
	
	
	
	// Check if current solution is full VC
	public static boolean check(Graph graph, List<Vertex> curr) {
		
		List<Long> vertexList = graph.getList();
		
		for (Vertex i:curr) {
			
			if (vertexList.contains(i.getId())) {
				vertexList.remove(Long.valueOf(i.getId()));
			}
			for(Vertex j:i.getVertexIds()) {
				if (vertexList.contains(j.getId())) {
					vertexList.remove(Long.valueOf(j.getId()));
				}
			}
		}
		return vertexList.isEmpty();
	}
	
	
	
	
	
	
	
	// print solution vertexList!
	public static void printSolution(List<Vertex> solutionList) {
		System.out.println("solution is:");
		for (Vertex i: solutionList) {
			System.out.print(i.getId()+ " ");
		}
	}

}
