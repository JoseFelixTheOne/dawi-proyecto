package com.dawi.models;



import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "tb_producto")
@Data
public class Producto {
	@Id
	private int id_prod;
	private String nom_prod;
	private double prec_prod;
	private int stock_prod;
	private int id_cat;
	private String activo_prod;
	private String imagen_prod;
	
	@ManyToOne
	@JoinColumn(name = "id_cat", insertable = false, updatable = false)
	private Categoria objCate;

	public Producto(int id_prod, String nom_prod, double prec_prod, int stock_prod, int id_cat, String activo_prod,
			String imagen_prod, Categoria objCate) {
		super();
		this.id_prod = id_prod;
		this.nom_prod = nom_prod;
		this.prec_prod = prec_prod;
		this.stock_prod = stock_prod;
		this.id_cat = id_cat;
		this.activo_prod = activo_prod;
		this.imagen_prod = imagen_prod;
		this.objCate = objCate;
	}

	public Producto() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "Producto [id_prod=" + id_prod + ", nom_prod=" + nom_prod + ", prec_prod=" + prec_prod + ", stock_prod="
				+ stock_prod + ", id_cat=" + id_cat + ", activo_prod=" + activo_prod + ", imagen_prod=" + imagen_prod
				+ ", objCate=" + objCate + "]";
	}

	public int getId_prod() {
		return id_prod;
	}

	public void setId_prod(int id_prod) {
		this.id_prod = id_prod;
	}

	public String getNom_prod() {
		return nom_prod;
	}

	public void setNom_prod(String nom_prod) {
		this.nom_prod = nom_prod;
	}

	public double getPrec_prod() {
		return prec_prod;
	}

	public void setPrec_prod(double prec_prod) {
		this.prec_prod = prec_prod;
	}

	public int getStock_prod() {
		return stock_prod;
	}

	public void setStock_prod(int stock_prod) {
		this.stock_prod = stock_prod;
	}

	public int getId_cat() {
		return id_cat;
	}

	public void setId_cat(int id_cat) {
		this.id_cat = id_cat;
	}

	public String getActivo_prod() {
		return activo_prod;
	}

	public void setActivo_prod(String activo_prod) {
		this.activo_prod = activo_prod;
	}

	public String getImagen_prod() {
		return imagen_prod;
	}

	public void setImagen_prod(String imagen_prod) {
		this.imagen_prod = imagen_prod;
	}

	public Categoria getObjCate() {
		return objCate;
	}

	public void setObjCate(Categoria objCate) {
		this.objCate = objCate;
	}
	
	
	
	
}
