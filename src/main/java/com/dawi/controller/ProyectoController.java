package com.dawi.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.dawi.models.Menu;
import com.dawi.repository.IMenuRepository;

@Controller
@SessionAttributes("iidusuario")
public class ProyectoController {
	
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
	
	@GetMapping("/home")
	public String principal(Model model) {
        //String valor = (String) model.getAttribute("nombreAtributo"); 
        //System.out.println("----------------------------------------------------------------"+valor);
		model.addAttribute("menus", listarMenus());
		return "index";
	}

	

}
