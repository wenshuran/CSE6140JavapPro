package JavaAlgo;

import java.io.*;
import java.util.*;
import java.util.concurrent.CopyOnWriteArraySet;


public class Approx implements Algo{
	 private PrintWriter Output;
//	 private PrintWriter OutputTrace;
	
		
	// generate initial solution with greedy
		public static List<Long> aproxSol(String filename, Graph_app graph, int cutoff, int randSeed) throws FileNotFoundException {
			
			//record initialization time
			long start_time=System.currentTimeMillis();
			long end_time;
			float run_time= 0;
			
			// init Random Generator
	        Random randomno = new Random();
	        randomno.setSeed(randSeed);
			
			List<Long> solutionList = new ArrayList<>();
			
			//output trace file
			
			String OutputTracePath = "output/"+filename +"_Approx_" +cutoff+"_"+randSeed+".trace";
//			String OutputTracePath = "E:\\classes\\CSE6140 Comp Sci&Engr Algorithms\\final_project\\output\\"+filename +"_Approx_" +cutoff+"_"+randSeed+".trace";
			PrintWriter OutputTrace = new PrintWriter(OutputTracePath);
			
			// use a copy of graph edges to track if all the vertices be covered
			CopyOnWriteArraySet<edge> copy = new CopyOnWriteArraySet<edge>(graph.getEdges());
			
			while(!copy.isEmpty() && run_time < cutoff) {
				// randomly pick an edge and add to solution
				int Idx = randomno.nextInt(copy.size());
				List<edge> getEdge = new ArrayList<>(copy);
				edge addEdge = getEdge.get(Idx);
				
				
				long addVertex_u = addEdge.u;
				long addVertex_v = addEdge.v;
				
				if(!solutionList.contains(addVertex_u)) {
					solutionList.add(addVertex_u);
//					float timestamp = (System.currentTimeMillis()-start_time)/1000F;
//					OutputTrace.println(timestamp+", "+solutionList.size());

				}
				
				if(!solutionList.contains(addVertex_v)) {
					solutionList.add(addVertex_v);
				}
								
				for(edge e:copy) {
					long u = e.u;
					long v = e.v;
					// remove edges covered by this new vertex
					if(addVertex_u==u || addVertex_v==v) {
						copy.remove(e);
					}
				}
				end_time = System.currentTimeMillis();
				run_time = (end_time-start_time)/1000F;
			}
			
			Collections.sort(solutionList);
			
			end_time = System.currentTimeMillis();
			run_time = (end_time-start_time)/1000F;
			// output trace file
			OutputTrace.println(run_time+", "+solutionList.size());
			OutputTrace.close();
			System.out.printf("Time: %.3f, approximation solution size: %d%n\n", run_time, solutionList.size());
			
			boolean fullVC = check(graph, solutionList);
	        System.out.println("\nInitial solution is full VC: "+fullVC);
			
			return solutionList;
			
		}
		
		
		// Check if current solution is full VC
		public static boolean check(Graph_app graph, List<Long> curr) {
				
						
				for (long id = 1; id <=  graph.getList().size(); id++) {
					Vertex_appr i = graph.getVertices().get(id);
					if (!curr.contains(i.getId())) {
						for(Vertex_appr j:i.getVertexIds()) {
							 if(!curr.contains(j.getId())) {
								return false;
							}
						}
					}
				}
				return true;
			}
		
		
		
		// main function
		public void run(String filename, int time, int seed) throws FileNotFoundException {
			 
//			Graph_app G = Graph_app.read("src/JavaAlgo/"+ filename);
			Graph_app G = Graph_app.read("E:\\classes\\CSE6140 Comp Sci&Engr Algorithms\\final_project\\DATA\\DATA\\"+ filename + ".graph");
	        G.addEdges();
//	        String filename = "jazz";
//	        int cutoff = 600;
//	        int randSeed = 6140;
	        
	        //open the output file
	        String OutputPath = "output/"+filename +"_Approx_"+ time +"_"+ seed+".sol";
//	        String OutputPath = "E:\\classes\\CSE6140 Comp Sci&Engr Algorithms\\final_project\\output\\"+filename +"_Approx_"+ time +"_"+ seed+".sol";
	       
	        
	        Output = new PrintWriter(OutputPath);
	       
	        //output result in the file
	        int size_VC = G.getList().size();
	        
	        System.out.println("Original vertex size: " + size_VC);
	        
	        
	        List<Long> solution = aproxSol(filename, G, time, seed);
	        // print solution and output solution file:
	        Output.println(solution.size());
	        for(int i = 0; i < solution.size(); i++) {
	        	System.out.print(solution.get(i)+" ");
	        	Output.print(solution.get(i)+",");
	        }
	        Output.close();
	 }

}
