package com.dawi.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.dawi.models.Persona;
import com.dawi.models.TipoUsuario;
import com.dawi.models.Usuario;
import com.dawi.repository.IPersonaRepository;
import com.dawi.repository.ITipoPersonaRepository;
import com.dawi.repository.IUsuarioRepository;

@Controller
public class UsuarioController {

	
	@Autowired
	private IUsuarioRepository usuarioRepo;
	@Autowired
	private IPersonaRepository personaRepo;
	@Autowired
	private ITipoPersonaRepository tipoUsuRepo;

	
	@GetMapping("/usuario")
	public String cargar(Model model) {
		TipoUsuarioController oTipoUsuarioController=new TipoUsuarioController();
		PersonaController oPersonaController=new PersonaController();
		model.addAttribute("usuarios",listarUsuarios());
		model.addAttribute("usuario", new Usuario());
		model.addAttribute("personas", listarPersonas());
		model.addAttribute("tipousuarios", listarTiposUsuario());

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
	
	
}
