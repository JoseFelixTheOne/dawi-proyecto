package com.dawi.controller;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.dawi.models.Menu;
import com.dawi.models.Persona;
import com.dawi.models.Usuario;
import com.dawi.repository.IMenuRepository;
import com.dawi.repository.IUsuarioRepository;

@Controller
public class LoginController {

	
	@Autowired
	private IUsuarioRepository usuarioRepo;
	
	@GetMapping("/")
	public String login(Model model) {
		model.addAttribute("error","");
		return "login";
	}
	
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
	
	
	@PostMapping("/login")
	public String loginUser(@RequestParam(name = "usuario")String usuario ,
			@RequestParam(name = "contra")String contra , Model model) {
		boolean exito=encontroUsuario(usuario,hashSHA256(contra));
		if(exito==true) {
			model.addAttribute("menus", listarMenus());
			return "index";
		}
		model.addAttribute("error","Usuario o contraseña incorrecta");
		return "/login";
	}
	
	private boolean encontroUsuario(String nombreusuario,String contra) {
		boolean exito=false;
		var usuarios=usuarioRepo.findAll();
		for (Usuario usuario : usuarios) {
			if(usuario.getActivo_usu().equals("a") && usuario.getOpersona().getActivo_per().equals("a")
					&& usuario.getNom_usu().equals(nombreusuario) 
					&& usuario.getContra_usu().equals(contra)
					) {
				exito=true;break;
			}
		}
		return exito;
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
