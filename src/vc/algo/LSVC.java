package vc.algo;

import vc.base.Graph;
import vc.base.Vertex;

import java.io.FileNotFoundException;
import java.util.*;
import java.util.logging.Logger;
import java.util.Comparator;

class LSVC {
    protected HashMap<Long, Integer> vc;
    protected Graph graph;
    protected int size = -1;

    public LSVC(){
        vc = new HashMap<>();
    }

    public LSVC(String filePath) throws FileNotFoundException {

        graph = Graph.read(filePath);

        Random rd = new Random();

        if(!initVCGreedy(rd)){
            Logger.getGlobal().info("Initialize Vertex Cover Failed!");
            System.exit(-1);
        }else {
            System.out.println("Check for Initialization Passed.");
        }

    }

    protected boolean initVC(Random rd){

        vc = new HashMap<>(graph.getVerticesNum());
        ArrayList<Long> vertexIds = graph.getVertexIds();
        for(Long vertexId: vertexIds){
            vc.put(vertexId, 0);
        }

        ArrayList<Long> vertexPool = new ArrayList<>(graph.getVertexIds());

        long curVertexId;
        long followerVertexId;
        boolean followervertexClear;

        while(!vertexPool.isEmpty()){
            curVertexId = vertexPool.get(rd.nextInt(vertexPool.size()));
            vc.put(curVertexId, 1);
            vertexPool.remove(curVertexId);
            List<Vertex> followVertices = graph.getVertex(curVertexId).getFollowVertices();

            for(Vertex followVertex : followVertices){
                followerVertexId = followVertex.getId();
                if(vertexPool.contains(followerVertexId)){
                    followervertexClear = true;
                    for(Vertex followFollowerVertex : graph.getVertex(followerVertexId).getFollowVertices()){
                        if(vc.get(followFollowerVertex.getId()) == 0){
                            followervertexClear = false;
                            break;
                        }
                    }
                    if(followervertexClear){
                        vertexPool.remove(followerVertexId);
                    }
                }
            }

        }

        getSize(true);

        return checkVC();
    }

    protected boolean initVCFast(Random rd){

        vc = new HashMap<>(graph.getVerticesNum());
        ArrayList<Long> vertexIds = graph.getVertexIds();
        for(Long vertexId: vertexIds){
            vc.put(vertexId, 0);
        }

        ArrayList<Long> vertexPool = new ArrayList<>(graph.getVertexIds());

        class MyVertexIdxComparator implements Comparator<Long>{

            public int compare(Long o1, Long o2) {
                int i1 = graph.getVertex(o1).getFollowVertices().size();
                int i2 = graph.getVertex(o2).getFollowVertices().size();
                if (i1 < i2){
                    return -1;
                }
                if (i1 > i2){
                    return 1;
                }
                return 0;
            }
        }

        Collections.sort(vertexPool,new MyVertexIdxComparator());

        long curVertexId;
        long followerVertexId;
        boolean followervertexClear;

        while(!vertexPool.isEmpty()){
            curVertexId = vertexPool.get(0);
            vc.put(curVertexId, 1);
            vertexPool.remove(curVertexId);
            List<Vertex> followVertices = graph.getVertex(curVertexId).getFollowVertices();

            for(Vertex followVertex : followVertices){
                followerVertexId = followVertex.getId();
                if(vertexPool.contains(followerVertexId)){
                    followervertexClear = true;
                    for(Vertex followFollowerVertex : graph.getVertex(followerVertexId).getFollowVertices()){
                        if(vc.get(followFollowerVertex.getId()) == 0){
                            followervertexClear = false;
                            break;
                        }
                    }
                    if(followervertexClear){
                        vertexPool.remove(followerVertexId);
                    }
                }
            }

        }
        getSize(true);
        return checkVC();
    }

    protected boolean initVCGreedy(Random rd){
        vc = new HashMap<>(graph.getVerticesNum());
        ArrayList<Long> vertexIds = graph.getVertexIds();
        for(Long vertexId: vertexIds){
            vc.put(vertexId, 0);
        }

        for(Long curVertexId: vertexIds){
            List<Vertex> followVertices = graph.getVertex(curVertexId).getFollowVertices();
            for(Vertex followVertex : followVertices){
                if(curVertexId < followVertex.getId()){
                    vc.put(curVertexId, 1);
                    break;
                }
            }
        }
        getSize(true);
        return checkVC();
    }

    public boolean checkVC(){
        long curVertexId;
        long followerVertexId;
        for (Map.Entry<Long, Integer> entry : vc.entrySet()) {
            curVertexId = entry.getKey();
            List<Vertex> followVertices = graph.getVertex(curVertexId).getFollowVertices();

            for(Vertex followVertex : followVertices){
                followerVertexId = followVertex.getId();
                if( followerVertexId > curVertexId && entry.getValue() == 0 && vc.get(followerVertexId) == 0){
                    System.out.println(vc);
                    System.out.println(curVertexId+", "+followVertex.getId());
                    return false;
                }
            }

        }
        return true;
    }

    public int getSize(boolean forceRedo){
        if(forceRedo){
            size = -1;
        }
        return _getSize();
    }

    private int _getSize(){
        if(size == -1) {
            size = 0;
            for (Map.Entry<Long, Integer> entry : vc.entrySet()) {
                size += entry.getValue();
            }
        }
        return size;
    }

    public HashMap<Long, Integer> getVC(){
        return vc;
    }

    public void printVC(){
        int cnt = 0;
        System.out.print("Vertex IDs in VC:");
        for (Map.Entry<Long, Integer> entry : vc.entrySet()) {
            if(entry.getValue() == 1) {
                if (cnt % 20 == 0)
                    System.out.print("\n" + entry.getKey());
                else {
                    System.out.print("\t" + entry.getKey());
                }
                cnt += 1;
            }
        }
        System.out.println();
    }
}

