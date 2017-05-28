package co.davo.booklisting;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Davo on 5/18/2017.
 */

public class Book {

    private String title;
    private String subtitle;
    private ArrayList<String> authors;
    private int pageCount;
    private Date publishedDate;
    private boolean hasPublishedYear;
    private boolean hasPublishedMonth;
    private String url;
    private String description;

    public Book(String title, String subtitle, ArrayList<String> authors, int pageCount, Date publishedDate, boolean hasPublishedyear, boolean hasPublishedMonth, String url, String description) {
        this.title = title;
        this.subtitle = subtitle;
        this.authors = authors;
        this.pageCount = pageCount;
        this.publishedDate = publishedDate;
        this.hasPublishedYear = hasPublishedyear;
        this.hasPublishedMonth = hasPublishedMonth;
        this.url = url;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public ArrayList<String> getAuthors() {
        return authors;
    }

    public int getPageCount() {
        return pageCount;
    }

    public Date getPublishedDate() {
        return publishedDate;
    }

    public boolean getHasPublishedYear() {
        return hasPublishedYear;
    }

    public  boolean getHasPublishedMonth() {
        return hasPublishedMonth;
    }

    public String getUrl() {
        return url;
    }

    public String getDescription() {
        return description;
    }
}
