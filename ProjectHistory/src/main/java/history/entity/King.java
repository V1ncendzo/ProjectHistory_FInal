package history.entity;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class King extends BaseEntity {

    protected String triVi;
    protected String tienNhiem;
    protected String keNhiem;
    protected String sinh;
    protected String mat;
    protected String trieuDai;
    protected String anTang;
    protected String thanPhu;
    protected String thanMau;

    public King() {

    }

    public King(String name, String time, String description) {
        super(name, time, description);
    }

    public String getTriVi() {
        return triVi;
    }

    public void setTriVi(String triVi) {
        if (triVi != null)
            this.triVi = triVi;
        else
            this.triVi = "Không rõ";
    }

    public String getTienNhiem() {
        return tienNhiem;
    }

    public void setTienNhiem(String tienNhiem) {
        if (tienNhiem != null)
            this.tienNhiem = tienNhiem;
        else
            this.tienNhiem = "Không có";
    }

    public String getKeNhiem() {
        return keNhiem;
    }

    public void setKeNhiem(String keNhiem) {
        if (keNhiem != null)
            this.keNhiem = keNhiem;
        else
            this.keNhiem = "Không có";
    }

    public String getSinh() {
        return sinh;
    }

    public void setSinh(String sinh) {
        if (sinh != null)
            this.sinh = sinh;
        else
            this.sinh = "Không rõ";
    }

    public String getMat() {
        return mat;
    }

    public void setMat(String mat) {
        if (mat != null)
            this.mat = mat;
        else
            this.mat = "Không rõ";
    }

    public String getTrieuDai() {
        return trieuDai;
    }

    public void setTrieuDai(String trieuDai) {
        if (trieuDai != null)
            this.trieuDai = trieuDai;
        else
            this.trieuDai = "Không rõ";
    }

    public String getAntang() {
        return anTang;
    }

    public void setAntang(String anTang) {
        if (anTang != null)
            this.anTang = anTang;
        else
            this.anTang = "Không rõ";
    }

    public String getThanPhu() {
        return thanPhu;
    }

    public void setThanPhu(String thanPhu) {
        if (thanPhu != null)
            this.thanPhu = thanPhu;
        else
            this.thanPhu = "Không rõ";
    }

    public String getThanMau() {
        return thanMau;
    }

    public void setThanMau(String thanMau) {
        if (thanMau != null)
            this.thanMau = thanMau;
        else
            this.thanMau = "Không rõ";
    }

    @Override
    public String hienthi() {
        return "ID" + this.getId() + "Tên: " + this.getName() + "\n" +
                "Trị vì: " + this.getTriVi() + "\n" + "Tiền nhiệm: " + this.getTienNhiem() + "\n"
                + "Kế nhiệm: " + this.getKeNhiem() + "\n" + "Sinh: " + this.getSinh() + "\n" + "Mất: " + this.getMat()
                + "\n"
                + "Triều đại: " + this.getTrieuDai() + "\n" + "An Táng: " + this.getAntang() + "\n" + "Thân phụ: "
                + this.getThanPhu() + "\n"
                + "Thân mẫu: " + this.getThanMau() + "\n" + "Chi tiết: " + this.getDescription();
    }
}
