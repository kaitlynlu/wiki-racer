import java.util.*;

public class DFS {
    Stack<String> stack;
    Graph graph;
    Map<String, String> parent;
    Set<String> visited;

    public DFS () {
        this.stack = new Stack<>();
        this.graph = new Graph();
        this.parent = new HashMap<>();
        this.visited = new HashSet<>();
    }

    /**
     * runs DFS starting from the inLink and goes until we hit the finalLink or if we
     * run out of time
     * @param inLink
     * @param finalLink
     * @return list of links in the path from the inLink to the finalLink
     */
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
                         elapsedTime = System.currentTimeMillis() - startTime;
                         if(elapsedTime >= maxTime) {
                             break;
                         }
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

        System.out.println("Path");
        for (String s : shortestPath) {
            System.out.println(s);
        }
        return shortestPath;
    }

    public static void main(String[] args) {
        DFS newDFS = new DFS();
        newDFS.runDFS("https://en.wikipedia.org/wiki/Barack_Obama",
                "https://en.wikipedia.org/wiki/Economic_anthropology");
    }

}
