package com.application.weatherApp.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import com.application.weatherApp.model.City;
import com.application.weatherApp.model.Week;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;

@Service
public class WeatherService {
	private String url = "http://api.openweathermap.org/data/2.5/";
	private String metric = "&units=metric";
	private String lang = "&lang=pt_br";
	private String apiKey = "add9a8a4b3826b943c3ded900e5f6859";
				
	public City findForecastByName(String name) throws Exception {
		String urlString = url + "weather?q=" + name + metric + lang + "&appid=" + apiKey;
		try {
			URL url = new URL(urlString);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();

			if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
				throw new RuntimeException("HTTP error code: " + connection.getResponseCode());
			}
			BufferedReader resp = new BufferedReader(new InputStreamReader((connection.getInputStream())));
			String jsonString = jsonToString(resp);
			Gson gson = new Gson();
			City city = gson.fromJson(jsonString, City.class);
			return city;

		} catch (Exception e) {
			throw new Exception("Error: " + e);
		}
	}

	public Week findWeekForecastByCoord(Double lat, Double lon) throws Exception {
		String urlString = url + "onecall?" + "lat=" + lat + "&lon=" + lon + "&exclude=minutely,hourly,alerts" + metric
				+ lang + "&appid=" + apiKey;

		try {
			URL url = new URL(urlString);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();

			if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
				throw new RuntimeException("HTTP error code: " + connection.getResponseCode());
			}

			BufferedReader resp = new BufferedReader(new InputStreamReader((connection.getInputStream())));
			String jsonString = jsonToString(resp);

			Gson gson = new Gson();
			Week week = gson.fromJson(jsonString, Week.class);

			return week;

		} catch (Exception e) {
			throw new Exception("Error: " + e);
		}
	}

	public static String jsonToString(BufferedReader bufferedReader) throws IOException {
		String response, jsonString = "";
		
		while ((response = bufferedReader.readLine()) != null) {
			jsonString += response;
		}
		return jsonString;
	}

}
