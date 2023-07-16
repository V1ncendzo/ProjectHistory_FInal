package scrapingdata.scraping.dynasty;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import history.entity.Dynasty;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ScrapingWikiDynasty extends BaseScrapingDynasty {

    public ScrapingWikiDynasty(String url) {
        super(url);
    }

    public ScrapingWikiDynasty() {
    }

    @Override
    public void getData() {
        File theFile = new File("src\\main\\java\\json\\Dynasty_Wiki.json");
        String url = "https://vi.wikipedia.org/wiki/L%E1%BB%8Bch_s%E1%BB%AD_Vi%E1%BB%87t_Nam";

        String capital = "Thủ đô";
        String kingdom = "Chính phủ";
        List<String> linkTrieuDai = new ArrayList<>();

        Document doc = null;
        try {
            doc = Jsoup.connect(url).get();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Elements table = doc.select("table[align*=right]");
        for (Element i : table.select("a"))
            linkTrieuDai.add(i.attr("href"));
        linkTrieuDai.remove(0);

        for (int l = 1; l < linkTrieuDai.size(); l++) {
            if (l == 0 || l == linkTrieuDai.size() - 1) {

            } else {
                Dynasty dk1 = new Dynasty();
                String link = ("https://vi.wikipedia.org" + linkTrieuDai.get(l));
                Connection webConnection = Jsoup.connect(link);
                Document data;
                try {
                    data = webConnection.get();

                    String title = data.getElementsByClass("mw-page-title-main").text();
                    dk1.setName(title);
                    Elements description = data.select("div[class = mw-parser-output]").select(">p");
                    String des = "";
                    int count = 0;
                    for (Element d : description) {
                        if (!d.text().equals("")) {
                            if (count > 1) {
                                break;
                            }
                            count++;
                            des += d.text() + "\n";
                            System.out.println(d.text());
                        }
                    }
                    dk1.setDescription(des);

                    Elements info = data.getElementsByClass("infobox").select("[style*=width:22em]");
                    for (Element infoElement : info.select("[scope=row]")) {
                        if (infoElement.text().equals(capital)) {
                            dk1.setCapital(infoElement.parent().select("td").text());
                        } else if (infoElement.text().equals(kingdom))
                            dk1.setKingdom(infoElement.parent().select("td").text());
                    }
                    dynastyList.add(dk1);
                    System.out.println(dk1.hienthi());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        try {
            FileWriter fileWriter = new FileWriter(theFile);
            Gson pretty_gs = new GsonBuilder().setPrettyPrinting().create();
            pretty_gs.toJson(dynastyList, fileWriter);
            fileWriter.close();

        } catch (IOException e) {
            System.err.println("Error in writing a file.");
        }
    }

    public static void main(String[] args) {
        String url = "https://vi.wikipedia.org/wiki/L%E1%BB%8Bch_s%E1%BB%AD_Vi%E1%BB%87t_Nam";
        ScrapingWikiDynasty wikidc = new ScrapingWikiDynasty(url);
        wikidc.getData();
    }
}