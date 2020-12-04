package main.java.method.LS1;

import java.util.*;

public class Vertex {
    private final long id;
//    private final List<Vertex> vertices;
    private final Set<Vertex> vertices;

    Vertex(long id) {
        this.id = id;
//        this.vertices = new LinkedList<>();
        this.vertices = new HashSet<>();
    }

//    public List<Vertex> getFollowVertices(){
//        return vertices;
//    }
    /**
     * @author chujiechen
     * @return vertices: neighbors
     */
    public Set<Vertex> getFollowVertices(){
    	return vertices;
    }
    public long getId(){
        return this.id;
    }

    public boolean addFollowVertex(Vertex vertex){
        return this.vertices.add(vertex);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof Vertex))
            return false;

        return this.id == ((Vertex)obj).getId();
    }
}
