package co.davo.booklisting;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

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
                //TODO Resume Here, Davo
            }
        }
        catch (JSONException e){
            Log.e("QueryUtils", "Problem parsing the book JSON results", e);
        }
    }
}
