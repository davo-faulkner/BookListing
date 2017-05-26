package co.davo.booklisting;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

public class SearchActivity extends AppCompatActivity {

    private static String mQueryUrl;

    public static String getQueryUrl() {
        return mQueryUrl;
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
                mQueryUrl = "https://www.googleapis.com/books/v1/volumes?q=" + searchEditText.getText().toString() + "&maxResults=10";

                Intent resultsIntent = new Intent(SearchActivity.this, ResultsActivity.class);
                startActivity(resultsIntent);

            }
        });
    }
}
