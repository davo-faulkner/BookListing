package co.davo.booklisting;

import android.app.LoaderManager;
import android.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;

public class ResultsActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<Book>> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);
    }
    @Override
    public Loader<ArrayList<Book>> onCreateLoader(int id, Bundle args) {
        return null;
    }
    @Override
    public void onLoadFinished(Loader<ArrayList<Book>> loader, ArrayList<Book> data) {

    }
    @Override
    public void onLoaderReset(Loader<ArrayList<Book>> loader) {

    }
}
