import java.io.FileNotFoundException;

public class SAGraph extends Graph{
    private SAGraph(long verticesNum) {
        super(verticesNum);
    }

    public static void main(String []args)  throws FileNotFoundException {
        Graph graph =  SAGraph.read("data\\delaunay_n10.graph");

        System.out.println(graph.getVerticesNum());
    }
}
