package com.dawi.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProyectoController {
	@GetMapping("/")
	public String principal() {
		return "index";
	}
}
