package history.collection;

import com.google.gson.Gson;
import history.entity.Character;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class Characters extends EntityCollection<Character> {
    public void loadDataJson() throws IOException {
        Gson gson = new Gson();
        Reader reader = Files.newBufferedReader(Paths.get("src/main/json/Figure.json"));
        List<Character> characterList = Arrays.asList(gson.fromJson(reader, Character[].class));
        setData(characterList);
    }

    public static void main(String[] args) throws IOException {
        Characters characters = new Characters();
        characters.loadDataJson();
        List<Character> characterList = characters.getData();
        for (Character character : characterList) {
            System.out.println(character);
        }
    }
}
