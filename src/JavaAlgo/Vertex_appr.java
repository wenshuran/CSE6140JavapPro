package JavaAlgo;

import java.util.*;

public class Vertex_appr {

	private final long id;
    private final List<Vertex_appr> vertexIds;
	

    Vertex_appr(long id) {
        this.id = id;
        this.vertexIds = new LinkedList<>();
    }

    public long getId(){
        return this.id;
    }
    
    public List<Vertex_appr> getVertexIds(){
        return this.vertexIds;
    }

    public boolean addFollowVertex(Vertex_appr vertex){
        return this.vertexIds.add(vertex);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof Vertex_appr))
            return false;

        return this.id == ((Vertex_appr)obj).getId();
    }
    

}
