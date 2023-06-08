package com.dawi.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "tb_categoria")
@Data
public class Categoria {
	@Id
	private int id_cat;
	private String nom_cat;
	private String desc_cat;
	private String activo_cat;
	private String imagen_cat;
	
	
	public Categoria(int id_cat, String nom_cat, String desc_cat, String activo_cat, String imagen_cat) {
		super();
		this.id_cat = id_cat;
		this.nom_cat = nom_cat;
		this.desc_cat = desc_cat;
		this.activo_cat = activo_cat;
		this.imagen_cat = imagen_cat;
	}


	public Categoria() {
		super();
		// TODO Auto-generated constructor stub
	}


	@Override
	public String toString() {
		return "Categoria [id_cat=" + id_cat + ", nom_cat=" + nom_cat + ", desc_cat=" + desc_cat + ", activo_cat="
				+ activo_cat + ", imagen_cat=" + imagen_cat + "]";
	}


	public int getId_cat() {
		return id_cat;
	}


	public void setId_cat(int id_cat) {
		this.id_cat = id_cat;
	}


	public String getNom_cat() {
		return nom_cat;
	}


	public void setNom_cat(String nom_cat) {
		this.nom_cat = nom_cat;
	}


	public String getDesc_cat() {
		return desc_cat;
	}


	public void setDesc_cat(String desc_cat) {
		this.desc_cat = desc_cat;
	}


	public String getActivo_cat() {
		return activo_cat;
	}


	public void setActivo_cat(String activo_cat) {
		this.activo_cat = activo_cat;
	}


	public String getImagen_cat() {
		return imagen_cat;
	}


	public void setImagen_cat(String imagen_cat) {
		this.imagen_cat = imagen_cat;
	}
	
	
}
