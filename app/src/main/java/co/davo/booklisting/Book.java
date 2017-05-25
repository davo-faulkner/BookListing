package co.davo.booklisting;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Davo on 5/18/2017.
 */

public class Book {

    private String mTitle;
    private String mSubtitle;
    private ArrayList<String> mAuthors;
    private int mPageCount;
    private Date mPublishedDate;
    private String mUrl;
    private String mDescription;

    public Book(String title, ArrayList<String> authors, int pageCount, Date publishedDate, String url, String description) {
        this.mTitle = title;
        this.mAuthors = authors;
        this.mPageCount = pageCount;
        this.mPublishedDate = publishedDate;
        this.mUrl = url;
        this.mDescription = description;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getSubtitle() {
        return mSubtitle;
    }


    public ArrayList<String> getAuthors() {
        return mAuthors;
    }


    public int getPageCount() {
        return mPageCount;
    }


    public Date getPublishedDate() {
        return mPublishedDate;
    }


    public String getUrl() {
        return mUrl;
    }
    public String getDescription() {
        return mDescription;
    }
}
