package com.dawi.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;import com.dawi.models.Menu;
import com.dawi.repository.IMenuRepository;

@Controller
public class MenuController {
	
	@Autowired
	private IMenuRepository menuRepo;
	
	private String[] iconList = {"fa-solid fa-house","fa-solid fa-magnifying-glass","fa-solid fa-user","fa-solid fa-file-chart-column","fa-solid fa-arrow-right-from-bracket","fa-solid fa-gear","fa-solid fa-person","fa-solid fa-square-list","fa-solid fa-user-plus","fa-solid fa-user-tag","fa-solid fa-grid-2-plus","fa-solid fa-tag","fa-solid fa-box"};

	private ArrayList<Menu> listarMenus(){
		ArrayList<Menu> lista = new ArrayList<Menu>();
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
	
	@GetMapping("/menu")
	public String cargarMenu(Model model) {
		model.addAttribute("menus", listarMenus());
		model.addAttribute("menu", new Menu());
		model.addAttribute("iconos", iconList);
		return "crudmenu";
	}
}
