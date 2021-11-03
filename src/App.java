import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.*;

public class App {
    public static void main(String[] args) throws Exception {
        Document doc = Jsoup.connect("http://www.39.net/").get();
        Elements links = doc.select("a[href]");
        String title = doc.title();
        System.out.println(title);

        String keywords = doc.select("meta[name=keywords]").first().attr("content");  
        System.out.println("Meta keyword : " + keywords);  
        String description = doc.select("meta[name=description]").get(0).attr("content");  
        System.out.println("Meta description : " + description);
        // System.out.println(doc);

        File file = new File("web.txt");
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
}
