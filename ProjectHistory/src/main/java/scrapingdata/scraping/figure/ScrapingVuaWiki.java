package scrapingdata.scraping.figure;

import history.entity.King;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

public class ScrapingVuaWiki extends BaseScrapingFigure {
    public ScrapingVuaWiki(String url) {
        super(url);
    }

    @Override
    public List<String> getFigureLinks(String url) {
        Document Doc = null;
        try {
            Doc = Jsoup.connect(url).get();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Elements table = Doc.select("table[cellpadding = 0] tbody");
        List<String> figureLinks = new ArrayList<>();
        for (Element i : table) {
            Elements row = i.select("tr[style *= height:50px;]");
            for (Element subRwo : row) {
                Element name = subRwo.select("td").get(1);
                Element y = name.getElementsByTag("a").get(0);
                figureLinks.add(y.attr("href"));
            }
        }
        return figureLinks;
    }

    @Override
    public void getFigureData(List<String> figureLinks) {
        try (Writer file = new FileWriter("src\\main\\java\\json\\King_Wiki.json")) {
            file.write("[\n");
            for (int i = 0; i < figureLinks.size(); i++) {
                String name = null;
                String depcription = "";
                String triVi = null;
                String tienNhiem = null;
                String keNhiem = null;
                String sinh = null;
                String mat = null;
                String trieuDai = null;
                String anTang = null;
                String thanPhu = null;
                String thanMau = null;
                String link = ("https://vi.wikipedia.org" + figureLinks.get(i));
                Document data = null;
                try {
                    data = Jsoup.connect(link).get();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                Elements infobox = data.getElementsByClass("infobox").select("[style=width:22em]"); // select table info
                                                                                                    // in page
                Elements pTags = data.select("div[class = mw-parser-output] > p");
                if (pTags.size() > 0)
                    depcription += pTags.get(0).text();
                for (Element in4 : infobox) {
                    King kingInfo = new King();
                    name = in4.select("th").get(0).text();
                    kingInfo.setName(name);
                    Elements infomation = in4.select("tr");
                    for (Element subInfo : infomation.select("[scope=row]")) {
                        if (subInfo.text().equals("Trị vì") || subInfo.text().equals("Tại vị")
                                || subInfo.text().equals("Lãnh đạo")) {
                            triVi = subInfo.parent().select("td").text(); // .parent() to back to "tr", then select
                                                                          // child of "tr" : td
                            continue;
                        } else if (subInfo.text().equals("Tiền nhiệm")) {
                            tienNhiem = subInfo.parent().select("td").text();
                            continue;
                        } else if (subInfo.text().equals("Kế nhiệm")) {
                            keNhiem = subInfo.parent().select("td").text();
                            continue;
                        } else if (subInfo.text().equals("Sinh")) {
                            sinh = subInfo.parent().select("td").text();
                            continue;
                        } else if (subInfo.text().equals("Mất")) {
                            mat = subInfo.parent().select("td").text();
                            continue;
                        } else if (subInfo.text().equals("An táng")) {
                            anTang = subInfo.parent().select("td").text();
                            continue;
                        } else if (subInfo.text().equals("Thời kỳ") || subInfo.text().equals("Triều đại")) {
                            trieuDai = subInfo.parent().select("td").text();
                            continue;
                        } else if (subInfo.text().equals("Thân phụ")) {
                            thanPhu = subInfo.parent().select("td").text();
                            continue;
                        } else if (subInfo.text().equals("Thân mẫu")) {
                            thanMau = subInfo.parent().select("td").text();
                            continue;
                        }
                    }
                    kingInfo.setTriVi(triVi);
                    kingInfo.setTienNhiem(tienNhiem);
                    kingInfo.setKeNhiem(keNhiem);
                    kingInfo.setSinh(sinh);
                    kingInfo.setMat(mat);
                    kingInfo.setAntang(anTang);
                    kingInfo.setTrieuDai(trieuDai);
                    kingInfo.setThanPhu(thanPhu);
                    kingInfo.setThanMau(thanMau);
                    kingInfo.setDescription(depcription);
                    Gson gson = new GsonBuilder().setPrettyPrinting().create();
                    gson.toJson(kingInfo, file);
                    file.write(",\n");
                    System.out.println(gson.toJson(kingInfo));
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public static void main(String[] args) {
        String url = "https://vi.wikipedia.org/wiki/Vua_Vi%E1%BB%87t_Nam";
        ScrapingVuaWiki kingCrawler = new ScrapingVuaWiki(url);

        List<String> allKingLinks = kingCrawler.getFigureLinks(url);
        System.out.println(allKingLinks.size()); // 166
        kingCrawler.getFigureData(allKingLinks);
    }
}
