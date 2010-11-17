package de.itemis.sample.json;

public class Trend {

	private String name;
	private String url;

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUrl() {
		return url;
	}
	
	@Override
	public String toString() {
		return name;
	}

}
