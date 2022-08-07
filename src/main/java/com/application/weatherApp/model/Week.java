package com.application.weatherApp.model;

import java.util.ArrayList;
import java.util.List;

public class Week {
	private List<Day> week = new ArrayList<>();
	private List<Day> daily;
	private Current current;

	public Current getCurrent() {
		return current;
	}

	public List<Day> getDaily() {
		return daily;
	}

	public List<Day> getWeek() {
		return this.week;
	}

}
