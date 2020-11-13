import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;


public class SA {
    public static PrintWriter sol_writer;
    public static PrintWriter trace_writer;
    public static void main(String filename, int cutoff, int seed) throws IOException {
        System.out.println("Main Function hasn't been implemented");
/*        File outDir = new File("").getCanonicalFile();
        String outD = outDir.getParent() + "/output/" + filename;
//		System.out.println("output dir: " + outDir);
        // initialize writers
        String method = "LS2";
        String sol_out = outD + "_" + method + "_" + cutoff + "_" + seed + ".sol";
//		System.out.println("sol_out dir: " + sol_out);
        String trace_out = outD + "_" + method + "_" + cutoff + "_" + seed + ".trace";
        sol_writer = new PrintWriter(sol_out);
        trace_writer = new PrintWriter(trace_out);
        // parse the graph
        HashMap<String,ArrayList<String>> graph = parseGraph(filename);
        // run the Simulated Annealing algorithm
        ArrayList<String> vc = runSAVC(graph, cutoff, seed);
        // write to solution
        Iterator<String> iter = vc.iterator();
        sol_writer.printf("%d%n",vc.size());
        while (iter.hasNext()) {
            sol_writer.printf("%s",iter.next());
            if (iter.hasNext()) {
                sol_writer.printf(",");
            }
        }
        sol_writer.close();
        trace_writer.close();*/
    }


    public static ArrayList<String> runSAVC(HashMap<String,ArrayList<String>> graph,int cutoff, int seed) {
        // set the random seed
//        Random rd = new Random();
//        rd.setSeed(seed);
//        // 1. parse the edges of the current graph
//        ArrayList<Edge> edges = new ArrayList<>();
//
//        // loop through all the entries in the graph
//        for (Map.Entry<String, ArrayList<String>> entry : graph.entrySet()) {
//            String cur_v = entry.getKey();
//            ArrayList<String> neighbor_v = entry.getValue();
//            for (String cur_n_v : neighbor_v) {
//                // some of the lines are empty, so the vertex is isolated
//                if (!cur_n_v.equals("")) {
//                    Edge cur_e = new Edge(cur_v, cur_n_v);
//                    // check if the current edge is already in the list
//                    if (edges.contains(cur_e)) {
//                        continue;
//                    } else {
//                        edges.add(cur_e);
//                    }
//                }
//            }
////			System.out.println("Edge number parse: " + edges.size());
//        }
//        System.out.println("Edge parse done!");
//
//        // used for solution trace
//        @SuppressWarnings("unused")
//        int cur_num_vertex = 0;
//
//        // 2. create the initial solution
//        // edge deletion method -- this method gives bad start
//        ArrayList<String> cur_vc = initVC(edges, graph, rd);
//
//        // start timing
//        long start_t = System.currentTimeMillis();
//        long elapsed_t_milis = 0;
//        float elapsed_t = 0;
//
//        System.out.println("initial verex cover: " + cur_vc);
//        // SA parameters
//        double T = 1000000;
//        double cool_rate = 0.95;
//        double T_limit = 0.0;
//
//        int best_cost = calc_cost(edges, cur_vc);
//        System.out.println("initial cost: " + best_cost);
//        ArrayList<String> graph_vertex = new ArrayList<>(graph.keySet());
//        // 3. loop while cut-off time not reached
//        while (elapsed_t < cutoff && T > T_limit) {
//            // 3.1 construct the neighbor, initialized with the current vertex cover
//            // construct neighbor is fast
//            ArrayList<String> neighbor = new ArrayList<>(cur_vc);
//            int flip_idx = rd.nextInt(graph_vertex.size());
//            boolean in_C = false;
//            String flip_vertex = graph_vertex.get(flip_idx);
//            // the random vertex is in the previous vertex cover
//            in_C = cur_vc.contains(flip_vertex);
//            if (in_C) {
////				 if in cur_vc, remove it from its neighbor
////				 check if neighbor is still a vc
//                boolean all_cover = true;
//                ArrayList<String> flip_neighbor_v = graph.get(flip_vertex);
//                for (String nv : flip_neighbor_v) {
//                    if (!neighbor.contains(nv)) {
//                        // not vc
//                        all_cover = false;
//                        break;
//                    }
//                }
//                if (all_cover) {
//                    neighbor.remove(flip_vertex);
//                } else {
//                    neighbor = cur_vc;
//                }
////				neighbor.remove(flip_vertex);
//            } else {
//                neighbor.add(flip_vertex);
//            }
//
//            // 3.2 delta F
//            int delta_cost = calc_cost(edges, neighbor) - calc_cost(edges, cur_vc);
//            // 3.3 decide
//            if (delta_cost < 0) {   // accept new solution
//                cur_vc = neighbor;
//            } else {  // delta_cost >= 0
//                double p = 0.0;
//                int deg = graph.get(flip_vertex).size() / edges.size();
//                if (in_C) { // the vertex was in previous vertex cover
//                    p = Math.exp(-delta_cost * (1 + deg) / T);
//                    if (p > rd.nextDouble()) {
//                        cur_vc = neighbor;
//                    }
//                } else { // the vertex is added to the neighbor
//                    p = Math.exp(-delta_cost * (1 - deg) / T);
//                    if (p > rd.nextDouble()) {
//                        cur_vc = neighbor;
//                    }
//                }
//            }
//
//            elapsed_t_milis = System.currentTimeMillis() - start_t;
//            elapsed_t = (float) elapsed_t_milis / 1000;
//
//            int cur_cost = calc_cost(edges, cur_vc);
//            // write to trace
//            if (cur_cost < best_cost) {
//                best_cost = cur_cost;
//                cur_num_vertex = cur_vc.size();
//                trace_writer.printf("%.6f, %d%n", elapsed_t, cur_cost);
//                System.out.println("elapsed time: " + elapsed_t);
//                System.out.println("current num of vertices: " + cur_cost);
//            }
//            if (elapsed_t > cutoff) {
//                break;
//            }
//            // update temperation
//            T *= cool_rate;
//        }
//        // construct final vertex cover
//        for (Edge e : edges) {
//            if (!cur_vc.contains(e.start_v) && !cur_vc.contains(e.end_v)) {
//                cur_vc.add(e.start_v);
//            }
//        }
//        return cur_vc;

        ArrayList<String> a = new ArrayList<String>();
        return a;
    }

