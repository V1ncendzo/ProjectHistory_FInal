package scrapingdata.scraping.dynasty;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import history.entity.Dynasty;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class ScrapingNKSDynasty extends BaseScrapingDynasty {
    public ScrapingNKSDynasty(String url) {
        super(url);
    }

    @Override
    public void getData() {
        File theFile = new File("src\\main\\java\\json\\Dynasty_NKS.json");

        try {
            String url = "https://nguoikesu.com/dong-lich-su";
            Document doc = Jsoup.connect(url).get();
            Elements names = doc.select("h3[class = item-title]");
            Elements descriptions = doc.select("ul[class = issues] li div[class = inner] div");
            for (int i = 0; i < names.size(); i++) {
                Dynasty dk = new Dynasty();
                dk.setName(names.get(i).text());
                dk.setDescription(descriptions.get(i).text());
                dynastyList.add(dk);
            }

            try {
                FileWriter fileWriter = new FileWriter(theFile);
                Gson pretty_gs = new GsonBuilder().setPrettyPrinting().create();
                pretty_gs.toJson(dynastyList, fileWriter);
                fileWriter.close();

            } catch (IOException e) {
                System.err.println("Error in writing a file.");
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public static void main(String[] args) {
        String url = "https://nguoikesu.com/dong-lich-su";
        ScrapingNKSDynasty dynasty = new ScrapingNKSDynasty(url);
        dynasty.getData();
    }
}
