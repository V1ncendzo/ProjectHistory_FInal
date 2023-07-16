package scrapingdata.scraping.dynasty;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import history.entity.Character;
import history.entity.Dynasty;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DynastyFiltering {
    public List<Dynasty> loadDataJsonDynastyNKS() throws IOException {
        Gson gson = new Gson();
        Reader reader = Files.newBufferedReader(Paths.get("src/main/java/json/Dynasty_NKS.json"));
        List<Dynasty> NKS = Arrays.asList(gson.fromJson(reader, Dynasty[].class));
        reader.close();

        return NKS;
    }

    public List<Dynasty> loadDataJsonDynastyWiki() throws IOException {
        Gson gson = new Gson();
        Reader reader = Files.newBufferedReader(Paths.get("src/main/java/json/Dynasty_Wiki.json"));
        List<Dynasty> wiki = Arrays.asList(gson.fromJson(reader, Dynasty[].class));
        reader.close();

        return wiki;
    }

    public List<Dynasty> Merging(List<Dynasty> NKS, List<Dynasty> Wiki) {
        List<Dynasty> merge = new ArrayList<>();
        System.out.println(NKS.size());
        System.out.println(Wiki.size());
        int numb = 0;
        int numb2 = 0;
        int same = 0;
        int wikiNumb = 0;
        int NKSnumb = 0;
        for (Dynasty dNKS : NKS) {
            NKSnumb++;
            boolean check = false;
            String nameNKS = dNKS.getName();
            for (Dynasty dWiki : Wiki) {
                String nameWiki = dWiki.getName();
                if (nameNKS.equals(nameWiki)) {
                    System.out.println("name1 : " + nameNKS);
                    System.out.println("name2 : " + nameWiki);
                    check = true;
                    String name = nameWiki;
                    String kingdom = dWiki.getKingdom();
                    String capital = dWiki.getCapital();
                    String description = null;
                    String descriptionNKS = dNKS.getDescription();
                    String descriptionWiki = dWiki.getDescription();
                    if (descriptionNKS.equals("") && descriptionWiki.equals("")) {
                        description = "không rõ";
                    } else if (descriptionWiki.equals("") && descriptionNKS.equals("")) {
                        description = descriptionNKS;
                    } else if (descriptionNKS.equals("") && descriptionWiki.equals("")) {
                        description = descriptionWiki;
                    }
                    Dynasty newDynasty = new Dynasty(name, kingdom, capital, description);
                    merge.add(newDynasty);
                    numb++;
                    same++;
                    break;
                }
            }
            if (check == false) {
                String name = nameNKS;
                System.out.println(name);
                String kingdom = dNKS.getKingdom();
                String capital = dNKS.getCapital();
                String description = dNKS.getDescription();
                Dynasty newDynasty = new Dynasty(name, kingdom, capital, description);
                merge.add(newDynasty);
                numb++;
            }
        }
        for (Dynasty dWiki : Wiki) {
            wikiNumb++;
            boolean check = false;
            String nameWiki = dWiki.getName();
            for (Dynasty dNKS : NKS) {
                String nameNKS = dNKS.getName();
                if (nameWiki.equals(nameNKS)) {
                    check = true;
                    break;
                }
            }
            if (check == false) {
                String name = nameWiki;
                System.out.println(name);
                String kingdom = dWiki.getKingdom();
                String capital = dWiki.getCapital();
                String description = dWiki.getDescription();
                Dynasty newDynasty = new Dynasty(name, kingdom, capital, description);
                merge.add(newDynasty);
                numb2++;
            }
        }
        System.out.println(wikiNumb);
        System.out.println(NKSnumb);
        System.out.println(numb);
        System.out.println(numb2);
        System.out.println(same);
        return merge;
    }

    public static void main(String[] args) throws IOException {
        DynastyFiltering dF = new DynastyFiltering();
        List<Dynasty> dNKS = dF.loadDataJsonDynastyNKS();
        List<Dynasty> dWiki = dF.loadDataJsonDynastyWiki();
        List<Dynasty> merge = dF.Merging(dNKS, dWiki);
        try (Writer file = new FileWriter("src\\main\\java\\json\\Dynasty.json")) {
            file.write("[\n");
            for (Dynasty d : merge) {
                if (d.getName() == null) {
                    continue;
                }
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                gson.toJson(d, file);
                file.write(",\n");
            }
            file.write("]");
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
    }
}