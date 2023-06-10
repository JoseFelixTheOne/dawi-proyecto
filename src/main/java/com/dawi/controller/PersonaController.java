package com.dawi.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.dawi.models.Persona;
import com.dawi.models.Producto;
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
		model.addAttribute("persona", new Persona());
		return "crudpersona";
	}
	
	@PostMapping("/personaeliminar")
	public String eliminarPersona(@RequestParam("id") int id, Model model) {
		Persona opersona= personaRepo.findById(id).orElse(new Persona());
		opersona.setActivo_per("d");
		personaRepo.save(opersona);
		model.addAttribute("persona", new Persona());
		model.addAttribute("personas",listarPersonas());
		return "crudpersona";
	}
	
	@PostMapping("/persona")
	public String guardarPersona(@ModelAttribute Persona persona,Model model) {
		String mensaje="";
		persona.setActivo_per("a");
        try {
        	personaRepo.save(persona);
            mensaje = "Registro exitosa";
        } catch(Exception e) {
            mensaje = "Error al guardar";
        }
		model.addAttribute("personas",listarPersonas());
        model.addAttribute("mensaje", mensaje);

		return "crudpersona";
	}
	
	
	
}
