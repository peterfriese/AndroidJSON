package de.itemis.sample.json;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.ListActivity;
import android.content.ContentResolver;
import android.database.Cursor;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.ArrayAdapter;
import android.widget.CursorAdapter;
import android.widget.SimpleAdapter;
import android.widget.SimpleCursorAdapter;

import com.google.gson.Gson;

public class TwitterTrendsListActivity extends ListActivity {
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        ContentResolver cr = getContentResolver();
        Cursor cursor = cr.query(TwitterTrendsContentProvider.CONTENT_URI, null, null, null, null);
        
        SimpleCursorAdapter trendsAdapter = new SimpleCursorAdapter(
        		this, 
        		android.R.layout.simple_list_item_1, cursor, 
        		new String[] {"name", "url"}, 
        		new int[]{android.R.id.text1, android.R.id.text2});
        setListAdapter(trendsAdapter);
//        List<Trend> trends = readData();
//        TrendsAdapter trendsAdapter = new TrendsAdapter(this, android.R.layout.simple_list_item_1, trends);
//        setListAdapter(trendsAdapter);
    }
    
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
    
    private List<Trend> readData() {
    	InputStreamReader reader = new InputStreamReader(getJSONData("http://search.twitter.com/trends.json"));
    	Gson gson = new Gson();
    	Trends trends = gson.fromJson(reader, Trends.class);
    	return trends.getTrends();
    }
    
    
}