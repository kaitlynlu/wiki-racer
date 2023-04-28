import java.util.*;

class BFS {
    Queue<String> queue;
    Graph graph;
    Map<String, String> parent;
    Set<String> visited;
    public BFS () {
        this.queue = new LinkedList<>();
        this.graph = new Graph();
        this.parent = new HashMap<>();
        this.visited = new HashSet<>();
    }
    public void runBFS(String inLink, String finalLink) {
        this.queue.add(inLink);
        while (!this.queue.isEmpty()) {
            String curr = this.queue.remove();
            this.visited.add(curr);
            // get all the links from the current link
            LinkScraper scrape = new LinkScraper(curr);
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
                    this.queue.clear();
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
        shortestPath.add(inLink);
        Collections.reverse(shortestPath);
        for (String s : shortestPath) {
            System.out.println(s);
        }
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