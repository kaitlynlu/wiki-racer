import java.util.ArrayList;
import java.util.HashMap;

public class Graph {
    HashMap<String, ArrayList<String>> graph;

    public Graph() {
        graph = new HashMap<>();

    }


    /**
     * checks if edge exists between u and v
     * @param u
     * @param v
     * @return true or false based on if edge exists
     */
    public boolean hasEdge(String u, String v) {
        if (this.graph.containsKey(u)) {
            return this.graph.get(u).contains(v);
        }
        return false;
    }


    /**
     * adds an edge between u and v
     * @param u
     * @param v
     */
    public void addEdge(String u, String v) {
        if (this.graph.containsKey(u) && !hasEdge(u,v)) {
            this.graph.get(u).add(v);
        } else {
            this.graph.put(u, new ArrayList<>());
            this.graph.get(u).add(v);
        }

    }


}