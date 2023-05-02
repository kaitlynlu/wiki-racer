
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.jsoup.parser.Parser;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class LinkScraper {
    private String baseURL;
    Document currentDoc;
    ArrayList<String> output;
    /**
     * Connects to the wikipedia link provided
     * input: wikipedia link
     */

    public LinkScraper(String base) {
        this.baseURL = base;
        this.output = new ArrayList<String>();
        try {
            this.currentDoc = Jsoup.parse(new URL(this.baseURL).openStream(), "ISO-8859-1", this.baseURL);

        } catch (IOException e) {
            System.out.println("URL not found");
        }
    }

    /**
     * Gets first 75 links (<= 75) from wikipedia page return them as a list
     * Links must be other wikipedia links
     * @return the list of links
     */
    public ArrayList<String> getLinks() {
        Elements divs = currentDoc.select("#mw-content-text");
        Element div = divs.get(0);
        int limit = 75;
        List<Element> links = div.select("a[href^='/wiki/']");
        for (int i = 0; i < limit && i < links.size(); i++) {
            Element link = links.get(i);
            String l = link.attr("href");
            if (l.length() > 13 && !l.substring(6,13).equals("Special")) {
                String validLink = "https://en.wikipedia.org" + l;
                output.add(validLink);
            } else {
                limit++;
            }
        }
        return output;
    }

    public static void main(String[] args) {
        LinkScraper l = new LinkScraper("https://en.wikipedia.org/wiki/Barack_Obama");
        System.out.println(l.getLinks());
    }
}
