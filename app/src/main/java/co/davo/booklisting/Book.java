package co.davo.booklisting;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Davo on 5/18/2017.
 */

public class Book {

    private String mTitle;
    private ArrayList<String> mAuthors;
    private int mPageCount;
    private Date mPublishedDate;
    private String mUrl;

    public Book(String mTitle, ArrayList<String> mAuthors, int mPageCount, Date mPublishedDate, String mUrl) {
        this.mTitle = mTitle;
        this.mAuthors = mAuthors;
        this.mPageCount = mPageCount;
        this.mPublishedDate = mPublishedDate;
        this.mUrl = mUrl;
    }

    public String getmTitle() {
        return mTitle;
    }


    public ArrayList<String> getmAuthors() {
        return mAuthors;
    }


    public int getmPageCount() {
        return mPageCount;
    }


    public Date getmPublishedDate() {
        return mPublishedDate;
    }


    public String getmUrl() {
        return mUrl;
    }

}
