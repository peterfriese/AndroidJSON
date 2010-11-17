package de.itemis.sample.json;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import com.google.gson.Gson;

import android.app.ListActivity;
import android.os.Bundle;

public class TwitterTrendsListActivity extends ListActivity {
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        readData();
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
    
    private void readData() {
    	InputStreamReader reader = new InputStreamReader(getJSONData("http://search.twitter.com/trends.json"));
    	Gson gson = new Gson();
    	Trends trends = gson.fromJson(reader, Trends.class);
    	List<Trend> trends2 = trends.getTrends();
    	for (Trend trend : trends2) {
			System.out.println("Trend: " + trend.getName() + ": " + trend.getUrl());
		}
    }
    
    
}