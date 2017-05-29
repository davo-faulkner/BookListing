package co.davo.booklisting;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.ArrayList;

/**
 * Created by Davo on 5/21/2017.
 */

public class BookLoader extends AsyncTaskLoader<ArrayList<Book>> {

    private String url;

    public BookLoader(Context context, String url) {
        super(context);
        this.url = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public ArrayList<Book> loadInBackground() {
        if (url == null) {
            return null;
        }
        ArrayList<Book> books = QueryUtils.extractBooks(url);
        return books;
    }
}
