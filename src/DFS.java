import java.util.*;

public class DFS {
    Stack<String> stack;
    Graph graph;
    Map<String, String> parent;
    Set<String> visited;
    Map<String, Integer> countLevel;
    public DFS () {
        this.stack = new Stack<>();
        this.graph = new Graph();
        this.parent = new HashMap<>();
        this.visited = new HashSet<>();
        this.countLevel = new HashMap<>();
    }

    public List<String> runDFS(String inLink, String finalLink) {
        long startTime = System.currentTimeMillis();
        long elapsedTime = 0;
        long maxTime = 180000;
        boolean found = false;
        this.stack.push(inLink);

        while (!this.stack.isEmpty()) {
            String curr = this.stack.pop();
            // get all the links from the current link
            LinkScraper scrape = new LinkScraper(curr);
            if(scrape.currentDoc != null) {
                 ArrayList<String> listLinks = new ArrayList<>();
                 listLinks = scrape.getLinks();
                 if (!this.visited.contains(curr)) {
                     this.visited.add(curr);
                     for (String linkCurr : listLinks) {
                         // for each link from the start page, add it one by one to graph
                         graph.addEdge(curr, linkCurr);
                         if (!this.visited.contains(linkCurr)) {
                             this.stack.push(linkCurr);
                             this.parent.put(linkCurr, curr);
                         }
                         if (linkCurr.equals(finalLink)) {
                             found = true;
                             this.stack.clear();
                             break;
                         }
                     }
                 }
                elapsedTime = System.currentTimeMillis() - startTime;
                if(elapsedTime >= maxTime) {
                    break;
                }

            }
        }

        List<String> shortestPath = new ArrayList<>();
        String getPath = finalLink;
        while (this.parent.containsKey(getPath)) {
            shortestPath.add(getPath);
            getPath = this.parent.get(getPath);
        }
        if (found) {
            shortestPath.add(inLink);
        }

        Collections.reverse(shortestPath);
        for (String s : shortestPath) {
            System.out.println(s);
        }
        return shortestPath;
    }

    public static void main(String[] args) {
        DFS newDFS = new DFS();
        // this works:
        newDFS.runDFS("https://en.wikipedia.org/wiki/Barack_Obama",
                "https://en.wikipedia.org/wiki/Economic_anthropology");

        // this does not work:
//        newDFS.runDFS("https://en.wikipedia.org/wiki/Barack_Obama",
//                "https://en.wikipedia.org/wiki/Private_university");
    }
}
