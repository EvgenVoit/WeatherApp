package com.application.weatherApp.model;

import java.util.HashMap;
import java.util.List;

public class Current {
	private Double temp;
	private List<HashMap<String, String>> weather;

	public Integer getTemp() {
		return temp.intValue();
	}

	public List<HashMap<String, String>> getWeather() {
		return weather;
	}
}
