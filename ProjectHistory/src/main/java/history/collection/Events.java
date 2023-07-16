package history.collection;

import history.entity.Event;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class Events extends EntityCollection<Event> {

    public void loadDataJson() throws IOException {
        Gson gson = new Gson();
        Reader reader = Files.newBufferedReader(Paths.get("src/main/json/EventRelated.json"));
        List<Event> eventList = Arrays
                .asList(gson.fromJson(reader, Event[].class));

        setData(eventList);
    }
}
