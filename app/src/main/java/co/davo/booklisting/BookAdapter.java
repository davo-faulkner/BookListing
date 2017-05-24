package co.davo.booklisting;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Davo on 5/21/2017.
 */

public class BookAdapter extends ArrayAdapter<Book> {

    public BookAdapter(Activity context, ArrayList<Book> books) {
        super(context, 0, books);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }
        Book currentBook = getItem(position);
        
        TextView titleTextView = (TextView) listItemView.findViewById(R.id.title);
        titleTextView.setText(currentBook.getTitle());
        
        TextView authorTextView = (TextView) listItemView.findViewById(R.id.author);
        String authorsString = "";
        ArrayList<String> authors = currentBook.getAuthors();
        for (int i = 0; i < authors.size(); i++) {
            authorsString = authorsString + currentBook.getAuthors().get(i);
            if (authors.size() - i != 1) {
                authorsString = authorsString + "\n";
            }
        }
        authorTextView.setText(authorsString);

        TextView publishedDateTextView = (TextView) listItemView.findViewById(R.id.published_date);
        String publishedDateString = "";
        publishedDateTextView.setText("Jan 1, 1970");

        TextView pageCountTextView = (TextView) listItemView.findViewById(R.id.page_count);
        pageCountTextView.setText(currentBook.getPageCount() + " pages");

        TextView descriptionTextView = (TextView) listItemView.findViewById(R.id.description);
        descriptionTextView.setText(currentBook.getDescription());

        return listItemView;
    }
}
