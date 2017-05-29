package co.davo.booklisting;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ResultsActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<Book>> {
    public static final String LOG_TAG = ResultsActivity.class.getName();
    private static final int BOOK_LOADER_ID = 1;

    private TextView emptyStateTextView;
    private ProgressBar progressBar;
    private BookAdapter bookAdapter;
    private static boolean hasJsonException = false;
    private static boolean hasParseException = false;
    private static boolean hasIoException = false;
    private static boolean hasBadResponseCode = false;
    private static int badResponseCode;
    private static boolean hasIoException2 = false;
    private static boolean hasMalformedUrlException = false;

    public static void setHasJsonException(boolean hasJsonException) {
        ResultsActivity.hasJsonException = hasJsonException;
    }

    public static void setHasParseException(boolean hasParseException) {
        ResultsActivity.hasParseException = hasParseException;
    }

    public static void setHasIoException(boolean hasIoException) {
        ResultsActivity.hasIoException = hasIoException;
    }

    public static void setHasBadResponseCode(boolean hasBadResponseCode) {
        ResultsActivity.hasBadResponseCode = hasBadResponseCode;
    }

    public static void setBadResponseCode(int badResponseCode) {
        ResultsActivity.badResponseCode = badResponseCode;
    }

    public static void setHasIoException2(boolean hasIoException2) {
        ResultsActivity.hasIoException2 = hasIoException2;
    }

    public static void setHasMalformedUrlException(boolean hasMalformedUrlException) {
        ResultsActivity.hasMalformedUrlException = hasMalformedUrlException;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        ListView bookListView = (ListView) findViewById(R.id.list);
        emptyStateTextView = (TextView) findViewById(R.id.empty_view);
        bookListView.setEmptyView(emptyStateTextView);

        ConnectivityManager cm =
                (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();

        progressBar = (ProgressBar) findViewById(R.id.loading_spinner);

        bookAdapter = new BookAdapter(this, new ArrayList<Book>());

        LoaderManager loaderManager = getLoaderManager();

        if (!isConnected) {
            progressBar.setVisibility(View.GONE);
            emptyStateTextView.setText(R.string.no_internet_connection);
        } else {
            loaderManager.initLoader(BOOK_LOADER_ID, null, this);

            bookListView.setAdapter(bookAdapter);

            bookListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Book currentBook = bookAdapter.getItem(position);

                    Uri bookUri = Uri.parse(currentBook.getUrl());

                    Intent websiteIntent = new Intent(Intent.ACTION_VIEW, bookUri);

                    startActivity(websiteIntent);
                }
            });
        }
    }

    @Override
    public Loader<ArrayList<Book>> onCreateLoader(int id, Bundle args) {
        return new BookLoader(this, SearchActivity.getQueryUrl());
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<Book>> loader, ArrayList<Book> data) {
        progressBar.setVisibility(View.GONE);

        emptyStateTextView.setText(R.string.no_books_found);

        bookAdapter.clear();

        if (data != null && !data.isEmpty()) {
            bookAdapter.addAll(data);
        }
        if (hasJsonException) {
            Toast.makeText(this, "Problem parsing the book JSON results", Toast.LENGTH_SHORT).show();
        }
        if (hasParseException) {
            Toast.makeText(this, "Problem parsing the Date", Toast.LENGTH_SHORT).show();
        }
        if (hasIoException) {
            Toast.makeText(this, "Error closing input stream", Toast.LENGTH_SHORT).show();
        }
        if (hasBadResponseCode) {
            String badResponseCodeString = "Error response code " + badResponseCode;
            Toast.makeText(this, badResponseCodeString, Toast.LENGTH_SHORT).show();
        }
        if (hasIoException2) {
            Toast.makeText(this, "Problem retrieving the book JSON results", Toast.LENGTH_SHORT).show();
        }
        if (hasMalformedUrlException) {
            Toast.makeText(this, "Error creating URL", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<Book>> loader) {
        bookAdapter.clear();
    }
}
