package LS1;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class Graph {

	private final HashMap<Long, Vertex> vertices;

	private Graph(long initialCapacity) {
        vertices = new HashMap<>((int)initialCapacity);
    }
	
	 public HashMap<Long, Vertex> getVertices() {
			return this.vertices;
		}

    
    public List<Long> getList() {
    	return this.vertices.keySet().stream().collect(Collectors.toList());
    }
     

    public boolean addVertex(Vertex vertex){
        if (!vertices.containsKey(vertex.getId())){
            vertices.put(vertex.getId(), vertex);
            return true;
        }
        else {
            return false;
        }
    }

    public Vertex getVertex(long id){
        return vertices.getOrDefault(id, null);
    }

    public boolean addFollowVertex(Vertex vertex, Vertex followVertex){
        return vertex.addFollowVertex(followVertex);
    }

    public static void main(String[] args) throws FileNotFoundException {
        System.out.println(System.getProperty("user.dir"));
        Graph graph = Graph.read("dummy2.graph");
        double cutoff = 2.0;
        long randSeed = 2;
        
        List<Vertex> finalSol = SA.SimulatedAnnealing(graph, cutoff, 2);
        boolean fullVC = SA.check(graph, finalSol);
        System.out.println("final solution is full VC: "+fullVC);
        for (Vertex i:finalSol) {
        	System.out.print(i.getId()+ " ");
        }
        
        //print Graph
//        for (Map.Entry<Long, Vertex> entry : graph.vertices.entrySet()) {
//            System.out.print(entry.getKey()+" : ");
//            entry.getValue().printVertexList();
//            System.out.print(entry.getValue().getSize());
//            System.out.println();
//        }
    }

    public static Graph read(String path) throws FileNotFoundException {
    	
        File file = new File(path);
        InputStream inputStream = new FileInputStream(file);

        try (BufferedReader input = new BufferedReader(new InputStreamReader(inputStream))) {
            String line = input.readLine();
            //get vertex number
            long numVertices = new Scanner(line).nextLong();

            //Initialize graph
            Graph graph = new Graph(numVertices);
            for (long vertex = 1; vertex <= numVertices; vertex++) {
                line = input.readLine();

                Vertex newVertex = graph.getVertex(vertex);
                if (newVertex == null) {
                    newVertex = new Vertex(vertex);
                    graph.addVertex(newVertex);
                }

                Scanner s = new Scanner(line);
                while (s.hasNext()) {
                    //add the follow vertices for current vertex
                    long followVertexId = s.nextLong();
                    Vertex followVertex = graph.getVertex(followVertexId);
                    if (followVertex == null) {
                        followVertex = new Vertex(followVertexId);
                        graph.addVertex(followVertex);
                    }

                    newVertex.addFollowVertex(followVertex);
                }
            }

            return graph;
        } catch (Exception e) {
            System.out.println(e.toString());
            return null;
        }
    }

}
