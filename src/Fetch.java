import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.*;


public class Fetch {
    private int count;
    private String url;
    public Elements links;
    public Fetch(String url, int count) {
        this.count = count;
        this.url = url;
    }
    public void get() throws IOException{
        Document doc = Jsoup.connect(url).get();
        links = doc.select("a[href]");
        // String title = doc.title();
        // System.out.println(title);

        // String keywords = doc.select("meta[name=keywords]").first().attr("content");  
        // System.out.println("Meta keyword : " + keywords);  
        // String description = doc.select("meta[name=description]").get(0).attr("content");  
        // System.out.println("Meta description : " + description);
        // System.out.println(doc);

        File file = new File("web" + count + ".txt");
        if(!file.exists()){
            file.createNewFile();
        }
        FileWriter fileWriter = new FileWriter(file.getAbsoluteFile());
        BufferedWriter bw = new BufferedWriter(fileWriter);
        for (Element link : links) {
            bw.write("text : " + link.text() + "\t");
            bw.write("link : " + link.attr("href") + "\n");
        }
        bw.close();
    }

    public Elements getLinks() {
        return links;
    }
}
