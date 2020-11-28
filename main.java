package LS1;

import java.io.PrintWriter;
import java.io.IOException;
import java.util.List;

public class main {
	 public static void main(String[] args) throws IOException {
		 
		 	String file = "as-22july06";
	       
	        Graph graph = Graph.read("E:\\classes\\CSE6140 Comp Sci&Engr Algorithms\\final_project\\DATA\\DATA\\"+ file + ".graph");
	        graph.addEdges();
	        double cutoff = 600.0;
	        long randSeed = 6140;
	        
	        //open the output file
	       
	        String name = "E:\\classes\\CSE6140 Comp Sci&Engr Algorithms\\final_project\\output\\"+file+"_Approx_600.sol";
	        
	        PrintWriter output_1 = new PrintWriter(name);
	       
	     
	        //write result in the file
	        int size_VC = graph.getList().size();
	        output_1.println(size_VC);
	        System.out.println("Original vertex size: " + size_VC);
	        
	        
	        List<Long> solution = Approx.aproxSol(file, graph, cutoff, randSeed);
	        // print solution:
	        for(int i = 0; i < solution.size(); i++) {
	        	System.out.print(solution.get(i)+" ");
	        	output_1.print(solution.get(i)+",");
	        }
	       
	        output_1.close();
	 }
	 
}
