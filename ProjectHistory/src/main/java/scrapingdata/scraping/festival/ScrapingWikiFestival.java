package scrapingdata.scraping.festival;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import history.entity.Festival;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class ScrapingWikiFestival extends BaseScrapingFestival {
    public ScrapingWikiFestival(String url) {
        super(url);
    }

    public ScrapingWikiFestival() {
    }

    @Override
    public void getData() {
        Festival[] festival = new Festival[300];
        ArrayList<String> listName = new ArrayList<String>();
        ArrayList<String> listTime = new ArrayList<String>();
        ArrayList<String> listFirstHeld = new ArrayList<String>();
        ArrayList<String> listCharacter = new ArrayList<String>();
        ArrayList<String> listPlace = new ArrayList<String>();

        String url = "https://vi.wikipedia.org/wiki/L%E1%BB%85_h%E1%BB%99i_Vi%E1%BB%87t_Nam";
        Document doc = null;
        try {
            doc = Jsoup.connect(url).get();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Elements elements = doc.select("table.prettytable > tbody > tr ");
        for (Element row : elements) {
            String timeFes = row.select("td:nth-of-type(1)").text();
            if (timeFes.length() == 0) {
                timeFes = null;
            }
            listTime.add(timeFes);

            String placeFes = row.select("td:nth-of-type(2)").text();
            if (placeFes.length() == 0) {
                placeFes = null;
            }
            listPlace.add(placeFes);

            String nameFes = row.select("td:nth-of-type(3)").text();
            if (nameFes.length() == 0) {
                nameFes = null;
            }
            listName.add(nameFes);

            String firstHeld = row.select("td:nth-of-type(4)").text();
            if (firstHeld.length() == 0) {
                firstHeld = null;
            }
            listFirstHeld.add(firstHeld);

            String character = row.select("td:nth-of-type(5)").text();
            if (character.length() == 0) {
                character = null;
            }
            listCharacter.add(character);

        }

        for (int i = 1; i < listTime.size(); i++) {
            festival[i] = new Festival();
            festival[i].setTime(listTime.get(i));
            festival[i].setName(listName.get(i));
            festival[i].setPlace(listPlace.get(i));
            festival[i].setFirstTime(listFirstHeld.get(i));
            festival[i].setCharacter(listCharacter.get(i));

        }

        try (FileWriter writer = new FileWriter("src\\main\\java\\json\\festival_Wiki.json", true)) {
            writer.write("[");
            for (int i = 1; i < listTime.size(); i++) {
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                gson.toJson(festival[i], writer);
                writer.write(",\n");
            }
            writer.write("]");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        String url = "https://vi.wikipedia.org/wiki/L%E1%BB%85_h%E1%BB%99i_Vi%E1%BB%87t_Nam";
        ScrapingWikiFestival festivalCrawler = new ScrapingWikiFestival(url);
        festivalCrawler.getData();
    }
}
