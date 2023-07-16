package scrapingdata.relation.creatingjson;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import history.entity.Dynasty;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class WriteToDynastyFile {
    public List<Dynasty> loadDataDynasty() throws IOException {
        Gson gson = new Gson();
        Reader reader = Files.newBufferedReader(Paths.get("src/main/java/json/Dynasty.json"));
        List<Dynasty> dynastyList = Arrays.asList(gson.fromJson(reader, Dynasty[].class));
        reader.close();

        return dynastyList;
    }

    public Map<Integer, List<Integer>> loadJsonFile(String filePath) throws IOException {
        byte[] jsonData = Files.readAllBytes(Paths.get(filePath));
        String jsonString = new String(jsonData);

        Gson gson = new Gson();
        TypeToken<Map<Integer, List<Integer>>> token = new TypeToken<Map<Integer, List<Integer>>>() {
        };
        Map<Integer, List<Integer>> data = gson.fromJson(jsonString, token.getType());

        return data;
    }

    public static void main(String[] args) throws IOException {
        WriteToDynastyFile w = new WriteToDynastyFile();
        List<Dynasty> dynastyList = w.loadDataDynasty();
        Map<Integer, List<Integer>> map = w.loadJsonFile("src/main/java/json/CharacterToDynasty.json");
        List<Dynasty> dynastyRelate = new ArrayList<>();
        for (int i = 0; i < dynastyList.size(); i++) {
            Dynasty dynasty = dynastyList.get(i);
            Dynasty dynasty1 = new Dynasty();
            dynasty1.setId(dynasty.getId());
            dynasty1.setRelate(map.get(dynasty.getId()));
            dynasty1.setKingdom(dynasty.getKingdom());
            dynasty1.setDescription(dynasty.getDescription());
            dynasty1.setCapital(dynasty.getCapital());
            dynasty1.setName(dynasty.getName());
            dynastyRelate.add(dynasty1);

        }

        File theFile = new File("src\\main\\java\\json\\DynastyRelated.json");
        FileWriter fileWriter = new FileWriter(theFile);
        Gson pretty_gs = new GsonBuilder().setPrettyPrinting().create();
        pretty_gs.toJson(dynastyRelate, fileWriter);
        fileWriter.close();
    }
}
