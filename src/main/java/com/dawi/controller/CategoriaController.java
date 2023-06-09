package com.dawi.controller;


import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.dawi.models.Categoria;
import com.dawi.models.Menu;
import com.dawi.repository.CategoriaRepository;
import com.dawi.repository.IMenuRepository;

@Controller
public class CategoriaController {
	@Autowired
	private IMenuRepository menuRepo;

	@Autowired
	private CategoriaRepository catRepo;

	private ArrayList<Categoria> lstCategorias() {
		ArrayList<Categoria> lista = new ArrayList<>();
		var categorias = catRepo.findAll();
		for (Categoria categoria : categorias) {
			if (categoria.getActivo_cat().equals("a")) {
				lista.add(categoria);
			}
		}
		return lista;
	}
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
	
	@GetMapping("/categoria")
	public String paginacategoria(Model model) {
		model.addAttribute("categorias", lstCategorias());
		model.addAttribute("menus", listarMenus());
		model.addAttribute("categoria", new Categoria());
		model.addAttribute("menus", listarMenus());
		return "crudcategoria";
	}
	
	@PostMapping("/categoria/eliminar")
	public String eliminarCategoria(@RequestParam("id") int id, Model model, RedirectAttributes redirectAttributes) {
		Categoria categoria = catRepo.findById(id).orElse(new Categoria());
		categoria.setActivo_cat("d");
		catRepo.save(categoria);
		model.addAttribute("categoria", new Categoria());
		model.addAttribute("menus", listarMenus());
		model.addAttribute("categorias", lstCategorias());
		model.addAttribute("menus", listarMenus());
		return "redirect:/categoria";
	}
	
	@PostMapping("/crud/categoria/guardar")
	public String guardarCategoria(@ModelAttribute Categoria categoria, Model model, RedirectAttributes redirectAttributes) {
		String mensaje = "";
		categoria.setActivo_cat("a");
		try {
			catRepo.save(categoria);
			mensaje = "Registro exitoso";
		}catch(Exception e) {
			mensaje = "Error al guardar";
		}
		model.addAttribute("categorias", lstCategorias());
		model.addAttribute("menus", listarMenus());
		model.addAttribute("mensaje", mensaje);
		model.addAttribute("menus", listarMenus());
		redirectAttributes.addFlashAttribute("mensaje", mensaje);
		return "redirect:/categoria";
	}
	

}

