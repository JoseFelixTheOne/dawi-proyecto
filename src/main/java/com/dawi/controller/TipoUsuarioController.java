package com.dawi.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

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
	public String grudTipoUsuario(Model model) {
		System.out.println("Entro al metodo");
		model.addAttribute("tipos", listarTiposUsuario());
		model.addAttribute("tipo", new TipoUsuario());
		return "crudtipo";
	}
}
