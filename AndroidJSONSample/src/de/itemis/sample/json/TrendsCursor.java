package de.itemis.sample.json;

import java.util.List;

import android.database.MatrixCursor;

public class TrendsCursor extends MatrixCursor {
	private static final String ID_COLUMN = "_id";
	private static final String NAME_COLUMN = "name";
	private static final String URL_COLUMN = "url";
	private static final int ID_COLUMN_IDX = 1;
	private static final int NAME_COLUMN_IDX = 1;
	private static final int URL_COLUMN_IDX = 2;
	
	private static final String[] columnNames = {ID_COLUMN, NAME_COLUMN, URL_COLUMN};

	public TrendsCursor(List<Trend> trends) {
		super(columnNames);
		for (Trend trend : trends) {
			addRow(new Object[]{trend.getUrl().hashCode(), trend.getName(), trend.getUrl()});
		}
	}
	
	public Trend getTrend() {
		String name = getString(NAME_COLUMN_IDX);
		String url = getString(URL_COLUMN_IDX);
		Trend trend = new Trend();
		trend.setName(name);
		trend.setUrl(url);
		return trend;
	}

}
