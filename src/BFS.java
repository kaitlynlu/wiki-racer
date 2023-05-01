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
    public List<String> runBFS(String inLink, String finalLink) {
        long startTime = System.currentTimeMillis();
        long elapsedTime = 0;
        long maxTime = 10000;
        this.queue.add(inLink);
        while (!this.queue.isEmpty()) {
            String curr = this.queue.remove();
            this.visited.add(curr);
            // get all the links from the current link
            LinkScraper scrape = new LinkScraper(curr);
            if(scrape.currentDoc != null) {
                ArrayList<String> listLinks = scrape.getLinks();
                for (String linkCurr: listLinks) {
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
            }
            elapsedTime = System.currentTimeMillis() - startTime;
            if(elapsedTime >= maxTime) {
                break;
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
        // this works:
//        newBfs.runBFS("https://en.wikipedia.org/wiki/Barack_Obama",
//                "https://en.wikipedia.org/wiki/Harvard_Business_School");

        // this does not work:
        newBfs.runBFS("https://en.wikipedia.org/wiki/Barack_Obama",
                "https://en.wikipedia.org/wiki/Private_university");
    }
}