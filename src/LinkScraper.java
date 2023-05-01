
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
     * Gets first 25 links (<= 25) from wikipedia page return them as a list
     * Webscraping helper function within this class:
     * Input: a wikipedia link
     * Output: a list of 25 /wiki/ links that they connect to
     * Helper function to check if the link u scraped has the entire url (usually the scrapped href are just /wiki/__),
     * if it doesn’t append it to get the whole link.
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

    public ArrayList<String> getLinks() {
        Elements divs = currentDoc.select("#mw-content-text");
        Element div = divs.get(0);
        int limit = 50;
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
