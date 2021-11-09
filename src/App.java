import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class App {
    public static void main(String[] args) throws IOException {
        LinkedList<String> link = new LinkedList<String>();
        HashMap<String, Integer> map = new HashMap<String, Integer>();
        link.add("http://www.39.net/");
        String regex = "https?://(\\w|-)+(\\.(\\w|-)+)+(/(\\w+(\\?(\\w+=(\\w|%|-)*(\\&\\w+=(\\w|%|-)*)*)?)?)?)+";
        Pattern p  = Pattern.compile(regex);
        int count = 0;
        while(link.size() > 0 && count < 100) {
            String url = link.poll();
            Matcher m = p.matcher(url);
            if(m.matches()){
                if( map.get(url) == null){
                    Fetch f = new Fetch(url, count);
                    count++;
                    f.get();
                    Elements links = f.getLinks();
                    for (Element l : links) {
                        link.add(l.attr("href"));
                    }
                }
                else{
                    map.put(url, 1);
                }
            }
            // System.out.println(links);
        }
       
    }
}
