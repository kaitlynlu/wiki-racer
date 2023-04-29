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

    public void runDFS(String inLink, String finalLink) {
        this.stack.push(inLink);
        this.countLevel.put(inLink, 0);
        while (!this.stack.isEmpty()) {
            String curr = this.stack.pop();
            this.visited.add(curr);
            try {
                // get all the links from the current link
                LinkScraper scrape = new LinkScraper(curr);
//                ArrayList<String> listLinks = scrape.getLinks();
                ArrayList<String> listLinks = new ArrayList<>();
                try {
                    listLinks = scrape.getLinks();
                } catch (Exception e) {
                    System.out.println("Error scraping link " + curr + ": " + e.getMessage());
                    continue;
                }
                int getCurrLevel = this.countLevel.get(curr);
                for (String linkCurr: listLinks) {
                    // for each link from the start page, add it one by one to graph
                    graph.addEdge(curr, linkCurr);
                    if (!this.visited.contains(linkCurr) && this.countLevel.get(curr) <= 10 &&
                            !this.countLevel.containsKey(linkCurr)) {
                        this.stack.push(linkCurr);
                        this.parent.put(linkCurr, curr);
                        this.visited.add(linkCurr);
                        this.countLevel.put(linkCurr, getCurrLevel+1);
                    }
                    if (linkCurr.equals(finalLink)) {
                        this.stack.clear();
                        break;
                    }
                 }
            } catch (NullPointerException e) {
                System.out.println("Error with link: " + curr);
                continue;
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
        DFS newDFS = new DFS();
        // this works:
        newDFS.runDFS("https://en.wikipedia.org/wiki/Barack_Obama",
                "https://en.wikipedia.org/wiki/President_of_the_United_States");

        // this does not work:
//        newDFS.runDFS("https://en.wikipedia.org/wiki/Barack_Obama",
//                "https://en.wikipedia.org/wiki/Private_university");
    }
}
