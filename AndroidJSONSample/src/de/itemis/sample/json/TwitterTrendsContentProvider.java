package de.itemis.sample.json;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;

import com.google.gson.Gson;

public class TwitterTrendsContentProvider extends ContentProvider {
	
	// content provider URIs
	private static final String PROVIDER_NAME = "de.itemis.sample.json.Trends";
	private static final String URI_ITEMS = "trends";
	private static final String URI_ITEM = URI_ITEMS + "/#";
	private static final String URI_STRING = "content://" + PROVIDER_NAME + "/"  + URI_ITEMS;
	public static final Uri CONTENT_URI = Uri.parse(URI_STRING);
	
	private static final int TRENDS = 1;
	private static final int TREND = 2;
	private static UriMatcher uriMatcher;
	static {
		uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
		uriMatcher.addURI(PROVIDER_NAME, URI_ITEMS, TRENDS);
		uriMatcher.addURI(PROVIDER_NAME, URI_ITEM, TREND);
	}
	
	private JSONHelper jsonHelper;
	
	private static final class JSONHelper {
		
	    private InputStream getJSONData(String url) {
	    	DefaultHttpClient httpClient = new DefaultHttpClient();
	    	URI uri;
	    	InputStream data = null;
	    	try {
				uri = new URI(url);
				HttpGet method = new HttpGet(uri);
				HttpResponse response = httpClient.execute(method);
				data = response.getEntity().getContent();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return data;
	    }
	    
	    public List<Trend> getTrends() {
	    	InputStreamReader reader = new InputStreamReader(getJSONData("http://search.twitter.com/trends.json"));
	    	Gson gson = new Gson();
	    	Trends trends = gson.fromJson(reader, Trends.class);
	    	return trends.getTrends();
	    }

	}
	
	@Override
	public boolean onCreate() {
		jsonHelper = new JSONHelper();
		return true;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		List<Trend> trends = jsonHelper.getTrends();
		TrendsCursor cursor = new TrendsCursor(trends);
		return cursor;
	}

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getType(Uri uri) {
		switch (uriMatcher.match(uri)) {
		case TRENDS:
			return "vnd.android.cursor.dir/vnd." + PROVIDER_NAME;
		case TREND:
		default:
			throw new IllegalArgumentException("Unsupported URI: " + uri);
		}
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		return null;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		return 0;
	}

}
