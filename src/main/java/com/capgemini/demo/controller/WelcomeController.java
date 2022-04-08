package com.capgemini.demo.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import com.capgemini.demo.model.Billionaire;

@Controller
public class WelcomeController {

	public static final String REST_SERVICE_URI = "http://localhost:8080/billionaires";

	// inject via application.properties
	@Value("${welcome.message}")
	private String message;

	private List<String> tasks = Arrays.asList("a", "b", "c", "d", "e", "f",
			"g");

	@GetMapping("/")
	public String main(Model model) {
		model.addAttribute("message", message);
		model.addAttribute("tasks", tasks);

		RestTemplate restTemplate = new RestTemplate();
		List<Billionaire> billionaires = restTemplate
				.getForObject(REST_SERVICE_URI, List.class);

		model.addAttribute("billionaires", billionaires);

		return "welcome"; // view
	}

	// /hello?name=kotlin
	@GetMapping("/hello")
	public String mainWithParam(
			@RequestParam(name = "name", required = false, defaultValue = "") String name,
			Model model) {

		model.addAttribute("message", name);

		return "welcome"; // view
	}

}
