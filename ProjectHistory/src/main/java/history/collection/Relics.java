package history.collection;

import com.google.gson.Gson;

import history.entity.Relic;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class Relics extends EntityCollection<Relic> {
    public void loadDataJson() throws IOException {
        Gson gson = new Gson();
        Reader reader = Files.newBufferedReader(Paths.get("src/main/json/RelicRelated.json"));
        List<Relic> relics = Arrays
                .asList(gson.fromJson(reader, Relic[].class));
        setData(relics);
    }
}
