package scrapingdata.relation.creatingmap;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import history.entity.Character;
import history.entity.Relic;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class FigureRelateToRelic {
    public List<Relic> loadDataJsonRelic() throws IOException {
        Gson gson = new Gson();
        Reader reader = Files.newBufferedReader(Paths.get("src/main/java/json/Relic.json"));
        List<Relic> relicList = Arrays.asList(gson.fromJson(reader, Relic[].class));
        reader.close();

        return relicList;
    }

    public List<Character> loadDataJsonFigure() throws IOException {
        Gson gson = new Gson();
        Reader reader = Files.newBufferedReader(Paths.get("src/main/java/json/Figure.json"));
        List<Character> figureList = Arrays.asList(gson.fromJson(reader, Character[].class));
        reader.close();

        return figureList;
    }

    public Map<Integer, List<Integer>> Relating(List<Character> figureList, List<Relic> relicList) {
        Map<Integer, List<Integer>> map = new HashMap<>();
        for (Relic relic : relicList) {
            List<Integer> relatedList = new ArrayList<>();
            String des = relic.getDescription();
            for (Character c : figureList) {
                String cName = c.getName();
                if (des == null) {
                    continue;
                }
                if (des.contains(cName)) {
                    relatedList.add(c.getId());
                }
            }
            map.put(relic.getId(), relatedList);
        }
        return map;
    }

    public static void main(String[] args) throws IOException {
        FigureRelateToRelic rl = new FigureRelateToRelic();
        List<Relic> listRelic = rl.loadDataJsonRelic();
        List<Character> listChar = rl.loadDataJsonFigure();
        Map<Integer, List<Integer>> maping = rl.Relating(listChar, listRelic);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(maping);
        try (FileWriter writer = new FileWriter("src\\main\\java\\json\\CharacterToRelic.json")) {
            writer.write(json);
            System.out.println("JSON data has been written to the file.");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
