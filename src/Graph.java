import java.util.ArrayList;
import java.util.HashMap;

public class Graph {
    HashMap<String, ArrayList<String>> graph;

    public Graph(int n) {
        graph = new HashMap<>();

    }


    public boolean hasEdge(String u, String v) {
        if (this.graph.containsKey(u)) {
            return this.graph.get(u).contains(v);
        }
        return false;
    }


    public void addEdge(String u, String v) {
        if (this.graph.containsKey(u) && !hasEdge(u,v)) {
            this.graph.get(u).add(v);
        } else {
            this.graph.put(u, new ArrayList<>());
            this.graph.get(u).add(v);
        }

    }


    public ArrayList<String> getNeighbors(String u) {
        if (!graph.containsKey(u)) {
            return new ArrayList<>();
        }
        return graph.get(u);
    }

}