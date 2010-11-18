package de.itemis.sample.json;

import android.app.ListActivity;
import android.content.AsyncQueryHandler;
import android.content.ContentResolver;
import android.database.Cursor;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class TwitterTrendsListActivity extends ListActivity {
	
    private TrendsAsyncQueryHandler queryHandler;
	private Toast loading;

	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        queryHandler = new TrendsAsyncQueryHandler(getContentResolver());
        loading = Toast.makeText(this, "Fetching trends", Toast.LENGTH_LONG);
        fetchData();
    }

	private void fetchData() {
		loading.show();
		queryHandler.startQuery(1, null, TwitterTrendsContentProvider.CONTENT_URI, null, null, null, null);
	}
    
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
    	fetchData();
    	return super.onKeyDown(keyCode, event);
    }
    
    private class TrendsAsyncQueryHandler extends AsyncQueryHandler {
    	@Override
    	protected void onQueryComplete(int token, Object cookie, Cursor cursor) {
    		super.onQueryComplete(token, cookie, cursor);
            SimpleCursorAdapter trendsAdapter = new SimpleCursorAdapter(
            		TwitterTrendsListActivity.this, 
            		android.R.layout.simple_list_item_1, cursor, 
            		new String[] {"name", "url"}, 
            		new int[]{android.R.id.text1, android.R.id.text2});
            setListAdapter(trendsAdapter);
            loading.cancel();
    	}

		public TrendsAsyncQueryHandler(ContentResolver cr) {
			super(cr);
		}
    }
    
}