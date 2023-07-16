package history.entity;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class Event extends BaseEntity {
    public Event() {

    }

    private List<Integer> relate;
    private String location;

    public Event(String name, String time, String description, String location, List<Integer> relate) {
        super(name, time, description);
        this.location = location;
        this.relate = relate;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public List<Integer> getRelate() {
        return relate;
    }

    public void setRelate(List<Integer> relate) {
        this.relate = relate;
    }

    @Override
    public String hienthi() {
        return "Tên: " + this.getName() + "\n" + "Thời gian: " + this.getTime() + "\n" + "Triều đại: " + this.location
                + "\n" + "Chi tiết: " + this.getDescription();
    }

}
