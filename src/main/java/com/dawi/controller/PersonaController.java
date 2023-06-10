package com.dawi.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.dawi.models.Persona;
import com.dawi.repository.IPersonaRepository;
import com.dawi.repository.ProductoRepository;

@Controller
public class PersonaController {

	@Autowired
	private IPersonaRepository personaRepo;
	
	
	private ArrayList<Persona> listarPersonas() {
		ArrayList<Persona> lista=new ArrayList<Persona> ();
		var personas=personaRepo.findAll();
		for (Persona persona : personas) {
			if(persona.getActivo_per().equals("a")) {
				lista.add(persona);
			}
		}
		return lista;
	}
	
	@GetMapping("/persona")
	public String paginapersona(Model model) {
		
		model.addAttribute("personas",listarPersonas());
		return "crudpersona";
	}
	
	
	
	
}
