package scrapingdata.scraping.figure;

import java.util.List;

public abstract class BaseScrapingFigure {

    public BaseScrapingFigure(String url){
        super();
    }
    public BaseScrapingFigure(){
    }

    public abstract List<String> getFigureLinks(String url);

    //extracts data from each character URL and writes it to a JSON file.
    public abstract void getFigureData(List<String> allUrl) ;

//    public abstract void start();  ????
}
