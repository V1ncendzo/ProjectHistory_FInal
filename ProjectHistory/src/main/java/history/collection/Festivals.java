package history.collection;

import com.google.gson.Gson;

import history.entity.Festival;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class Festivals extends EntityCollection<Festival> {
    public void loadDataJson() throws IOException {
        Gson gson = new Gson();
        Reader reader = Files.newBufferedReader(Paths.get("src/main/json/Festival_Wiki.json"));
        List<Festival> festivalList = Arrays
                .asList(gson.fromJson(reader, Festival[].class)); // dks is arraylist of festival
        // reader.close();

        setData(festivalList);
    }
}
