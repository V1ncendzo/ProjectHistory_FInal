package scrapingdata.scraping.figure;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import history.entity.Character;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

public class ScrapingVanSu extends BaseScrapingFigure {
    public ScrapingVanSu(String url) {
        super();
    }

    @Override
    public List<String> getFigureLinks(String url) { // 120 pages
        List<String> FigureLinks = new ArrayList<String>();
        for (int i = 0; i <= 120; i++) {
            Document vanSu = null;
            try {
                vanSu = Jsoup.connect(url + "?page=" + i).get();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Element table = vanSu.selectFirst("table[class = ui selectable celled table]");
            Elements rows = table.select("tbody tr");
            for (Element row : rows) {
                Element characterName = row.selectFirst("td > a");
                String charaterLink = "https://vansu.vn" + characterName.attr("href");
                FigureLinks.add(charaterLink);
                System.out.println(charaterLink);
            }
        }
        return FigureLinks;
    }

    @Override
    public void getFigureData(List<String> allFigureLinks) {
        try (Writer writer = new FileWriter("src\\main\\java\\json\\Figure_VanSu.json")) {
            writer.write('[');
            for (String link : allFigureLinks) {
                Character nhanVat = new Character();
                Document figureInfo = null;
                try {
                    figureInfo = Jsoup.connect(link).get();

                    // Attribute
                    String name = figureInfo.select("div[class = active section]").text();
                    String description = "";
                    String time = null;
                    String otherName = null;

                    Elements table = figureInfo.select("table tbody");
                    Elements rows = table.select("tr");
                    for (Element row : rows) {
                        Elements column = row.select("td");
                        if (column.size() == 2) {
                            String key = column.get(0).text();
                            String value = column.get(1).text();
                            if (key.equals("Tên khác")) {
                                otherName = value;
                            }
                            if (key.equals("Thời kì")) {
                                time = value;
                            }

                        } else if (column.size() == 1) {

                            description += column.select("> p").get(0);
                        }
                    }
                    nhanVat.setTime(time);
                    nhanVat.setName(name);
                    nhanVat.setDescription(description);
                    nhanVat.setotherName(otherName);
                    nhanVat.setSinh("Không rõ");
                    nhanVat.setMat("Không rõ");
                    nhanVat.setNgheNghiep("Không rõ");

                    Gson gson = new GsonBuilder().setPrettyPrinting().create();
                    gson.toJson(nhanVat, writer);
                    writer.write(",\n");
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
        String url = "https://vansu.vn/viet-nam/viet-nam-nhan-vat";
        ScrapingVanSu test = new ScrapingVanSu(url);
        List<String> allFigureLinks = test.getFigureLinks(url);
        test.getFigureData(allFigureLinks);
        // System.out.println(allFigureLinks.size()); //2391
    }
}
