package co.davo.booklisting;

import android.util.Log;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Davo on 5/18/2017.
 */

public final class QueryUtils {

    private QueryUtils() {
    }

    public static ArrayList<Book> extractBooks(String requestUrl) {

        ArrayList<Book> books = new ArrayList<>();
        String booksJsonString = fetchBookData(requestUrl);

        try {
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
        } catch (JSONException e) {
            Log.e("QueryUtils", "Problem parsing the book JSON results", e);
        } catch (ParseException e) {
            Log.e("QueryUtils", "Problem parsing the Date", e);
        }
        return books;
    }

    private static String fetchBookData(String requestUrl) {
        URL url = createUrl(requestUrl);
        String booksJsonString = null;
        try {
            booksJsonString = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e("QueryUtils", "Error closing input stream", e);
        }
        return booksJsonString;
    }

    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";
        if (url == null) {
            return jsonResponse;
        }
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e("QueryUtils", "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e("QueryUtils", "Problem retrieving the earthquake JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e("QueryUtils", "Error creating URL", e);
        }
        return url;
    }
}
