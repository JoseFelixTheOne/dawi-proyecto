package com.dawi.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.dawi.models.TipoUsuario;
import com.dawi.repository.ITipoPersonaRepository;

@Controller
public class TipoUsuarioController {
	
	@Autowired
	private ITipoPersonaRepository tipoUsuRepo;
	
	private ArrayList<TipoUsuario> listarTiposUsuario(){
		ArrayList<TipoUsuario> lista = new ArrayList<TipoUsuario>();
		try {
			var tipos = tipoUsuRepo.findAll();
			for(TipoUsuario tipo: tipos) {
				if(tipo.getActivo_tipousu().equals("a"))
					lista.add(tipo);
			}
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
		return lista;
	}
	@GetMapping("/tipousuario")
	public String crudTipoUsuario(Model model) {
		model.addAttribute("tipos", listarTiposUsuario());
		model.addAttribute("tipo", new TipoUsuario());
		return "crudtipo";
	}
	@PostMapping("/tipousuario")
	public String guardarTipo(@ModelAttribute TipoUsuario tipo ,Model model) {
		String mensaje = "";
		tipo.setActivo_tipousu("a");
		try {
			tipoUsuRepo.save(tipo);
			mensaje = "Registro exitoso";
			
		} catch (Exception e) {
			mensaje = "Error al guardar";
		}
		model.addAttribute("tipos", listarTiposUsuario());
		model.addAttribute("tipo", new TipoUsuario());
		model.addAttribute("mensaje", mensaje);
		return "crudtipo";
	}
}
