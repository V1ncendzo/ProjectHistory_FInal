package scrapingdata.scraping.figure;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import history.entity.Character;
import history.entity.King;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FigureFiltering {
    public List<Character> loadDataJsonNKS() throws IOException {
        Gson gson = new Gson();
        Reader reader = Files.newBufferedReader(Paths.get("src/main/java/json/Figure_NKS.json"));
        List<Character> NKS = Arrays.asList(gson.fromJson(reader, Character[].class));
        reader.close();

        return NKS;
    }

    public List<Character> loadDataJsonVanSu() throws IOException {
        Gson gson = new Gson();
        Reader reader = Files.newBufferedReader(Paths.get("src/main/java/json/Figure_VanSu.json"));
        List<Character> VanSu = Arrays.asList(gson.fromJson(reader, Character[].class));
        reader.close();

        return VanSu;
    }

    public List<Character> Aggregation(List<Character> NKS, List<Character> VanSu) {
        List<Character> nv = new ArrayList<Character>();
        int numb = 0;
        int NKSnumb = 0;
        int VSnumb = 0;
        int same = 0;
        for (Character e : NKS) {
            NKSnumb++;
            boolean ktra = false;
            String name1 = e.getName();
            for (Character f : VanSu) {
                // System.out.println(f.hienthi());
                String name2 = f.getName();
                if (name1.equals(name2)) {
                    ktra = true;
                    String name = name2;
                    String sinh = e.getSinh();
                    String mat = e.getMat();
                    String time = f.getTime();
                    String depcription = e.getDescription();
                    String aotherName = f.getotherName();
                    String ngheNghiep = e.getNgheNghiep();
                    // String place = f.getPlace();
                    // List<String> era = f.getEra();
                    Character nvNew = new Character(name, time, depcription, aotherName, sinh, mat, ngheNghiep);
                    nv.add(nvNew);
                    numb++;
                    same++;
                    break;
                }
            }
            if (ktra == false) {
                nv.add(e);
                numb++;

            }
        }
        for (Character f : VanSu) {
            VSnumb++;
            boolean check = false;
            String name1 = f.getName();
            for (Character e : nv) {
                String name2 = e.getName();
                if (name1.equals(name2)) {
                    check = true;
                    break;
                }
            }
            if (check == false) {
                String name = name1;
                String sinh = f.getSinh();
                String mat = f.getMat();
                String time = f.getTime();
                String depcription = f.getDescription();
                String aotherName = f.getotherName();
                String ngheNghiep = f.getNgheNghiep();
                Character nvNew = new Character(name, time, depcription, aotherName, sinh, mat, ngheNghiep);
                nv.add(nvNew);
                numb++;

            }
        }
        System.out.println(NKSnumb); // 1200
        System.out.println(VSnumb); // 2300
        System.out.println("total : " + numb);
        System.out.println("same : " + same);
        return nv;
    }

    public static void main(String[] args) throws IOException {
        FigureFiltering dA = new FigureFiltering();
        List<Character> listNKS = dA.loadDataJsonNKS();
        List<Character> listVS = dA.loadDataJsonVanSu();
        List<Character> nv = dA.Aggregation(listNKS, listVS);

        try (Writer file = new FileWriter("src\\main\\java\\json\\Figure.json")) {
            file.write("[\n");
            for (Character e : nv) {
                if (e.getName() == null) {
                    continue;
                }
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                gson.toJson(e, file);
                file.write(",\n");
            }
            file.write("]");
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
    }
}
