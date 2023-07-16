package scrapingdata.scraping.event;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import history.entity.Character;
import history.entity.Event;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ScrapingWikiEvent extends ScrapingEvent {
    public ScrapingWikiEvent(String url) {
        super();
    }

    private static List<String> eventLinks = new ArrayList<>();

    public ScrapingWikiEvent() {
    }

    @Override
    public void getAllEventLinks() throws IOException {
        String baseUrl = "https://nguoikesu.com/tu-lieu/quan-su/";
        Document doc = Jsoup.connect("https://nguoikesu.com/tu-lieu/quan-su?filter_tag[0]=").timeout(120000).get();
        Element pTag = doc
                .selectFirst("p[class=com-content-category-blog__counter counter float-end pt-3 pe-2]");
        String[] pTagContentArray = pTag.text().split(" ");
        int pTagContentArrSize = pTagContentArray.length;
        int numberOfPagination = Integer.parseInt(pTagContentArray[pTagContentArrSize - 1]);


        for(int i = 0; i < numberOfPagination; i++){
            Document doc2 = Jsoup.connect("https://nguoikesu.com/tu-lieu/quan-su?start=" + i*5).timeout(120000).get();
            Elements pageHeaders = doc2.select("div[class = page-header]").select("a");
            for (Element e : pageHeaders){
                String eventUrl = baseUrl + e.attr("href");
                eventLinks.add(eventUrl);
            }
        }
    }

    @Override
    public void getEventData(List<String> URLs) throws IOException {

        List<Event> eventList = new ArrayList<>();
        File theFile = new File("src\\main\\java\\json\\event_Wiki.json");
        for (String url : URLs) {
            try {
                Document doc = Jsoup.connect(url).timeout(120000).get();

                String name = "Chưa rõ"; // Ten su kien
                String time = "Chưa rõ"; // Thoi gian dien ra su kien
                String location = "Chưa rõ"; // Dia diem dien ra su kien
                String reason = "Chưa rõ"; // Nguyen nhan dien ra su kien
                String description = "Chưa rõ"; // Mo ta ngan gon ve tran chien
                String result = "Chưa rõ"; // Ket qua cua su kien
                ArrayList<String> relatedChar = new ArrayList<>();

                // Lay ten cua su kien
                // Lay tu the div dau tien truoc
                Element firstPageHeader = doc.selectFirst("div[class=page-header]");
                if (firstPageHeader != null) {
                    firstPageHeader.select("sup").remove();
                    if (name.equals("Chưa rõ"))
                        name = firstPageHeader.text();
                }


                Element infoTable = doc.selectFirst("table[cellpadding=0]");

                if (infoTable != null) {
                    Elements infoTableRows = infoTable.select("tr");

                    for (Element tr : infoTableRows) {

                        Elements tableDatas = tr.select("td");


                        if (tableDatas != null) {
                            if (tableDatas.size() > 1) {
                                tableDatas.get(0).select("sup").remove();
                                tableDatas.get(1).select("sup").remove();   // loai bo cac chi so tren dau
                                String title = tableDatas.get(0).text();
                                // System.out.println(title);
                                if (title.equals("Thời gian") && time.equals("Chưa rõ")) {
                                    time = tableDatas.get(1).text();
                                } else if (title.equals("Địa điểm") && location.equals("Chưa rõ")) {
                                    location = tableDatas.get(1).text();
                                } else if (title.equals("Kết quả") && result.equals("Chưa rõ")) {
                                    result = tableDatas.get(1).text();
                                } else if (title.contains("Nguyên nhân") && reason.equals("Chưa rõ")) {
                                    reason = tableDatas.get(1).text();
                                }
                            }
                        }
                    }
                }

                // Loc tu text
                Element contentBody = doc.selectFirst("div[class=com-content-article__body]");
                Elements contentBodyELements = contentBody.children();
                boolean firstPFound = false;
                // Loc tu doan p dau tien, co the thieu thong tin
                for (Element item : contentBodyELements) {
                    if (item.tagName().equals("p")) {
                        if (!firstPFound) {
                            firstPFound = true;
                            Element firstParagraph = item;
                            firstParagraph.select("sup").remove(); // [class~=(annotation).*]
                            String firstPContent = firstParagraph.text();

                            // The b dau tien la ten cua su kien, co the gom ca thoi gian
                            Element firstBTag = firstParagraph.selectFirst("b");
                            if (firstBTag != null) {
                                String firstBTagContent = firstBTag.text();
                                String[] splitArray = firstBTagContent.split(",");
                                if (splitArray.length == 1) {
                                    if (name.equals("Chưa rõ"))
                                        name = splitArray[0].trim();
                                } else if (splitArray.length > 1) {
                                    if (name.equals("Chưa rõ"))
                                        name = splitArray[0].trim();
                                    if (time.equals("Chưa rõ"))
                                        time = splitArray[1].trim();
                                }
                            }

                            // Loc ra mo ta ngan gon cua tran chien
                            Pattern p = Pattern.compile("là[^.]*[.]");
                            Matcher m = p.matcher(firstPContent);

                            if (m.find()) {
                                String findResult = m.group(0);
                                if (description.equals("Chưa rõ")) {
                                    description = findResult.substring(0, findResult.length() - 1);
                                }
                            }

                            // Loc ra ket qua cua su kien
                            p = Pattern.compile("(Kết quả|cuối cùng)[^.]*[.]", Pattern.CASE_INSENSITIVE);
                            m = p.matcher(firstPContent);

                            if (m.find()) {
                                String findResult = m.group(0);
                                if (result.equals("Chưa rõ")) {
                                    result = findResult.substring(0, findResult.length() - 1);
                                }
                            }

                            // Loc ra thoi gian cua su kien
                            p = Pattern.compile("(xảy ra|diễn ra) (từ|vào)[^.]*[.]", Pattern.CASE_INSENSITIVE);
                            m = p.matcher(firstPContent);

                            if (m.find()) {
                                String findResult = m.group(0);
                                if (time.equals("Chưa rõ")) {
                                    time = findResult.substring(0, findResult.length() - 1);
                                }
                            }

                            // Loc ra nguyen nhan cua tran chien
                            p = Pattern.compile("(nhằm|bắt nguồn|do)[^.]*[.]", Pattern.CASE_INSENSITIVE);
                            m = p.matcher(firstPContent);

                            if (m.find()) {
                                String findResult = m.group(0);
                                if (reason.equals("Chưa rõ")) {
                                    reason = findResult.substring(0, findResult.length() - 1);
                                }
                            }
                        }

                        // Lay ra cac thẻ a tim duoc trong p
                        Elements pATags = item.select("a");
                        for (Element a : pATags) {
                            String hrefValue = a.attr("href");
                            if (hrefValue.contains("/nhan-vat") && !hrefValue.contains("nha-")) {
                                String aTagValue = a.text();
                                if (!relatedChar.contains(aTagValue)) {
                                    relatedChar.add(aTagValue);
                                }
                            }
                        }
                    }
                }

                System.out.println("Name: " + name);
                System.out.println("Date: " + time);
                System.out.println("Location: " + location);
                System.out.println("Result: " + result);
                System.out.println("Reason: " + reason);
                System.out.println("Description: " + description);
                System.out.print("Related characters: [");
                if (relatedChar.size() > 0) {
                    for (int i = 0; i < relatedChar.size(); ++i) {
                        if (i < relatedChar.size() - 1) {
                            System.out.print(relatedChar.get(i) + ", ");
                        } else
                            System.out.print(relatedChar.get(i) + "]\n");
                    }
                } else {
                    System.out.print("]\n");
                }

                Event ev = new Event();
                ev.setName(name);
                ev.setTime(time);
                ev.setLocation(location);
                ev.setDescription(description);

                eventList.add(ev);
                System.out.println(ev.hienthi());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        try {
            FileWriter fileWriter = new FileWriter(theFile);
            Gson pretty_gs = new GsonBuilder().setPrettyPrinting().create();
            pretty_gs.toJson(eventList, fileWriter);
            fileWriter.close();
        } catch (IOException e) {
            System.out.println("Error");
        }
    }

    public static void main(String[] args) throws IOException {
        ScrapingEvent event = new ScrapingWikiEvent();
        event.getAllEventLinks();
        event.getEventData(eventLinks);

    }
}
