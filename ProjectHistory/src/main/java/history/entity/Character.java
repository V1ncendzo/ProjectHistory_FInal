package history.entity;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class Character extends BaseEntity {
    private String otherName;
    private String place;
    private String sinh;
    private String mat;
    private String ngheNghiep;

    public Character() {
        super();
    }

    public Character(String otherName) {
        this.otherName = otherName;
    }

    public Character(String name, String time, String depcription, String aotherName, String sinh, String mat,
            String ngheNghiep) {
        super();
        this.otherName = null;
        this.place = null;
        this.sinh = sinh;
        this.mat = mat;
        this.ngheNghiep = ngheNghiep;
        // this.era = null;
    }

    public String getotherName() {
        return otherName;
    }
    // public String getPlace() {
    // return place;
    // }
    // public List<String> getEra() {
    // return era;
    // }

    // public Character (String name, String time, String description,String
    // otherName,String place,List<String> era) {
    // super(name,time,description);
    // this.otherName = otherName;
    // this.place = place;
    // this.era = era;
    // }
    public void setotherName(String otherName) {
        if (otherName != null)
            this.otherName = otherName;
        else {
            this.otherName = "Không có";
        }
    }

    // public void setPlace(String place) {
    // if(place != null) this.place = place;
    // else this.place = "Không rõ";
    // }
    // public void setEra(List<String> era) {
    // this.era = era;
    // }
    @Override
    public String hienthi() {
        return "Tên: " + this.getName() + "\n" + "Năm sinh - năm mất: " + this.getTime() + "\n"
                + /* "Tên khác: " + this.getotherName() + */ "\n" + /* "Quê quán: " + this.getPlace() + */ "\n"
                + /*
                   * "Thời: " + /*this.getEra().toString()
                   * +
                   */ "\n" + "Chi tiết: " + this.getDescription();
    }

    public String getSinh() {
        return sinh;
    }

    public void setSinh(String sinh) {
        this.sinh = sinh;
    }

    public String getMat() {
        return mat;
    }

    public void setMat(String mat) {
        this.mat = mat;
    }

    public String getNgheNghiep() {
        if (this.ngheNghiep == null) {
            return "Trong phần miêu tả";
        }
        return ngheNghiep;
    }

    public void setNgheNghiep(String ngheNghiep) {
        this.ngheNghiep = ngheNghiep;
    }
}
