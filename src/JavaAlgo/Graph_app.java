package JavaAlgo;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class Graph_app{

	private final HashMap<Long, Vertex_appr> vertices;
	
	private ArrayList<edge> Edges;
	
	public ArrayList<edge> getEdges() {
		return this.Edges;
	}

	public void addEdges() {
		for (Map.Entry<Long, Vertex_appr> entry : this.vertices.entrySet()) {
			long u = entry.getKey();
			for(Vertex_appr v:entry.getValue().getVertexIds()) {
    			edge newEdge = new edge(u, v.getId());
    			this.Edges.add(newEdge);    			
    		}
            
        }
		
    }

	private Graph_app(long initialCapacity) {
        vertices = new HashMap<>((int)initialCapacity);
        Edges = new ArrayList<>();
    }
	
	 public HashMap<Long, Vertex_appr> getVertices() {
			return this.vertices;
		}

    
    public List<Long> getList() {
    	return this.vertices.keySet().stream().collect(Collectors.toList());
    }
     

    public boolean addVertex(Vertex_appr vertex){
        if (!vertices.containsKey(vertex.getId())){
            vertices.put(vertex.getId(), vertex);
            return true;
        }
        else {
            return false;
        }
    }

    public Vertex_appr getVertex(long id){
        return vertices.getOrDefault(id, null);
    }

    public boolean addFollowVertex(Vertex_appr vertex, Vertex_appr followVertex){
        return vertex.addFollowVertex(followVertex);
    }

   
        
        //print Edges
//        System.out.println("\nEdges: ");
//    	for (edge e:graph.Edges) {
//    		e.printEdge();
//    	}
//    	System.out.println("\n"+graph.Edges.size());
    	
        
        //print Graph
//        for (Map.Entry<Long, Vertex> entry : graph.vertices.entrySet()) {
//            System.out.print(entry.getKey()+" : ");
//            entry.getValue().printVertexList();
//            //System.out.print(entry.getValue().getSize());
//            System.out.println();
//        }
    

    public static Graph_app read(String path) throws FileNotFoundException {
    	
        File file = new File(path);
        InputStream inputStream = new FileInputStream(file);

        try (BufferedReader input = new BufferedReader(new InputStreamReader(inputStream))) {
            String line = input.readLine();
            //get vertex number
            long numVertices = new Scanner(line).nextLong();

            //Initialize graph
            Graph_app graph = new Graph_app(numVertices);
            for (long vertex = 1; vertex <= numVertices; vertex++) {
                line = input.readLine();

                Vertex_appr newVertex = graph.getVertex(vertex);
                if (newVertex == null) {
                    newVertex = new Vertex_appr(vertex);
                    graph.addVertex(newVertex);
                }

                Scanner s = new Scanner(line);
                while (s.hasNext()) {
                    //add the follow vertices for current vertex
                    long followVertexId = s.nextLong();
                    Vertex_appr followVertex = graph.getVertex(followVertexId);
                    if (followVertex == null) {
                        followVertex = new Vertex_appr(followVertexId);
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
