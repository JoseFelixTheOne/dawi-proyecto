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
import org.springframework.web.bind.annotation.SessionAttributes;

import com.dawi.models.Menu;
import com.dawi.models.Persona;
import com.dawi.models.Usuario;
import com.dawi.repository.IMenuRepository;
import com.dawi.repository.IUsuarioRepository;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@SessionAttributes("iidtipousuario")
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
		int exito=encontroUsuario(usuario,hashSHA256(contra));
		if(exito!=0) {
	        model.addAttribute("iidtipousuario", exito+"");
	        String valor = (String) model.getAttribute("iidtipousuario"); 
	        System.out.println("----------------------------------------------------------------"+valor);
			model.addAttribute("menus", listarMenus());
			return "index";
		}
		model.addAttribute("error","Usuario o contraseña incorrecta");
		return "/login";
	}
	
	@GetMapping("/logout")
	public String logoutUser(Model model) {
		return "login";
	}
	
	private int encontroUsuario(String nombreusuario,String contra) {
		int idusuario=0;
		var usuarios=usuarioRepo.findAll();
		for (Usuario usuario : usuarios) {
			if(usuario.getActivo_usu().equals("a") && usuario.getOpersona().getActivo_per().equals("a")
					&& usuario.getNom_usu().equals(nombreusuario) 
					&& usuario.getContra_usu().equals(contra)
					) {
				idusuario=usuario.getId_tipousu();break;
			}
		}
		return idusuario;
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
