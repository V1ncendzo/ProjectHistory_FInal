package scrapingdata.scraping.dynasty;

import history.entity.Dynasty;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseScrapingDynasty  {
    protected static List<Dynasty> dynastyList = new ArrayList<>();

    public BaseScrapingDynasty(String url) {
        super();
    }

    public static List<Dynasty> getDynastyList() {
        return dynastyList;
    }

    public static void setDynastyList(List<Dynasty> dynastyList) {
        BaseScrapingDynasty.dynastyList = dynastyList;
    }

    public BaseScrapingDynasty() {
    }

    public abstract void getData();

}