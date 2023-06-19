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
import com.dawi.repository.IPersonaRepository;

@Controller
public class PersonaController {

	@Autowired
	private IPersonaRepository personaRepo;


	private ArrayList<Persona> listarPersonas() {
		ArrayList<Persona> lista=new ArrayList<> ();
		var personas=personaRepo.findAll();
		for (Persona persona : personas) {
			if(persona.getActivo_per().equals("a")) {
				lista.add(persona);
			}
		}
		return lista;
	}
	
	private ArrayList<Persona> filtrarPersonas(String nombrecompleto) {
		ArrayList<Persona> lista=new ArrayList<Persona> ();
		var personas=personaRepo.findAll();
		for (Persona persona : personas) {
			if(persona.getActivo_per().equals("a")  && 
					(persona.getNom_per()+" "+persona.getApepat_per()+" "+persona.getApemat_per())
					.toUpperCase().contains(nombrecompleto.toUpperCase())
					) {
				lista.add(persona);
			}
		}
		return lista;
	}
	
	@PostMapping("/persona/filtro")
	public String paginapersonaFiltro(Model model,@RequestParam(name = "txtnombrecompletofiltro") String nombrecompleto) {
		model.addAttribute("personas",filtrarPersonas(nombrecompleto));
		model.addAttribute("persona", new Persona());
		model.addAttribute("personabusqueda", nombrecompleto);
		return "crudpersona";
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
        	if(persona.getId_per()==0) {
        		persona.setBtieneusuario(0);
            	personaRepo.save(persona);
        	}else {
        		Persona opersona= personaRepo.findById(persona.getId_per()).orElse(new Persona());
        		opersona.setNom_per(persona.getNom_per());
        		opersona.setApepat_per(persona.getApepat_per());
        		opersona.setApemat_per(persona.getApemat_per());
        		opersona.setCorreo_per(persona.getCorreo_per());
        		opersona.setDir_per(persona.getDir_per());
        		opersona.setFlagcliente_per(persona.getFlagcliente_per());
            	personaRepo.save(opersona);
        	}
            mensaje = "Registro exitosa";
        } catch(Exception e) {
            mensaje = "Error al guardar";
        }
		model.addAttribute("personas",listarPersonas());
        model.addAttribute("mensaje", mensaje);

		return "crudpersona";
	}



}
