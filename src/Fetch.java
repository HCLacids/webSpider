import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


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
        // System.out.println(doc);
        links = doc.select("a[href]");
        // Element content = doc.select("div.art_content").first();
        // if(content != null) System.out.println(content);
        // String title = doc.title();
        // System.out.println(title);   
        // System.out.println(doc);

        File file = new File("web" + count + ".txt");
        if(!file.exists()){
            file.createNewFile();
        }
        FileWriter fileWriter = new FileWriter(file.getAbsoluteFile());
        BufferedWriter bw = new BufferedWriter(fileWriter);
        for (Element link : links) {
            if(link.text().length() > 10 && link.text().length() < 15) {
                String regex = "https?://(\\w|-)+(\\.(\\w|-)+)+(/(\\w+(\\?(\\w+=(\\w|%|-)*(\\&\\w+=(\\w|%|-)*)*)?)?)?)+";
                Pattern p  = Pattern.compile(regex);
                Matcher m = p.matcher(link.attr("href"));
                if(m.matches()){
                    Document doc1 = Jsoup.connect(link.attr("href")).get();
                    String keywords = doc1.select("meta[name=keywords]").first() == null? "": doc1.select("meta[name=keywords]").first().attr("content");  
                    System.out.println("Meta keyword : " + keywords);  
                    String description = doc1.select("meta[name=description]") == null?"":doc1.select("meta[name=description]").get(0).attr("content");  
                    System.out.println("Meta description : " + description);
                    bw.write("text : " + link.text() + "\t");
                    bw.write("link : " + link.attr("href") + "\t");
                    bw.write("keyword: " + keywords + "\t");
                    bw.write("description:" + description + "\n" );
                }
            }
        }
        bw.close();
    }

    public Elements getLinks() {
        Elements ls = new Elements();
        for(Element link : links) {
            if(link.text().length() > 10) { 
                ls.add(link);
            }
        }
        return ls;
    }
}
