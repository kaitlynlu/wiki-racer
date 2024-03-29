import java.util.*;

class BFS {
    Queue<String> queue;
    Graph graph;
    Map<String, String> parent;
    Set<String> visited;
    boolean found;

    public BFS () {
        this.queue = new LinkedList<>();
        this.graph = new Graph();
        this.parent = new HashMap<>();
        this.visited = new HashSet<>();
        this.found = false;
    }

    /**
     * runs BFS starting from the inLink and goes until we hit the finalLink or if we
     * run out of time
     * @param inLink
     * @param finalLink
     * @return list of links in the path from the inLink to the finalLink
     */
    public List<String> runBFS(String inLink, String finalLink) {
        long startTime = System.currentTimeMillis();
        long elapsedTime = 0;
        long maxTime = 180000;
        this.queue.add(inLink);
        while (!this.queue.isEmpty()) {
            String curr = this.queue.remove();
            this.visited.add(curr);
            // get all the links from the current link
            LinkScraper scrape = new LinkScraper(curr);
            if(scrape.currentDoc != null) {

                ArrayList<String> listLinks = scrape.getLinks();
                for (String linkCurr: listLinks) {
                    elapsedTime = System.currentTimeMillis() - startTime;
                    if(elapsedTime >= maxTime) {
                        break;
                    }
                    // for each link from the start page, add it one by one to graph
                    graph.addEdge(curr, linkCurr);
                    if (!this.visited.contains(linkCurr)) {
                        this.queue.add(linkCurr);
                        this.parent.put(linkCurr, curr);
                        this.visited.add(linkCurr);
                    }
                    if (linkCurr.equals(finalLink)) {
                        found = true;
                        this.queue.clear();
                        break;
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
        System.out.println("Path:");
        for (String s : shortestPath) {
            System.out.println(s);
        }

        return shortestPath;
    }


    public static void main(String[] args) {
        BFS newBfs = new BFS();
        newBfs.runBFS("https://en.wikipedia.org/wiki/Barack_Obama",
                "https://en.wikipedia.org/wiki/Economic_anthropology");
    }
}