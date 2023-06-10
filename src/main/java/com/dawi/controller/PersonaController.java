package com.dawi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.dawi.repository.IPersonaRepository;
import com.dawi.repository.ProductoRepository;

@Controller
public class PersonaController {

	@Autowired
	private IPersonaRepository personaRepo;
	
	@GetMapping("/persona")
	public String paginapersona(Model model) {
		var personas=personaRepo.findAll();
		model.addAttribute("personas",personas);
		return "crudpersona";

	}
	
}
