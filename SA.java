package LS1;

import java.io.OutputStreamWriter;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

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

        // generate nextSol
        List<Vertex> nextSol = new ArrayList<>();
        
        // Loop using Sim Annealing - temp and as long as time is below cutoff
        while (temp > absTemp && elapsedFinal < cutoff) {

            //Gets the next neighboring Solution
            nextSol = getNextSol(graph, curr, randomno);

            // Move current solution to next solution with a probability!
            // Cost: vertex size of solution
            long cost1 = curr.size();
            long cost2 = nextSol.size();
            
            //System.out.println("curr solution's cost:"+curr.size()+ " " +"next solution's cost:"+nextSol.size());
            
            double p = probability(cost1, cost2, temp);
            //System.out.println(p);
            if(randomno.nextDouble() <= p) {
            	// move to next solution!
            	curr = nextSol;
            	//System.out.print(" Move!\n");
            }
            
            

            // Cool the Temperature
            temp *= cooling;

            iter++;

            // Time Measure
            endTime = System.currentTimeMillis();
            elapsed =  endTime - start;
            elapsedFinal = elapsed / 1000F;

            
        }
        //sort final solution by vertexId
        curr.sort((x,y) -> (int)x.getId()-(int)y.getId());

        System.out.println("Total time:	"  + elapsedFinal + "  VC size: " + curr.size());
        
        boolean fullVC = SA.check(graph, curr);
        //print final solution!
        System.out.println("\nfinal solution is full VC: "+fullVC);
        for (Vertex i:curr) {
        	System.out.print(i.getId()+ " ");
        }

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
		
		// use a copy of graph edges to track if all the vertices be covered
		CopyOnWriteArrayList<edge> copy = new CopyOnWriteArrayList<edge>(graph.getEdges());		
		while(!copy.isEmpty()) {
			Vertex addVertex = pq.remove(0);
			solutionList.add(addVertex);
			
			for(edge e:copy) {
				long u = e.u;
				long v = e.v;
				// remove edges covered by this new vertex
				if(addVertex.getId()==u || addVertex.getId()==v) {
					copy.remove(e);
				}
			}
		}
		boolean fullVC = SA.check(graph, solutionList);
        System.out.println("\nInitial solution is full VC: "+fullVC);
		
		end_time = System.currentTimeMillis();
		run_time = (end_time-start_time)/1000F;
		System.out.printf("Initialize time: %.3f, initial solution size: %d%n\n", run_time, solutionList.size());
		
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
		
		//(2) pick up to 'num = count' random vertex from current solution to flip
		int count = 0;
		while(count < 2) {
		long Idx = random.nextInt(graph.getVertices().size())+1;
		
		Vertex Vex = graph.getVertices().get(Idx);
		//System.out.println(Vex.getId());
		// if current solution contains this Vex, C' <- C\v
		if(nextSol.contains(Vex)) {
			nextSol.remove(Vex);
			
			 //(3) Make sure the nextSol is still a full VC after flipping, use check(Graph graph, List<Vertex> curr)!
			 //if check, return neighbor solution
				if(check(graph, nextSol)) {
					//System.out.println("next solution remove Vex: "+Idx);
//					nextSol.sort((x,y) -> y.getSize()-x.getSize());
					return nextSol;
					}
				else {nextSol.add(Vex);}
		}
		// if current solution does not contain this Vex, C' <- C U v
		else {
			nextSol.add(Vex);
			//System.out.println("next solution add Vex: "+Idx);
				}
		count += 1;
		}
		//nextSol.sort((x,y) -> y.getSize()-x.getSize());
		return nextSol;
	}
	
	
	
	
	
	// Check if current solution is full VC
		public static boolean check(Graph graph, List<Vertex> curr) {
			// make a copy of graph edges
			CopyOnWriteArrayList<edge> edgeList = new CopyOnWriteArrayList<edge>(graph.getEdges());		
			for(edge e:edgeList) {
				long u = e.u;
				long v = e.v;
				
				for (Vertex i:curr) {
					if(i.getId()==u || i.getId()==v) {
						edgeList.remove(e);
						break;
					}
				}
			}
//			System.out.println("final edgeList size is:"+edgeList.size());
//			for (edge e:edgeList) {
//	    		e.printEdge();
//	    	}			
			return edgeList.isEmpty();
		}
	
	
	
	// print solution vertexList!
	public static void printSolution(List<Vertex> solutionList) {
		System.out.println("solution is:");
		for (Vertex i: solutionList) {
			System.out.print(i.getId()+ " ");
		}
	}

}
