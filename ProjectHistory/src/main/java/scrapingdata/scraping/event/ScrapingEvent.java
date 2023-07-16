package scrapingdata.scraping.event;


import java.io.IOException;
import java.util.List;

public abstract class ScrapingEvent  {
    public ScrapingEvent(String url) {
        super();
    }

    public ScrapingEvent() {
    }

//    protected List<Event> eventList = new ArrayList<>();
//
//    public List<Event> getEventList() {
//        return eventList;
//    }

//    public void setEventList(List<Event> eventList) {
//        this.eventList = eventList;
//    }

    public abstract void getAllEventLinks() throws IOException;
    public abstract void getEventData(List<String> URLS) throws IOException;

}