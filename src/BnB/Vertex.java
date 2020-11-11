package BnB;

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
}
