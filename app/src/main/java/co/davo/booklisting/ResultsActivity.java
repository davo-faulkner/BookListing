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

import java.util.ArrayList;

public class ResultsActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<Book>> {

    private static final int BOOK_LOADER_ID = 1;
    private TextView mEmptyStateTextView;
    private ProgressBar mProgressBar;
    private BookAdapter mBookAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        ListView bookListView = (ListView) findViewById(R.id.list);
        mEmptyStateTextView = (TextView) findViewById(R.id.empty_view);
        bookListView.setEmptyView(mEmptyStateTextView);

        ConnectivityManager cm =
                (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();

        mProgressBar = (ProgressBar) findViewById(R.id.loading_spinner);

        mBookAdapter = new BookAdapter(this, new ArrayList<Book>());

        LoaderManager loaderManager = getLoaderManager();

        if (!isConnected) {
            mProgressBar.setVisibility(View.GONE);
            mEmptyStateTextView.setText(R.string.no_internet_connection);
        } else {
            loaderManager.initLoader(BOOK_LOADER_ID, null, this);

            bookListView.setAdapter(mBookAdapter);

            bookListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Book currentBook = mBookAdapter.getItem(position);

                    Uri bookUri = Uri.parse(currentBook.getmUrl());

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

    }
    @Override
    public void onLoaderReset(Loader<ArrayList<Book>> loader) {

    }
}
