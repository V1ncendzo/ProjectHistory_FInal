package history.collection;

import com.google.gson.Gson;
import history.entity.King;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class Kings extends EntityCollection<King> {
    public void loadDataJson() throws IOException {
        Gson gson = new Gson();
        Reader reader = Files.newBufferedReader(Paths.get("src/main/json/King_Wiki.json"));
        List<King> kingWikiList = Arrays.asList(gson.fromJson(reader, King[].class));
        reader.close();

        setData(kingWikiList);
    }
}
