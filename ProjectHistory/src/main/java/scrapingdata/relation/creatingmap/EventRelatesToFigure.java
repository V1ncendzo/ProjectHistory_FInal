package scrapingdata.relation.creatingmap;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import history.entity.Character;
import history.entity.Event;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class EventRelatesToFigure {
    public List<Event> loadDataEvent() throws IOException {
        Gson gson = new Gson();
        Reader reader = Files.newBufferedReader(Paths.get("src/main/java/json/Event.json"));
        List<Event> eventList = Arrays.asList(gson.fromJson(reader, Event[].class));
        reader.close();

        return eventList;
    }

    public List<Character> loadDataJsonFigure() throws IOException {
        Gson gson = new Gson();
        Reader reader = Files.newBufferedReader(Paths.get("src/main/java/json/Figure.json"));
        List<Character> figureList = Arrays.asList(gson.fromJson(reader, Character[].class));
        reader.close();

        return figureList;
    }

    public Map<Integer, List<Integer>> Relating(List<Character> figureList, List<Event> eventList) {
        Map<Integer, List<Integer>> map = new HashMap<>();
        for (Event ev : eventList) {
            boolean check = false;
            List<Integer> relatedList = new ArrayList<>();
            String des = ev.getDescription();
            for (Character c : figureList) {
                String cName = c.getName();
                if (des == null) {
                    continue;
                }
                if (des.contains(cName)) {
                    relatedList.add(c.getId());
                }
            }
            map.put(ev.getId(), relatedList);
        }
        return map;
    }

    public static void main(String[] args) throws IOException {
        EventRelatesToFigure rl = new EventRelatesToFigure();
        List<Event> listDynasty = rl.loadDataEvent();
        List<Character> listChar = rl.loadDataJsonFigure();
        Map<Integer, List<Integer>> maping = rl.Relating(listChar, listDynasty);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(maping);
        try (FileWriter writer = new FileWriter("src\\main\\java\\json\\CharacterToEvent.json")) {
            writer.write(json);
            System.out.println("JSON data has been written to the file.");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
