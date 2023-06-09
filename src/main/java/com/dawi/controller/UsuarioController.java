package com.dawi.controller;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.dawi.models.Menu;
import com.dawi.models.Persona;
import com.dawi.models.TipoUsuario;
import com.dawi.models.Usuario;
import com.dawi.repository.IMenuRepository;
import com.dawi.repository.IPersonaRepository;
import com.dawi.repository.ITipoPersonaRepository;
import com.dawi.repository.IUsuarioRepository;

@Controller
public class UsuarioController {

	@Autowired
	private IMenuRepository menuRepo;
	
	private ArrayList<Menu> listarMenus(){
		ArrayList<Menu> lista = new ArrayList<>();
		try {
			var menus = menuRepo.findAll();
			for(Menu menu : menus) {
				if(menu.getActivo_menu().equals("a"))
					lista.add(menu);
			}
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
		return lista;
	}


	@Autowired
	private IUsuarioRepository usuarioRepo;
	@Autowired
	private IPersonaRepository personaRepo;
	@Autowired
	private ITipoPersonaRepository tipoUsuRepo;

	
	private ArrayList<Usuario> filtrarUsuarios(String nombreusuario) {
		ArrayList<Usuario> lista=new ArrayList<Usuario> ();
		var usuarios=usuarioRepo.findAll();
		for (Usuario usuario : usuarios) {
			if(usuario.getActivo_usu().equals("a") && usuario.getOpersona().getActivo_per().equals("a")
					&& usuario.getNom_usu().contains(nombreusuario)
					) {
				lista.add(usuario);
			}
		}
		return lista;
	}
	
	@PostMapping("/usuario/filtro")
	public String paginapersonaFiltro(Model model,@RequestParam(name = "txtnombreusuario") String nombreusuario) {
		model.addAttribute("usuariobusqueda", nombreusuario);
		model.addAttribute("usuarios",filtrarUsuarios(nombreusuario));
		model.addAttribute("usuario", new Usuario());
		model.addAttribute("personas", listarPersonas());
		model.addAttribute("menus", listarMenus());
		return "crudusuario";
	}
	
	@GetMapping("/usuario")
	public String cargar(Model model) {
		TipoUsuarioController oTipoUsuarioController=new TipoUsuarioController();
		PersonaController oPersonaController=new PersonaController();
		model.addAttribute("usuarios",listarUsuarios());
		model.addAttribute("usuario", new Usuario());
		model.addAttribute("personas", listarPersonas());
		model.addAttribute("tipousuarios", listarTiposUsuario());
		model.addAttribute("menus", listarMenus());
		return "crudusuario";
	}

	private ArrayList<Usuario> listarUsuarios() {
		ArrayList<Usuario> lista=new ArrayList<> ();
		var usuarios=usuarioRepo.findAll();
		for (Usuario usuario : usuarios) {
			if(usuario.getActivo_usu().equals("a") && usuario.getOpersona().getActivo_per().equals("a")) {
				lista.add(usuario);
			}
		}
		return lista;
	}

	private ArrayList<Persona> listarPersonas() {
		ArrayList<Persona> lista=new ArrayList<> ();
		var personas=personaRepo.findAll();
		for (Persona persona : personas) {
			if(persona.getActivo_per().equals("a") && persona.getBtieneusuario()==0 ) {
				lista.add(persona);
			}
		}
		return lista;
	}

	private ArrayList<TipoUsuario> listarTiposUsuario(){
		ArrayList<TipoUsuario> lista = new ArrayList<>();
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


	@PostMapping("/usuarioeliminar")
	public String eliminarUsuario(@RequestParam("id") int id, Model model) {
		System.out.println("Id usuario:"+id);
		Usuario ousuario= usuarioRepo.findById(id).orElse(new Usuario());
		ousuario.setActivo_usu("d");
		usuarioRepo.save(ousuario);
		model.addAttribute("usuarios",listarUsuarios());
		model.addAttribute("usuario", new Usuario());
		model.addAttribute("personas", listarPersonas());
		model.addAttribute("tipousuarios", listarTiposUsuario());
		model.addAttribute("menus", listarMenus());
		return "crudusuario";
	}

	@PostMapping("/usuarioguardar")
	public String guardarUsuario(@ModelAttribute Usuario usuario,Model model) {
		String mensaje="";
		usuario.setActivo_usu("a");
		try {
        	if(usuario.getId_usu()==0) {
        		String contra= hashSHA256(usuario.getContra_usu());
        		usuario.setContra_usu(contra);
            	usuarioRepo.save(usuario);
        		Persona opersona= personaRepo.findById(usuario.getId_per()).orElse(new Persona());
        		opersona.setBtieneusuario(1);
        		personaRepo.save(opersona);

        	}else {
        		Usuario ousuario= usuarioRepo.findById(usuario.getId_usu()).orElse(new Usuario());
        		ousuario.setNom_usu(usuario.getNom_usu());
        		ousuario.setId_tipousu(usuario.getId_tipousu());
        		System.out.println("Id tipo usuario "+usuario.getId_tipousu());
            	usuarioRepo.save(ousuario);
        	}
            mensaje = "Registro exitosa";
        } catch(Exception e) {
            mensaje = "Error al guardar";
        }
		model.addAttribute("usuario", new Usuario());
		model.addAttribute("personas", listarPersonas());
		model.addAttribute("tipousuarios", listarTiposUsuario());
    	model.addAttribute("usuarios",listarUsuarios());
    	model.addAttribute("menus", listarMenus());
		return "crudusuario";
	}


	  public String hashSHA256(String input) {
	        try {
	            MessageDigest digest = MessageDigest.getInstance("SHA-256");
	            byte[] encodedHash = digest.digest(input.getBytes(StandardCharsets.UTF_8));

	            StringBuilder hexString = new StringBuilder();
	            for (byte b : encodedHash) {
	                String hex = Integer.toHexString(0xff & b);
	                if (hex.length() == 1) {
	                    hexString.append('0');
	                }
	                hexString.append(hex);
	            }
	            return hexString.toString();
	        } catch (NoSuchAlgorithmException e) {
	            e.printStackTrace();
	        }
	        return null;
	    }



}
