package com.dawi.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.dawi.models.Menu;
import com.dawi.repository.IMenuRepository;

@Controller
public class MenuController {

	@Autowired
	private IMenuRepository menuRepo;

	private String[] iconList = {"fa-solid fa-house","fa-solid fa-magnifying-glass","fa-solid fa-user","fa-solid fa-file-pdf","fa-solid fa-arrow-right-from-bracket","fa-solid fa-gear","fa-solid fa-person","fa-solid fa-list-ul","fa-solid fa-user-plus","fa-solid fa-user-tag","fa-solid fa-bars","fa-solid fa-tag","fa-solid fa-box"};

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

	@GetMapping("/menu")
	public String cargarMenu(Model model) {
		envioAtributos(model);
		return "crudmenu";
	}
	@PostMapping("/menu")
	public String registrarMenu(@ModelAttribute Menu menu ,Model model) {
		String mensaje = "";
		menu.setActivo_menu("a");
		try {
			menuRepo.save(menu);
			mensaje = "Registro exitoso";

		} catch (Exception e) {
			mensaje = "Error al guardar";
		}

		envioAtributos(model);
		model.addAttribute("mensaje", mensaje);
		return "crudmenu";
	}
	@PostMapping("/menu/eliminar")
	public String eliminarMenu(@RequestParam("id") int id, Model model) {
		Menu xmenu = menuRepo.findById(id).orElse(new Menu());
		xmenu.setActivo_menu("i");
		try {
			menuRepo.save(xmenu);
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
		envioAtributos(model);
		return "crudmenu";
	}

	//metodos comunes
	public void envioAtributos(Model model) {
		model.addAttribute("menus", listarMenus());
		model.addAttribute("menu", new Menu());
		model.addAttribute("iconos", iconList);
	}
}
