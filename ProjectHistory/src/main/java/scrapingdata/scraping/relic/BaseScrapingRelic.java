package scrapingdata.scraping.relic;

import java.util.List;


public abstract class BaseScrapingRelic {
    public BaseScrapingRelic(){
        super();

    }

    public abstract List<String> getRelicLinks(String url);
    public abstract void getRelicData(List<String> allRelicLinks);

}
