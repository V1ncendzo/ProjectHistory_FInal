package scrapingdata.relation.creatingmap;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import history.entity.*;
import history.entity.Character;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class EntityRelateToFigure {
    public List<Character> loadDataJsonFigure() throws IOException {
        Gson gson = new Gson();
        Reader reader = Files.newBufferedReader(Paths.get("src/main/java/json/Figure.json"));
        List<Character> figureList = Arrays.asList(gson.fromJson(reader, Character[].class));
        reader.close();

        return figureList;
    }

    public List<Festival> loadDataJsonFestival() throws IOException {
        Gson gson = new Gson();
        Reader reader = Files.newBufferedReader(Paths.get("src/main/java/json/Festival_Wiki.json"));
        List<Festival> festivalList = Arrays.asList(gson.fromJson(reader, Festival[].class));
        reader.close();

        return festivalList;
    }

    public List<Dynasty> loadDataDynasty() throws IOException {
        Gson gson = new Gson();
        Reader reader = Files.newBufferedReader(Paths.get("src/main/java/json/Dynasty_Wiki.json"));
        List<Dynasty> dynastyList = Arrays.asList(gson.fromJson(reader, Dynasty[].class));
        reader.close();

        return dynastyList;
    }

    public List<Relic> loadDataJsonRelic() throws IOException {
        Gson gson = new Gson();
        Reader reader = Files.newBufferedReader(Paths.get("src/main/java/json/Relic.json"));
        List<Relic> relicList = Arrays.asList(gson.fromJson(reader, Relic[].class));
        reader.close();

        return relicList;
    }

    public List<Event> loadDataJsonEvent() throws IOException {
        Gson gson = new Gson();
        Reader reader = Files.newBufferedReader(Paths.get("src/main/java/json/Event.json"));
        List<Event> eventList = Arrays.asList(gson.fromJson(reader, Event[].class));
        reader.close();

        return eventList;
    }

    public List<King> loadDataJsonKing() throws IOException {
        Gson gson = new Gson();
        Reader reader = Files.newBufferedReader(Paths.get("src/main/java/json/King_Wiki.json"));
        List<King> kingList = Arrays.asList(gson.fromJson(reader, King[].class));
        reader.close();
        return kingList;
    }

    public Map<BaseEntity, List<BaseEntity>> Relating(List<Character> figureList, List<Festival> festivalList,
            List<Dynasty> dynastyList, List<Relic> relicList, List<Event> eventList, List<King> kingWikiList) {
        Map<BaseEntity, List<BaseEntity>> map = new HashMap<>();
        for (Character c : figureList) {
            if (c == null) {
                continue;
            }
            List<BaseEntity> relatedEntity = new ArrayList<>();
            String cName = c.getName();
            if (cName == null) {
                continue;
            } else {
                for (Festival f : festivalList) {
                    String fChar = f.getCharacter();
                    if (cName.equals(fChar)) {
                        relatedEntity.add(f);
                    }
                }
                for (Dynasty d : dynastyList) {

                    if (d.isRelated(cName)) {
                        relatedEntity.add(d);
                    }
                }
                for (Relic r : relicList) {
                    if (r.isRelated(cName)) {
                        relatedEntity.add(r);
                    }
                }
                for (Event e : eventList) {
                    if (e.isRelated(cName)) {
                        relatedEntity.add(e);
                    }
                }
                for (King k : kingWikiList) {
                    if (k.isRelated(cName)) {
                        relatedEntity.add(k);
                    } else if (k.getThanMau() != null && k.getThanMau().equals(cName)) {
                        relatedEntity.add(k);
                    } else if (k.getThanPhu() != null && k.getThanPhu().equals(cName)) {
                        relatedEntity.add(k);
                    } else if (k.getTienNhiem() != null && k.getTienNhiem().equals(cName)) {
                        relatedEntity.add(k);
                    } else if (k.getKeNhiem() != null && k.getKeNhiem().equals(cName)) {
                        relatedEntity.add(k);
                    }

                }
            }
            map.put(c, relatedEntity);
        }
        return map;
    }

    public static void main(String[] args) throws IOException {
        EntityRelateToFigure relate = new EntityRelateToFigure();
        List<Character> figureList = relate.loadDataJsonFigure();
        List<Festival> festivalList = relate.loadDataJsonFestival();
        List<Dynasty> dynastyList = relate.loadDataDynasty();
        List<Relic> relicList = relate.loadDataJsonRelic();
        List<Event> eventList = relate.loadDataJsonEvent();
        List<King> kingWikiList = relate.loadDataJsonKing();
        Map<BaseEntity, List<BaseEntity>> maping = relate.Relating(figureList, festivalList, dynastyList, relicList,
                eventList, kingWikiList);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(maping);
        try (FileWriter writer = new FileWriter("src\\main\\java\\json\\Relating.json")) {
            writer.write(json);
            System.out.println("JSON data has been written to the file.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}