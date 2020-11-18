package BnB;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class BranchAndBound {
    public static Graph Proc_A(Graph G, Graph C){
        while(C.hasRemovableVertex()){
            int rmax = 0;
            Vertex vMax = null;
            for(Vertex v : C.getRemovableVertices()){
                int r = C.removeVertex(v).getRemovableVertices().size();
                if (r > rmax){
                    vMax = v;
                    rmax = r;
                }
            }
            C = C.removeVertex(vMax);
        }
        return C;
    }

    public static Graph Proc_B(Graph G, Graph C, int n){
        for(int i = 1; i <= n; i++){
            Vertex v = C.findVwithOneNeighboroutC();
            Vertex w = C.findNeighboroutC(v);
            if (v != null){
                C.addVertex(w);
                C = Proc_A(G, C.removeVertex(v));
            }
        }
        return C;
    }

    public static void branchAndBound(Graph G, int k){
        int n = G.getVerticesNum();
        List<Graph> CList = new ArrayList<>(n);
        for (int i = 1; i <= n; i++){
            Graph C = G.removeVertex(G.getVertex(i));
            C = Proc_A(G, C);
            for (int r = 1; r <= n-k; r++){
                C = Proc_B(G, C, r);
            }
            CList.add(C);
        }
        for (int i = 1; i <=n; i++){
            for (int j = i+1; j <= n; j++){
                Graph C = Proc_A(G, CList.get(i).unionGraph(CList.get(j)));
                for (int r = 1; r <= n-k; r++){
                    C = Proc_B(G, C, r);
                }
            }
        }
    }

    public static void main(String[] args) throws FileNotFoundException {
        Graph G = Graph.read("src\\BnB\\delaunay_n10.graph");
        int n = G.getVerticesNum();
        int k = n - (int)Math.ceil((double)n/(G.getDelta()+1));
        branchAndBound(G, k);
    }
}
