package history.entity;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class BaseEntity {
    private int id;
    private static int entityID = 0;
    private String name;
    private String time;
    private String description;
    private String moreInfo;

    public BaseEntity() {
        entityID++;
        this.id = entityID;
    }

    public BaseEntity(String name, String time, String description) {
        this.name = name;
        this.time = time;
        this.description = description;
        entityID++;
        this.id = entityID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name != null)
            this.name = name;
        else
            this.name = "Không rõ";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        if (time != null)
            this.time = time;
        else
            this.time = "Không rõ";
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        if (description != null)
            this.description = description;
        else
            this.description = "";
    }

    public abstract String hienthi();

    public boolean isRelated(String name) {
        if (description == null && moreInfo == null)
            return false;

        if (description == null)
            return moreInfo.contains(name);

        if (moreInfo == null)
            return description.contains(name);

        return description.contains(name) || moreInfo.contains(name);
    }

    public void setMoreInfo(String moreInfo) {
        this.moreInfo = moreInfo;
    }

    private Map<String, Integer> related = new HashMap<>();

    public Map<String, Integer> getRelated() {
        return this.related;
    }

    public void setRelated(Map<String, Integer> related) {
        this.related = related;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof BaseEntity) {
            if (this.isMatch(((BaseEntity) obj).getId()))
                return true;
            else {
                if (this.name == null) {
                    return false;
                } else
                    return this.hasName(((BaseEntity) obj).getName());
            }
        }
        return false;
    }

    public boolean hasName(String name) {
        return this.name.equalsIgnoreCase(name);
    }

    public boolean isMatch(Integer id) {
        if (id == null)
            return false;
        return this.getId() == id;
    }

    public boolean isMatch(String name) {
        if (this.name != null) {
            return this.name.toLowerCase().contains(name.toLowerCase());
        }
        return false;
    }
}
