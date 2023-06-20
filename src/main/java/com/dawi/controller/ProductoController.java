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

import com.dawi.models.Menu;
import com.dawi.models.Producto;
import com.dawi.repository.CategoriaRepository;
import com.dawi.repository.IMenuRepository;
import com.dawi.repository.ProductoRepository;

@Controller
public class ProductoController {

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
	private ProductoRepository prodRepo;

	@Autowired
	private CategoriaRepository catRepo;

//	@GetMapping("/cargar")
//	public String cargarProd(Model model) {
//		cargarCombos(model);
//		model.addAttribute("lstProductos", prodRepo.findAll());
//		return "crudproductos";
//	}
//
//	@PostMapping("/productos/mantenimiento")
//	public String registrarProd(@ModelAttribute Producto producto, Model model, HttpServletRequest request, HttpServletResponse response, @RequestParam("imagen") MultipartFile imagen) {
//		System.out.println(producto);
//
//
//		String accion =request.getParameter("accion");
//		String mensaje = "";
//		 switch (accion) {
//	        case "registrar":
//
//	            try {
//	            	 if (!imagen.isEmpty()) {
//	                     byte[] datosImagen = imagen.getBytes();
//	                     producto.setImagen_prod(datosImagen);
//	                 }
//	                prodRepo.save(producto);
//	                mensaje = "Registro exitoso";
//	            } catch (Exception e) {
//	                mensaje = "Error al registrar";
//	            }
//	            break;
//
//	        case "actualizar":
//	            try {
//	                prodRepo.save(producto);
//	                mensaje = "Actualización exitosa";
//	            } catch(Exception e) {
//	                mensaje = "Error al actualizar";
//	            }
//	            break;
//
//	        case "eliminar":
//	            try {
//	                prodRepo.delete(producto);
//	                mensaje = "Producto eliminado exitosamente";
//	            } catch (Exception e) {
//	                mensaje = "Error al eliminar el producto";
//	            }
//	            break;
//	    }
//
//	    model.addAttribute("mensaje", mensaje);
//	    model.addAttribute("lstProductos", prodRepo.findAll());
//	    return "crudproductos";
//	}

	private ArrayList<Producto> lstProductos() {
		ArrayList<Producto> lista = new ArrayList<>();
		var productos = prodRepo.findAll();
		for (Producto producto : productos) {
			if(producto.getActivo_prod().equals("a")) {
				lista.add(producto);
			}
		}
		return lista;
	}

	@GetMapping("/producto")
	public String paginaproducto(Model model) {
		cargarCombos(model);
		model.addAttribute("productos", lstProductos());
		model.addAttribute("producto", new Producto());
		model.addAttribute("menus", listarMenus());
		return "crudproductos";
	}

	@PostMapping("/producto/eliminar")
	public String eliminarProducto(@RequestParam("id") int id, Model model, RedirectAttributes redirectAttributes) {
		Producto producto = prodRepo.findById(id).orElse(new Producto());
		producto.setActivo_prod("d");
		prodRepo.save(producto);
		model.addAttribute("producto", new Producto());
		model.addAttribute("productos", lstProductos());
		model.addAttribute("menus", listarMenus());
		return "redirect:/crud/producto";
	}

	@PostMapping("/crud/producto/guardar")
	public String guardarProducto(@ModelAttribute Producto producto, Model model, RedirectAttributes redirectAttributes) {
		String mensaje = "";
		producto.setActivo_prod("a");
		try {
			prodRepo.save(producto);
			mensaje = "Registro exitoso";
		}catch(Exception e) {
			mensaje = "Error al guardar";
		}
		model.addAttribute("lstCategorias", catRepo.findAll());
		model.addAttribute("productos", lstProductos());
		model.addAttribute("mensaje", mensaje);
		model.addAttribute("menus", listarMenus());
		redirectAttributes.addFlashAttribute("mensaje", mensaje);
		return "redirect:/crud/producto";
	}

	void cargarCombos(Model model) {
		model.addAttribute("lstCategorias", catRepo.findAll());
		model.addAttribute("producto", new Producto());
	}
}
