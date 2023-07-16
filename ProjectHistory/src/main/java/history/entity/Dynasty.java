
package history.entity;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Dynasty extends BaseEntity {

    private String kingdom;
    private String capital;
    private List<Integer> relate;
    // private ArrayList<String> king;

    public Dynasty(String name, String kingdom, String capital, String description) {
    }

    public Dynasty() {

    }

    public String getKingdom() {
        return kingdom;
    }

    public void setKingdom(String kingdom) {
        this.kingdom = kingdom;
    }

    public String getCapital() {
        return capital;
    }

    public void setCapital(String capital) {
        this.capital = capital;
    }

    public List<Integer> getRelate() {
        return relate;
    }

    public void setRelate(List<Integer> relate) {
        this.relate = relate;
    }
    // public ArrayList<String> getKing() {
    // return king;
    // }
    // public void setKing(ArrayList<String> king) {
    // this.king = king;
    // }

    @Override
    public String hienthi() {
        return "Tên: "
                + this.getName() + "\n" + "Thời gian: " + this.getTime() + "\n" + "Tên nước: " + this.getKingdom()
                + "\n" + "Thủ đô: " + this.getCapital() + "\n" + "các đời vua: " + /* this.getKing().toString() */
                "\n" + "Chi tiết: " + this.getDescription();
    }

}
