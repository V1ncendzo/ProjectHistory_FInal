package scrapingdata.relation.creatingmap;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import history.entity.Character;
import history.entity.Dynasty;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class DynastyRelatedToFigure {
    public List<Dynasty> loadDataDynasty() throws IOException {
        Gson gson = new Gson();
        Reader reader = Files.newBufferedReader(Paths.get("src/main/java/json/Dynasty.json"));
        List<Dynasty> dynastyList = Arrays.asList(gson.fromJson(reader, Dynasty[].class));
        reader.close();

        return dynastyList;
    }

    public List<Character> loadDataJsonFigure() throws IOException {
        Gson gson = new Gson();
        Reader reader = Files.newBufferedReader(Paths.get("src/main/java/json/Figure.json"));
        List<Character> figureList = Arrays.asList(gson.fromJson(reader, Character[].class));
        reader.close();

        return figureList;
    }

    public Map<Integer, List<Integer>> Relating(List<Character> figureList, List<Dynasty> dynastyList) {
        Map<Integer, List<Integer>> map = new HashMap<>();
        for (Dynasty dynasty : dynastyList) {
            boolean check = false;
            List<Integer> relatedList = new ArrayList<>();
            String des = dynasty.getDescription();
            for (Character c : figureList) {
                String cName = c.getName();
                if (des == null) {
                    continue;
                }
                if (des.contains(cName)) {
                    relatedList.add(c.getId());
                }
            }
            map.put(dynasty.getId(), relatedList);
        }
        return map;
    }

    public static void main(String[] args) throws IOException {
        DynastyRelatedToFigure rl = new DynastyRelatedToFigure();
        List<Dynasty> listDynasty = rl.loadDataDynasty();
        List<Character> listChar = rl.loadDataJsonFigure();
        Map<Integer, List<Integer>> maping = rl.Relating(listChar, listDynasty);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(maping);
        try (FileWriter writer = new FileWriter("src\\main\\java\\json\\CharacterToDynasty.json")) {
            writer.write(json);
            System.out.println("JSON data has been written to the file.");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
