package LS1;

import java.util.LinkedList;
import java.util.List;

public class Vertex {

	private final long id;
    private final List<Vertex> vertexIds;
	

    Vertex(long id) {
        this.id = id;
        this.vertexIds = new LinkedList<>();
    }

    public long getId(){
        return this.id;
    }
    
    public List<Vertex> getVertexIds(){
        return this.vertexIds;
    }

    public boolean addFollowVertex(Vertex vertex){
        return this.vertexIds.add(vertex);
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
    
    public int getSize() {
    	return this.vertexIds.size();
    }
    
    public void printVertexList() {
    	for(Vertex i:this.vertexIds) {
    		System.out.print(i.getId() + " ");
    	}
//    	System.out.println();
//    	System.out.print(this.vertexIds.size());
    }

}
