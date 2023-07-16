package scrapingdata.relation.creatingjson;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
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
import java.util.Map;

public class WriteToEvetfile {
    public List<Event> loadDataEvent() throws IOException {
        Gson gson = new Gson();
        Reader reader = Files.newBufferedReader(Paths.get("src/main/java/json/Event.json"));
        List<Event> eventList = Arrays.asList(gson.fromJson(reader, Event[].class));
        reader.close();

        return eventList;
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
        WriteToEvetfile w = new WriteToEvetfile();
        List<Event> eventList = w.loadDataEvent();
        Map<Integer, List<Integer>> map = w.loadJsonFile("src/main/java/json/CharacterToEvent.json");
        List<Event> eventRelate = new ArrayList<>();
        for (int i = 0; i < eventList.size(); i++) {
            Event event1 = eventList.get(i);
            Event event = new Event();
            event.setLocation(event1.getLocation());
            event.setRelate(map.get(i));
            event.setId(event1.getId());
            event.setName(event1.getName());
            event.setDescription(event1.getDescription());
            eventRelate.add(event);
        }
        File theFile = new File("src\\main\\java\\json\\EventRelated.json");
        FileWriter fileWriter = new FileWriter(theFile);
        Gson pretty_gs = new GsonBuilder().setPrettyPrinting().create();
        pretty_gs.toJson(eventList, fileWriter);
        fileWriter.close();
    }
}
