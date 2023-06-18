package com.dawi.controller;

import java.io.OutputStream;
import java.util.HashMap;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

@Controller
public class ReporteController {

	
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
	
}
