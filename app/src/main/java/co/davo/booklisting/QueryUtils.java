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

import static co.davo.booklisting.ResultsActivity.LOG_TAG;

/**
 * Created by Davo on 5/18/2017.
 */

public final class QueryUtils {
    private static final String KEY_ITEMS = "items";
    private static final String KEY_VOLUME_INFO = "volumeInfo";
    private static final String KEY_TITLE = "title";
    private static final String KEY_SUBTITLE = "subtitle";
    private static final String KEY_AUTHORS = "authors";
    private static final String KEY_PAGE_COUNT = "pageCount";
    private static final String KEY_PUBLISHED_DATE = "publishedDate";
    private static final String KEY_URL = "infoLink";
    private static final String KEY_DESCRIPTION = "description";

    private QueryUtils() {
    }

    public static ArrayList<Book> extractBooks(String requestUrl) {

        ArrayList<Book> books = new ArrayList<>();
        String booksJsonString = fetchBookData(requestUrl);

        try {
            JSONObject baseJsonResponse = new JSONObject(booksJsonString);
            JSONArray bookArray = baseJsonResponse.getJSONArray(KEY_ITEMS);
            for (int i = 0; i < bookArray.length(); i++) {
                JSONObject currentBook = bookArray.getJSONObject(i);
                JSONObject volumeInfo = currentBook.getJSONObject(KEY_VOLUME_INFO);
                String title = volumeInfo.getString(KEY_TITLE);
                String subtitle = "";
                if (volumeInfo.has(KEY_SUBTITLE)) {
                    subtitle = volumeInfo.getString(KEY_SUBTITLE);
                }
                JSONArray authorsArray = new JSONArray();
                if (volumeInfo.has(KEY_AUTHORS)) {
                    authorsArray = volumeInfo.getJSONArray(KEY_AUTHORS);
                }
                ArrayList<String> authors = new ArrayList<>();
                for (int j = 0; j < authorsArray.length(); j++) {
                    authors.add(authorsArray.getString(j));
                }
                int pageCount = 0;
                if (volumeInfo.has(KEY_PAGE_COUNT)) {
                    pageCount = volumeInfo.getInt(KEY_PAGE_COUNT);
                }
                String publishedDateString;
                Date publishedDate = null;
                boolean hasPublishedYear = false;
                boolean hasPublishedMonth = false;
                if (volumeInfo.has(KEY_PUBLISHED_DATE)) {
                    publishedDateString = volumeInfo.getString(KEY_PUBLISHED_DATE);
                    SimpleDateFormat dateParser;
                    if (publishedDateString.length() == 4) {
                        dateParser = new SimpleDateFormat("yyyy");
                        hasPublishedYear = true;
                    } else if (publishedDateString.length() == 7) {
                        dateParser = new SimpleDateFormat("yyyy-MM");
                        hasPublishedMonth = true;
                    } else {
                        dateParser = new SimpleDateFormat("yyyy-MM-dd");
                    }
                    publishedDate = dateParser.parse(publishedDateString);
                }
                String url = "";
                if (volumeInfo.has(KEY_URL)) {
                    url = volumeInfo.getString(KEY_URL);
                }
                String description = "";
                if (volumeInfo.has(KEY_DESCRIPTION)) {
                    if (volumeInfo.getString(KEY_DESCRIPTION).length() > 255) {
                        description = volumeInfo.getString(KEY_DESCRIPTION).substring(0, 255) + "...(Tap for more)";
                    } else {
                        description = volumeInfo.getString(KEY_DESCRIPTION) + " (Tap for more)";
                    }
                }

                books.add(new Book(title, subtitle, authors, pageCount, publishedDate, hasPublishedYear, hasPublishedMonth, url, description));
            }
        } catch (JSONException e) {
            Log.e(LOG_TAG, "Problem parsing the book JSON results", e);
            ResultsActivity.setHasJsonException(true);
        } catch (ParseException e) {
            Log.e(LOG_TAG, "Problem parsing the Date", e);
            ResultsActivity.setHasParseException(true);
        }
        return books;
    }

    private static String fetchBookData(String requestUrl) {
        URL url = createUrl(requestUrl);
        String booksJsonString = null;
        try {
            booksJsonString = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error closing input stream", e);
            ResultsActivity.setHasIoException(true);
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
                String badResponseCode = "Error response code: " + urlConnection.getResponseCode();
                Log.e(LOG_TAG, badResponseCode);
                ResultsActivity.setHasBadResponseCode(true);
                ResultsActivity.setBadResponseCode(urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the book JSON results", e);
            ResultsActivity.setHasIoException2(true);
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
            Log.e(LOG_TAG, "Error creating URL", e);
            ResultsActivity.setHasMalformedUrlException(true);
        }
        return url;
    }
}
