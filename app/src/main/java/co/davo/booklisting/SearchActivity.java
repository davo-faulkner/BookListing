package co.davo.booklisting;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

public class SearchActivity extends AppCompatActivity {
    private static final String BASE_QUERY_URL = "https://www.googleapis.com/books/v1/volumes?q=";
    private static final String MAX_RESULTS_URL_FRAGMENT = "&maxResults=10";
    private static String queryUrl;

    public static String getQueryUrl() {
        return queryUrl;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        final EditText searchEditText = (EditText) findViewById(R.id.search_editText);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

        Button searchButton = (Button) findViewById(R.id.search_button);
        searchButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                queryUrl = BASE_QUERY_URL + searchEditText.getText().toString() + MAX_RESULTS_URL_FRAGMENT;

                Intent resultsIntent = new Intent(SearchActivity.this, ResultsActivity.class);
                startActivity(resultsIntent);

            }
        });
    }
}
