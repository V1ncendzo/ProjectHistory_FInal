package scrapingdata.scraping.relic;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import history.entity.Relic;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

public class ScrapingRelic extends BaseScrapingRelic {
    public ScrapingRelic(String url) {
        super();
    }

    @Override
    public List<String> getRelicLinks(String url) {
        List<String> allRelicLinks = new ArrayList<>();
        Document relicData = null;
        try {
            relicData = Jsoup.connect(url).get();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Elements relicNames = relicData.select("p[style = margin-left:5px; margin-right:8px] a");
        for (Element relicName : relicNames) {
            String relicURL = "http://dsvh.gov.vn/" + relicName.attr("href");
            if (relicURL.contentEquals(
                    "http://dsvh.gov.vn/http://di-tich-kien-truc-nghe-thuat-chua-keo-huyen-vu-thu-tinh-thai-binh-2964")) {
                continue;
            } else {
                // System.out.println(relicURL);
                allRelicLinks.add(relicURL);
            }
        }
        return allRelicLinks;
    }

    @Override
    public void getRelicData(List<String> allRelicLinks) {
        try (Writer writer = new FileWriter("src\\main\\java\\json\\Relic.json")) {
            writer.write('[');
            Document doc = Jsoup.connect("http://dsvh.gov.vn/danh-muc-di-tich-quoc-gia-dac-biet-1752").get();
            Elements relics = doc.select("p[style = margin-left:5px; margin-right:8px] > a");
            for (String url : allRelicLinks) {
                try {
                    Document data = Jsoup.connect(url).get();
                    Elements names = data.select("div[class=page-content] h1[class=page-title]");
                    Elements description = data.select("div[class=description] ");
                    // Elements decriptions = data.select("p[style=text-align:justify]");
                    for (int i = 0; i < names.size(); i++) {
                        Relic relic = new Relic();
                        relic.setName(names.get(i).text());
                        relic.setTime("");
                        // int count = 0;
                        // for(int j=0; j<=0; j++) {
                        // if(decriptions.get(i).text() != null){
                        // count ++;
                        // if(count <= 1){
                        // relic.setDescription(decriptions.text());
                        // }
                        // }
                        // }
                        relic.setDescription(description.text());
                        for (Element element : relics) {
                            Element tdTag = element.parent().parent();
                            String relicCertifacte = tdTag.nextElementSibling().text();
                            String relicProvice = tdTag.nextElementSibling().nextElementSibling().text();
                            relic.setCertifacte(relicCertifacte);
                            relic.setProvince(relicProvice);
                        }
                        relic.setReference(url);
                        System.out.println(relic.hienthi());
                        Gson gson = new GsonBuilder().setPrettyPrinting().create();
                        gson.toJson(relic, writer);
                        writer.write(",\n");
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            writer.write(']');
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        String url = "http://dsvh.gov.vn/danh-muc-di-tich-quoc-gia-dac-biet-1752";
        ScrapingRelic diaDiem = new ScrapingRelic(url);
        List<String> allRelicLinks = diaDiem.getRelicLinks(url);
        diaDiem.getRelicData(allRelicLinks);
    }
}
