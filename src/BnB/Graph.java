package BnB;

import java.io.*;
import java.util.*;

public class Graph {
    private final HashMap<Long, Vertex> vertices;
    private int delta; //max edge num from one vertex

    private Graph() {
        vertices = new HashMap<>();
        delta = 0;
    }
    
    private Graph(long verticesNum) {
        vertices = new HashMap<>((int)verticesNum);
        delta = 0;
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

    public int getVerticesNum(){
        return vertices.size();
    }

    public int getDelta(){
        return delta;
    }

    public boolean isRemovableVertex(Vertex vertex){
//        List<Vertex> vertices = vertex.getFollowVertices();
    	Set<Vertex> vertices = vertex.getFollowVertices();
        //看它所有followVertex还在不在vertices中 如果都在则可，有一个不在就不可
        for (Vertex v : vertices){
            if (!this.vertices.containsKey(v.getId())){
                return false;
            }
        }
        return true;
    }

    public boolean hasRemovableVertex(){
        for (Vertex v : this.vertices.values()){
            if (this.isRemovableVertex(v)){
                return true;
            }
        }
        return false;
    }

    public List<Vertex> getRemovableVertices(){
        List<Vertex> vertices = new ArrayList<>();
        for (Vertex v : this.vertices.values()){
            if (this.isRemovableVertex(v)){
                vertices.add(v);
            }
        }
        return vertices;
    }
    /**
     * TODO
     * followVertex won't get removed
     * - Chujie
     */
    public Graph removeVertex(Vertex vertex){
        if (vertex == null){
            return this;
        }
        Graph graph = this.deepClone();
        graph.vertices.remove(vertex.getId());
        return graph;
    }


	public Vertex getVertex(long id){
        return vertices.getOrDefault(id, null);
    }


	
    public static void main(String[] args) throws FileNotFoundException {
        System.out.println(System.getProperty("user.dir"));
//        Graph graph = Graph.read("src\\BnB\\delaunay_n10.graph");
//        Graph graph = Graph.read("src/BnB/delaunay_n10.graph");
        Graph graph = Graph.read("src/BnB/karate.graph");
    }

//    public static String genKey(Vertex u, Vertex v){
//        Vertex smaller;
//        Vertex larger;
//        if (u.getId() < v.getId()){
//            smaller = u;
//            larger = v;
//        }
//        else {
//            smaller = v;
//            larger = u;
//        }
//        StringBuilder sb = new StringBuilder();
//        sb.append(smaller.getId());
//        sb.append("_");
//        sb.append(larger.getId());
//        return sb.toString();
//    }

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
                int edgeNum = 0;
                while (s.hasNext()) {
                    //add the follow vertices for current vertex
                    long followVertexId = s.nextLong();
                    Vertex followVertex = graph.getVertex(followVertexId);
                    if (followVertex == null) {
                        followVertex = new Vertex(followVertexId);
                        graph.addVertex(followVertex);
                    }

                    newVertex.addFollowVertex(followVertex);
                    edgeNum++;
                }
                graph.delta = Math.max(graph.delta, edgeNum);
            }

            return graph;
        } catch (Exception e) {
            System.out.println(e.toString());
            return null;
        }
    }
    
    /**
     * @author chujiechen
     * @return graphDC: a deepCloned new graph
     */
    public Graph deepClone() {
    	int numVertices = this.getVerticesNum();
    	Graph graphDC = new Graph(numVertices);
    	for(long i: this.vertices.keySet()) {
    		Vertex thisU = this.vertices.get(i);
    		if(thisU != null) {
    			Vertex thatU = new Vertex(i);
    			for(Vertex thisV: thisU.getFollowVertices()) {
    				 Vertex thatV = new Vertex(thisV.getId());
    				 thatU.addFollowVertex(thatV);
    			}
    			graphDC.addVertex(thatU);
    		}
    	}
		return graphDC;
	}
    
    /**
     * @author chujiechen
     * @param Cj: Vertex cover C_j
     * @return Cij: Vertex cover C_{i,j} = C_i \cup C_j
     * this is C_i
     */
	public Graph unionGraph(Graph Cj) {
		Set<Integer> idSet = new HashSet<>();
		for(long i: this.vertices.keySet()) {
			if(this.vertices.get(i) != null) idSet.add((int) i);
		}
		for(long i: Cj.vertices.keySet()) {
			if(Cj.getVertex(i) != null) idSet.add((int) i);
		}
		// get union number of vertices
		int totNumVertex = idSet.size();
		Graph Cij = new Graph(totNumVertex);
		// copy graph from Ci
		for(long i: this.vertices.keySet()) {
    		Vertex thisU = this.getVertex(i);
    		if(thisU != null) {
    			Vertex thatU = new Vertex(i);
    			for(Vertex thisV: thisU.getFollowVertices()) {
    				 Vertex thatV = new Vertex(thisV.getId());
    				 thatU.addFollowVertex(thatV);
    			}
    			Cij.addVertex(thatU);
    		}
    	}
		for(long i: Cj.vertices.keySet()) {
    		Vertex U = Cj.getVertex(i);
    		if(U != null) {
				Vertex newU = Cij.getVertex(U.getId());
    			// if newU is not in Cij
    			if(newU == null) {
    				newU = new Vertex(U.getId());
    				for(Vertex V: U.getFollowVertices()) {
    					Vertex newV = new Vertex(V.getId());
    					newU.addFollowVertex(newV);
    				}
    				Cij.addVertex(newU);
    			} else {
        			// else newU is already in Cij
    				for(Vertex V: U.getFollowVertices()) {
    					Vertex newV = new Vertex(V.getId());
    					newU.addFollowVertex(newV);
    				}
    			}
    		}
    	}
		return Cij;
	}
    
	/**
	 * Find v \in C such that v has exactly one neighbor w not \in C
	 * @return array consisting of v and w;
	 */
	public Vertex[] findVW(Graph G) {
		// TODO Auto-generated method stub
		for(long i :this.vertices.keySet()) {
			// the full list of neighbors is from graph G
			Vertex v = G.getVertex(i);
			if(v == null) continue;
			Set<Vertex> neighbors = G.getVertex(i).getFollowVertices();
			int numOfNotInC = 0;
			Vertex w = null;
			for(Vertex x: neighbors) {
				// if the neighbor is not in C
				if(this.getVertex(x.getId()) == null) {
					w = x;
					numOfNotInC++;
					if(numOfNotInC > 1) {
						w = null;
						break;
					}
				}
			}
			if(numOfNotInC == 1 && w != null) return new Vertex[] {v, w};
		}
		return new Vertex[] {null, null};
	}
}
