package de.itemis.sample.json;

import java.util.List;

public class Trends {

	private String as_of;
	private List<Trend> trends;

	public void setAs_of(String as_of) {
		this.as_of = as_of;
	}

	public String getAs_of() {
		return as_of;
	}

	public void setTrends(List<Trend> trends) {
		this.trends = trends;
	}

	public List<Trend> getTrends() {
		return trends;
	}

}
