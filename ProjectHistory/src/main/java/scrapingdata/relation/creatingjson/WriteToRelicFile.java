package scrapingdata.relation.creatingjson;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import history.entity.Relic;

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

public class WriteToRelicFile {
    public List<Relic> loadDataRelic() throws IOException {
        Gson gson = new Gson();
        Reader reader = Files.newBufferedReader(Paths.get("src/main/java/json/Relic.json"));
        List<Relic> relicList = Arrays.asList(gson.fromJson(reader, Relic[].class));
        reader.close();

        return relicList;
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
        WriteToRelicFile w = new WriteToRelicFile();
        List<Relic> relicList = w.loadDataRelic();
        Map<Integer, List<Integer>> map = w.loadJsonFile("src/main/java/json/CharacterToRelic.json");
        System.out.println(map);
        List<Relic> relicsRelate = new ArrayList<>();
        for (int i = 0; i < relicList.size(); i++) {
            Relic relic1 = relicList.get(i);
            Relic relic = new Relic();
            relic.setDescription(relic1.getDescription());
            relic.setCertifacte(relic1.getCertifacte());
            relic.setRelate(map.get(i + 1));
            relic.setProvince(relic1.getProvince());
            relic.setReference(relic1.getReference());
            relic.setId(relic1.getId());
            relic.setName(relic1.getName());
            relic.setTime(relic1.getTime());
            relicsRelate.add(relic);

        }

        File theFile = new File("src\\main\\java\\json\\RelicRelated.json");
        FileWriter fileWriter = new FileWriter(theFile);
        Gson pretty_gs = new GsonBuilder().setPrettyPrinting().create();
        pretty_gs.toJson(relicsRelate, fileWriter);
        fileWriter.close();
    }
}
