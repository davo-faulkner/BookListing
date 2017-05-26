package co.davo.booklisting;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
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
        TextView subtitleTextView = (TextView) listItemView.findViewById(R.id.subtitle);
        if (currentBook.getSubtitle() == "") {
            subtitleTextView.setVisibility(View.GONE);
        } else {
            subtitleTextView.setText(currentBook.getSubtitle());
        }
        TextView authorTextView = (TextView) listItemView.findViewById(R.id.author);
        if (currentBook.getAuthors().size() == 0) {
            authorTextView.setVisibility(View.GONE);
        } else {
            String authorsString = "";
            ArrayList<String> authors = currentBook.getAuthors();
            for (int i = 0; i < authors.size(); i++) {
                authorsString = authorsString + currentBook.getAuthors().get(i);
                if (authors.size() - i != 1) {
                    authorsString = authorsString + ", ";
                }
            }
            authorTextView.setText(authorsString);
        }

        TextView publishedDateTextView = (TextView) listItemView.findViewById(R.id.published_date);
        if (currentBook.getPublishedDate() == null) {
            publishedDateTextView.setVisibility(View.GONE);
        } else {
            SimpleDateFormat publishedDateFormatter = new SimpleDateFormat("MMMM d, yyyy");
            SimpleDateFormat publishedYearFormatter = new SimpleDateFormat("yyyy");
            SimpleDateFormat publishedMonthFormatter = new SimpleDateFormat("MMMM yyyy");
            String publishedDateString = "";
            if (currentBook.getHasPublishedYear() == true) {
                publishedDateString = publishedYearFormatter.format(currentBook.getPublishedDate());
            } else if (currentBook.getHasPublishedMonth() == true) {
                publishedDateString = publishedMonthFormatter.format(currentBook.getPublishedDate());
            } else {
                publishedDateString = publishedDateFormatter.format(currentBook.getPublishedDate());
            }
            publishedDateTextView.setText("Published " + publishedDateString);
        }

        TextView pageCountTextView = (TextView) listItemView.findViewById(R.id.page_count);
        if (currentBook.getPageCount() == 0) {
            pageCountTextView.setVisibility(View.INVISIBLE);
        } else {
            DecimalFormat pageCountFormatter = new DecimalFormat("#,###,###");
            pageCountTextView.setText(pageCountFormatter.format(currentBook.getPageCount()) + " pages");
        }

        TextView descriptionTextView = (TextView) listItemView.findViewById(R.id.description);
        if (currentBook.getDescription() == "") {
            descriptionTextView.setVisibility(View.GONE);
        } else {
            descriptionTextView.setText(currentBook.getDescription());
        }

        return listItemView;
    }
}
