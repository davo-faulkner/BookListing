package co.davo.booklisting;

/**
 * Created by Davo on 5/18/2017.
 */

public class Book {
    private String mTitle;
    private String mAuthor;
    private int mPageCount;
    private String mPublishedDate;
    private String mUrl;

    public Book(String mTitle, String mAuthor, int mPageCount, String mPublishedDate, String mUrl) {
        this.mTitle = mTitle;
        this.mAuthor = mAuthor;
        this.mPageCount = mPageCount;
        this.mPublishedDate = mPublishedDate;
        this.mUrl = mUrl;
    }

    public String getmTitle() {
        return mTitle;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getmAuthor() {
        return mAuthor;
    }

    public void setmAuthor(String mAuthor) {
        this.mAuthor = mAuthor;
    }

    public int getmPageCount() {
        return mPageCount;
    }

    public void setmPageCount(int mPageCount) {
        this.mPageCount = mPageCount;
    }

    public String getmPublishedDate() {
        return mPublishedDate;
    }

    public void setmPublishedDate(String mPublishedDate) {
        this.mPublishedDate = mPublishedDate;
    }

    public String getmUrl() {
        return mUrl;
    }

    public void setmUrl(String mUrl) {
        this.mUrl = mUrl;
    }
}
