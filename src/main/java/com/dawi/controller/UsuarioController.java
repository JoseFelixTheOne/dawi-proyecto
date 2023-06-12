package com.dawi.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.dawi.models.Persona;
import com.dawi.models.Usuario;
import com.dawi.repository.IPersonaRepository;
import com.dawi.repository.IUsuarioRepository;

@Controller
public class UsuarioController {

	
	@Autowired
	private IUsuarioRepository usuarioRepo;
	
	
	@GetMapping("/usuario")
	public String cargar(Model model) {
		model.addAttribute("usuarios",listarUsuarios());
		model.addAttribute("usuario", new Usuario());
		return "crudusuario";
	}
	
	private ArrayList<Usuario> listarUsuarios() {
		ArrayList<Usuario> lista=new ArrayList<Usuario> ();
		var usuarios=usuarioRepo.findAll();
		for (Usuario usuario : usuarios) {
			if(usuario.getActivo_usu().equals("a")) {
				lista.add(usuario);
			}
		}
		return lista;
	}
	
	
}
