package BnB;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class BranchAndBound {
    private static HashMap<Graph, Integer> graph2RMap = new HashMap<>();
    public static Graph Proc_A(Graph G, Graph C){
//    	System.out.println("Proc_A running...");
        while(C.hasRemovableVertex()){
//        	System.out.println("having removable vertices...");
            int rmax = 0;
                Vertex vMax = null;
            for(Vertex v : C.getRemovableVertices()){
                int r;
                if (graph2RMap.containsKey(C.removeVertex(v))){
                    r = graph2RMap.get(C.removeVertex(v));
                }
                else {
                    r = C.removeVertex(v).getRemovableVertices().size();
                    graph2RMap.put(C, r);
                }
                if (r >= rmax){
                    vMax = v;
                    rmax = r;
                }
            }
            C = C.removeVertex(vMax);
        }
//        System.out.println("Proc_A done.");
        return C;
    }

    public static Graph Proc_B(Graph G, Graph C, int n){
//    	System.out.println("Proc_B running...");
        for(int i = 1; i <= n; i++){
//            Vertex v = C.findVwithOneNeighboroutC();
//            Vertex w = C.findNeighboroutC(v);
            Vertex[] vw = C.findVW(G);
            Vertex v = vw[0];
            Vertex w = vw[1];

            if (v != null){
                C.addVertex(w);
                C = Proc_A(G, C.removeVertex(v));
            }
        }
        return C;
    }

    public static Graph branchAndBound(Graph G, int k){
        int n = G.getVerticesNum();
        List<Graph> CList = new ArrayList<>(n);
        Graph rnt = null;
        System.out.println("Part I");
        long start = System.currentTimeMillis();
        long end;
        for (long i : G.getDegreeSortedVertices()){
            Graph C = G.removeVertex(G.getVertex(i));
            C = Proc_A(G, C);
            for (int r = 1; r <= n-k; r++){
                C = Proc_B(G, C, r);
                if(rnt == null){
                    rnt = C;
                    end = System.currentTimeMillis();
                    System.out.println("After "+(end-start)/1000+" second, count is " + rnt.getVerticesNum());
                }
                else if(C.getVerticesNum() < rnt.getVerticesNum()){
                    rnt = C;
                    end = System.currentTimeMillis();
                    System.out.println("After "+(end-start)/1000+" second, count is " + rnt.getVerticesNum());
                }
            }
            CList.add(C);
        }
        if (rnt.getVerticesNum() < k){
            return rnt;
        }
        System.out.println("Part II");
        List<Graph> VCs = new ArrayList<>();
        for (int i = 1; i <=n; i++){
            for (int j = i+1; j <= n; j++){
            	Graph Cij = CList.get(i-1).unionGraph(CList.get(j-1));
            	Cij = Proc_A(G, Cij);
            	for(int r = 1; r <= n-k; r++){
            		Cij = Proc_B(G, Cij, r);
            	}
            	VCs.add(Cij);
            }
        }
		/**
		 * The results of Cij will be the 
		 * minimum vertex covers of the problem
		 */

        for(Graph VC: VCs) {
        	System.out.println(VC);
        	if(rnt == null) rnt = VC;
        	else if(VC.getVerticesNum() < rnt.getVerticesNum()){
        		rnt = VC;
        	}
        }
        return rnt;
    }

    public static void main(String[] args) throws FileNotFoundException {
//        Graph G = Graph.read("src\\BnB\\delaunay_n10.graph");
//    	Graph G = Graph.read("src/BnB/delaunay_n10.graph");
    	Graph G = Graph.read("src/BnB/karate.graph");
//    	Graph G = Graph.read("src/BnB/karate.graph");
    	System.out.println("running...");
        int n = G.getVerticesNum();
        int k = n - (int)Math.ceil((double)n/(G.getDelta()+1));
        System.out.println("k is: " + k);
        Graph res = branchAndBound(G, k);
        
        System.out.println("A naive printout of the res Graph: ");
        System.out.println(res);
    }
}
