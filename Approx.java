package LS1;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import java.util.concurrent.CopyOnWriteArraySet;
public class Approx {
	
	
	// generate initial solution with greedy
		public static List<Long> aproxSol(String file, Graph graph, double cutoff, long randSeed) throws IOException {
			//record initialization time
			long start_time=System.currentTimeMillis();
			long end_time;
			float run_time= 0;
			
			// init Random Generator
	        Random randomno = new Random();
	        randomno.setSeed(randSeed);
			
			List<Long> solutionList = new ArrayList<>();
			
//			List<Float> timeList = new ArrayList<>();
//			List<Long> traceList = new ArrayList<>();
			
			//output trace file
			String name_2 = "E:\\classes\\CSE6140 Comp Sci&Engr Algorithms\\final_project\\output\\"+file+"_Approx_600.trace";
			PrintWriter output_2 = new PrintWriter(name_2);
			
			// use a copy of graph edges to track if all the vertices be covered
			CopyOnWriteArraySet<edge> copy = new CopyOnWriteArraySet<edge>(graph.getEdges());		
			while(!copy.isEmpty()) {
				// randomly pick an edge and add to solution
				int Idx = randomno.nextInt(copy.size());
				List<edge> getEdge = new ArrayList<>(copy);
				edge addEdge = getEdge.get(Idx);
				
				
				long addVertex_u = addEdge.u;
				long addVertex_v = addEdge.v;
				
				if(!solutionList.contains(addVertex_u)) {
					solutionList.add(addVertex_u);
					float timestamp = (System.currentTimeMillis()-start_time)/1000F;
					output_2.println(timestamp+", "+addVertex_u);
//					traceList.add(addVertex_u);
//					timeList.add((System.currentTimeMillis()-start_time)/1000F);
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
			}
			
			output_2.close();
			
			Collections.sort(solutionList);
			
			end_time = System.currentTimeMillis();
			run_time = (end_time-start_time)/1000F;
			System.out.printf("Time: %.3f, approximation solution size: %d%n\n", run_time, solutionList.size());
			
			//boolean fullVC = check(graph, solutionList);
	       //System.out.println("\nInitial solution is full VC: "+fullVC);
			
			return solutionList;
			
		}
		
		
		// Check if current solution is full VC
		public static boolean check(Graph graph, List<Long> curr) {
				
						
				for (long id = 1; id <=  graph.getList().size(); id++) {
					Vertex i = graph.getVertices().get(id);
					if (!curr.contains(i.getId())) {
						for(Vertex j:i.getVertexIds()) {
							 if(!curr.contains(j.getId())) {
								return false;
							}
						}
					}
				}
				return true;
			}

}
