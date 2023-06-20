package com.dawi.controller;

import java.io.OutputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.dawi.models.Categoria;
import com.dawi.models.Menu;
import com.dawi.repository.CategoriaRepository;
import com.dawi.repository.IMenuRepository;

import jakarta.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

@Controller
public class ReporteController {

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
	
	@GetMapping("/reportes")
	public String cargarPagReporte() {
		return "reportes";
	}

	
//Reporte todo los productos
	@Autowired
	private DataSource dataSource; // javax.sql
	@Autowired
	private ResourceLoader resourceLoader; // core.io
	@GetMapping("/reports/listado")
	public void generarPDFListado(HttpServletResponse response) {
		response.setHeader("Content-Disposition", "attachment; filename=\"reporte.pdf\";");
		response.setHeader("Content-Disposition", "inline;");
		response.setContentType("application/pdf");
		try {
		String ru = resourceLoader.getResource("classpath:reportes/reporte_persona.jasper").getURI().getPath();
		JasperPrint jasperPrint = JasperFillManager.fillReport(ru, null, dataSource.getConnection());
		OutputStream outStream = response.getOutputStream();
		JasperExportManager.exportReportToPdfStream(jasperPrint, outStream);
		} catch (Exception e) {
		e.printStackTrace();
		}

	}
	
	
	@PostMapping("/reports/filtro")
	public void generarPDFListadoFiltro(HttpServletResponse response,@RequestParam(name="txtnombrecompleto") String valor) {
		response.setHeader("Content-Disposition", "attachment; filename=\"reporte.pdf\";");
		response.setContentType("application/pdf");
		try {
		String ru = resourceLoader.getResource("classpath:reportes/reporte_persona_filtro.jasper").getURI().getPath();
		HashMap parametros = new HashMap<>();
		parametros.put("p_nombrecompleto", valor);
		// la categoria se obtendrá del combo
		JasperPrint jasperPrint = JasperFillManager.fillReport(ru, parametros, dataSource.getConnection());
		OutputStream outStream = response.getOutputStream();
		JasperExportManager.exportReportToPdfStream(jasperPrint, outStream);
		} catch (Exception e) {
		e.printStackTrace();
		}

	}
	
	
	@PostMapping("/reports/filtrousuario")
	public void generarPDFListadoFiltroUsu(HttpServletResponse response,@RequestParam(name="txtnombreusuario") String valor) {
		response.setHeader("Content-Disposition", "attachment; filename=\"reporte.pdf\";");
		response.setContentType("application/pdf");
		
	
		try {
		String ru = resourceLoader.getResource("classpath:reportes/reporte_usuario_persona.jasper").getURI().getPath();
		HashMap parametros = new HashMap<>();
		parametros.put("p_nombreusuario", valor);
		// la categoria se obtendrá del combo
		JasperPrint jasperPrint = JasperFillManager.fillReport(ru, parametros, dataSource.getConnection());
		OutputStream outStream = response.getOutputStream();

		JasperExportManager.exportReportToPdfStream(jasperPrint, outStream);
		} catch (Exception e) {
		e.printStackTrace();
		}

	}
	
	//categoria
	
	@GetMapping("/consultas/categoria")
	public String cargarConCategoria(Model model) {
		model.addAttribute("categoria", new Categoria());
		model.addAttribute("menus", listarMenus());
		return "reportecategoria";
	}
	
	@PostMapping("/reports/categoria")
	public void filtroCat(HttpServletResponse response, @ModelAttribute Categoria categoria) {
		response.setHeader("Content-Disposition", "inline;"); 
		response.setContentType("application/pdf");
		System.out.println(categoria);

		Connection connection = null;
		try {
			connection = dataSource.getConnection();
			String ru = resourceLoader.getResource("classpath:reportes/reporte_categoria.jasper").getURI().getPath();
			// HashMap --> otro tipo de coleccion
			HashMap parametros = new HashMap<>();
			parametros.put("Estado", categoria.getActivo_cat());
			
			JasperPrint jasperPrint = JasperFillManager.fillReport(ru, parametros, connection);
			OutputStream outStream = response.getOutputStream();
			JasperExportManager.exportReportToPdfStream(jasperPrint, outStream);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
		    // Cerrar la conexión
		    if (connection != null) {
		        try {
		            connection.close();
		        } catch (SQLException e) {
		            // Manejar excepciones al cerrar la conexión
		        }
		    }
		}
		
	}
	
	@GetMapping("/reports/prodxcat")
	public void consultarProdXCat(HttpServletResponse response) {
		response.setHeader("Content-Disposition", "inline;"); 
		response.setContentType("application/pdf");

		try {
			String ru = resourceLoader.getResource("classpath:reportes/grafico_categoria.jasper").getURI().getPath();
			JasperPrint jasperPrint = JasperFillManager.fillReport(ru, null, dataSource.getConnection());
			OutputStream outStream = response.getOutputStream();
			JasperExportManager.exportReportToPdfStream(jasperPrint, outStream);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	
	
}
