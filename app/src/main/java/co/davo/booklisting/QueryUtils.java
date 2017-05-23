package co.davo.booklisting;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Davo on 5/18/2017.
 */

public final class QueryUtils {

    private QueryUtils(){
    }

    public static ArrayList<Book> extractBooks(String requestUrl) {

        ArrayList<Book> books = new ArrayList<>();
        String booksJsonString = fetchBookData(requestUrl);

        try{
            JSONObject baseJsonResponse = new JSONObject(booksJsonString);
            JSONArray bookArray = baseJsonResponse.getJSONArray("items");
            for (int i = 0; i < bookArray.length(); i++) {
                JSONObject currentBook = bookArray.getJSONObject(i);
                JSONObject volumeInfo = currentBook.getJSONObject("volumeInfo");
                String title = volumeInfo.getString("title");
                JSONArray authorsArray = volumeInfo.getJSONArray("authors");
                ArrayList<String> authors = new ArrayList<>();
                for (int j = 0; j < authorsArray.length(); j++) {
                    authors.add(authorsArray.getString(j));
                }
                int pageCount = volumeInfo.getInt("pageCount");
                String publishedDateString = volumeInfo.getString("publishedDate");
                SimpleDateFormat dateParser = new SimpleDateFormat("yyyy-MM-dd");
                Date publishedDate = dateParser.parse(publishedDateString);
                String url = volumeInfo.getString("infoLink");
                String description = volumeInfo.getString("description");

                books.add(new Book(title, authors, pageCount, publishedDate, url, description));
            }
        }
        catch (JSONException e){
            Log.e("QueryUtils", "Problem parsing the book JSON results", e);
        }
        catch (ParseException e) {
            Log.e("QueryUtils", "Problem parsing the Date", e);
        }
    }
}
