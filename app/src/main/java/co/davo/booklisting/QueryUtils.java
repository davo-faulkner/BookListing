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
                String subtitle = "";
                if (volumeInfo.has("subtitle")) {
                    subtitle = volumeInfo.getString("subtitle");
                }
                JSONArray authorsArray = new JSONArray();
                if (volumeInfo.has("authors")) {
                    authorsArray = volumeInfo.getJSONArray("authors");
                }
                ArrayList<String> authors = new ArrayList<>();
                for (int j = 0; j < authorsArray.length(); j++) {
                    authors.add(authorsArray.getString(j));
                }
                int pageCount = 0;
                if (volumeInfo.has("pageCount")) {
                    pageCount = volumeInfo.getInt("pageCount");
                }
                String publishedDateString;
                Date publishedDate = null;
                boolean hasPublishedYear = false;
                if (volumeInfo.has("publishedDate")) {
                    publishedDateString = volumeInfo.getString("publishedDate");
                    SimpleDateFormat dateParser;
                    if (publishedDateString.length() == 4) {
                        dateParser = new SimpleDateFormat("yyyy");
                        hasPublishedYear = true;
                    } else {
                        dateParser = new SimpleDateFormat("yyyy-MM-dd");
                    }
                    publishedDate = dateParser.parse(publishedDateString);
                }
                String url = "";
                if (volumeInfo.has("infoLink")) {
                    url = volumeInfo.getString("infoLink");
                }
                String description = "";
                if (volumeInfo.has("description")) {
                    if (volumeInfo.getString("description").length() > 255) {
                        description = volumeInfo.getString("description").substring(0, 255) + "...(Tap for more)";
                    } else {
                        description = volumeInfo.getString("description") + " (Tap for more)";
                    }
                }

                books.add(new Book(title, subtitle, authors, pageCount, publishedDate, hasPublishedYear, url, description));
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
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
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
