package co.davo.booklisting.BookLoader;

import android.content.AsyncTaskLoader;
import android.content.Context;
import java.util.ArrayList;
import co.davo.booklisting.Book;

/**
 * Created by Davo on 5/21/2017.
 */

public class BookLoader extends AsyncTaskLoader<ArrayList<Book>> {

    private String mUrl;

    public BookLoader(Context context, String url) {
        super(context);
        mUrl = url;
    }
    @Override
    public ArrayList<Book> loadInBackground() {
        return null;
    }
}
