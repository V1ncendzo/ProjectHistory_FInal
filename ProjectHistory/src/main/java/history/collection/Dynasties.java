package history.collection;

import com.google.gson.Gson;

import history.entity.BaseEntity;
import history.entity.Dynasty;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Dynasties extends EntityCollection<Dynasty> {
    public void loadDataJson() throws IOException {
        Gson gson = new Gson();
        Reader reader = Files.newBufferedReader(Paths.get("src/main/json/DynastyRelated.json"));
        List<Dynasty> dynastyList = Arrays
                .asList(gson.fromJson(reader, Dynasty[].class));
        setData(dynastyList);
    }

    public static void main(String[] args) throws IOException {
        Dynasties dynasties = new Dynasties();
        dynasties.loadDataJson();
    }
}