    public static int calc_cost(ArrayList<Vertex> vc, Float A, Float B) {
        // The objective F = A \sum vi + B \sum_i \sum_j dij(1-vi*vj-vi-vj)
        int covered = vc.size();
        return covered;
    }

//    // Edge Deletion for initialization
//    public static ArrayList<String> initVC(ArrayList<Edge> edges,
//                                           HashMap<String, ArrayList<String>> graph, Random rd) {
//        // a list of vertices
//        ArrayList<String> cover_list = new ArrayList<>();
//        // make a copy of the edges
//        ArrayList<Edge> edges_clone = new ArrayList<>(edges);
//        // get the index of edge to delete
//        int edge_idx;
//        Edge e_add;
//        String u;
//        String v;
//        while (!edges_clone.isEmpty()) {
//            // randomly select one edge
//            edge_idx = rd.nextInt(edges_clone.size());
//            e_add = edges_clone.get(edge_idx);
//            // add both vertices into cover set
//            u = e_add.start_v;
//            v = e_add.end_v;
//            cover_list.add(u);
//            cover_list.add(v);
//            // remove both vertices from the graph vertices
//            // also the edges connecting them will be removed
//            ArrayList<Edge> edges_remove = new ArrayList<>();
//            for (Edge cur_e : edges) {
//                if (cur_e.start_v.equals(u) || cur_e.end_v.equals(u) ||
//                        cur_e.start_v.equals(v) || cur_e.end_v.equals(v)) {
//                    edges_remove.add(cur_e);
//                }
//            }
//            edges_clone.removeAll(edges_remove);
//            System.out.println("edge number: " + edges_clone.size());
//        }
//        return cover_list;
//    }
//
//    // parse graph, returns vertex-neighbors
//    public static HashMap<String,ArrayList<String>> parseGraph(String filename) throws IOException {
//        HashMap<String,ArrayList<String>> graph = new HashMap<>();
//        String[] size;
//        String[] neighbors;
//        // file path
//        File dir = new File("").getCanonicalFile();
//        String dataDir = dir.getParent() + "/Data/" + filename + ".graph";
//        BufferedReader br = new BufferedReader(new FileReader(dataDir));
//        // first line
//        String line = br.readLine();
//        size = line.split(" ");
//        // graph info
//        int num_vertex = Integer.parseInt(size[0].trim());
//
//        // loop through all the vertices
//        int vertex_id = 1;
//        while (vertex_id < num_vertex + 1) {
//            line = br.readLine();
//            neighbors=line.split(" ");
//            String v_id = String.valueOf(vertex_id);
//            ArrayList<String> nei = new ArrayList<>(Arrays.asList(neighbors));
//            // put current entry to the HashMap
//            graph.put(v_id, nei);
//            vertex_id++;
//        }
//        br.close();
//        System.out.println("parseGraph done!");
//        return graph;
//    }
//
}