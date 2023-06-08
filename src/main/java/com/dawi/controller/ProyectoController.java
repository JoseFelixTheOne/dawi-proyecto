package com.dawi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.dawi.models.Producto;
import com.dawi.repository.CategoriaRepository;
import com.dawi.repository.ProductoRepository;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class ProyectoController {
	@GetMapping("/")
	public String principal() {
		return "index";
	}
	
	@Autowired
	private ProductoRepository prodRepo;
	
	@Autowired
	private CategoriaRepository catRepo;
	
	@GetMapping("/cargar")
	public String cargarProd(Model model) {
		cargarCombos(model);
		model.addAttribute("lstProductos", prodRepo.findAll());
		return "crudproductos";
	}
	
	@PostMapping("/productos/mantenimiento")
	public String registrarProd(@ModelAttribute Producto producto, Model model, HttpServletRequest request, HttpServletResponse response) {
		System.out.println(producto);
		
		String accion =request.getParameter("accion");
		String mensaje = "";
		 switch (accion) {
	        case "registrar":

	            try {
	                prodRepo.save(producto);
	                mensaje = "Registro exitoso";
	            } catch (Exception e) {
	                mensaje = "Error al registrar";
	            }
	            break;
	            
	        case "actualizar":
	            try {
	                prodRepo.save(producto);
	                mensaje = "Actualización exitosa";
	            } catch(Exception e) {
	                mensaje = "Error al actualizar";
	            }
	            break;
	            
	        case "eliminar":
	            try {
	                prodRepo.delete(producto);
	                mensaje = "Producto eliminado exitosamente";
	            } catch (Exception e) {
	                mensaje = "Error al eliminar el producto";
	            }
	            break;
	    }
	    
	    model.addAttribute("mensaje", mensaje);
	    model.addAttribute("lstProductos", prodRepo.findAll());
	    return "crudproductos";
	}
	
	@PostMapping("/productos/actualizar")
	public String actualizarProd(@ModelAttribute Producto producto, Model model) {
		try {
			prodRepo.save(producto);
			model.addAttribute("mensaje", "Actualizacion exitosa");
		} catch(Exception e) {
			model.addAttribute("mensaje", "Error al actualizar");
		}
		
		return "crudproductos";
	}
	
	@PostMapping("/productos/eliminar")
	public String eliminarProd(@RequestParam("id") Producto producto, Model model) {
	    try {
	        prodRepo.delete(producto);
	        model.addAttribute("mensaje", "Producto eliminado exitosamente");
	    } catch (Exception e) {
	        model.addAttribute("mensaje", "Error al eliminar el producto");
	    }

	    return "crudproductos"; // Redirige a la página de CRUD de productos
	}


	void cargarCombos(Model model) {
		model.addAttribute("lstCategorias", catRepo.findAll());
		model.addAttribute("producto", new Producto());
	}
}
