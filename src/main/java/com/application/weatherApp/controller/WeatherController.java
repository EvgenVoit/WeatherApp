package com.application.weatherApp.controller;

import java.time.DayOfWeek;
import java.time.LocalDateTime;

import com.application.weatherApp.model.City;
import com.application.weatherApp.model.Day;
import com.application.weatherApp.model.Week;
import com.application.weatherApp.service.WeatherService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping
public class WeatherController {
	private final WeatherService weatherService;

	WeatherController(WeatherService weatherService) {
		this.weatherService = weatherService;
	}

	@GetMapping("/")
	public String home() {
		return "home";
	}

	@GetMapping("/weather/find")
	public String recedeCity(@RequestParam("name") String cityName, Model model) throws Exception {
		try {
			City todayInfo = weatherService.findForecastByName(cityName);
			Double cityLat = todayInfo.getCoord().getLat();
			Double cityLon = todayInfo.getCoord().getLon();
			Week weekInfo = weatherService.findWeekForecastByCoord(cityLat, cityLon);
			int daysWeek = 7;
			for (int i = 0; i <= daysWeek; i++) {
				LocalDateTime date = LocalDateTime.now().plusDays(i);
				Day day = new Day(DayOfWeek.from(date), date.getDayOfMonth());
				weekInfo.getWeek().add(day);
			}
			model.addAttribute("todayInfo", todayInfo);
			model.addAttribute("weekInfo", weekInfo);
			return "city";
			
		} catch (Exception e) {
			return "redirect:/";
		}
		
	}
	@ExceptionHandler(IllegalArgumentException.class)
    public String onError() {
    	return "redirect:/";
    }

}
