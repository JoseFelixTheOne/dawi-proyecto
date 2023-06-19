package com.dawi.models;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tb_producto")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Producto {
	@Id
	private int id_prod;
	private String nom_prod;
	private double prec_prod;
	private int stock_prod;
	private int id_cat;
	private String activo_prod;
	private byte[] imagen_prod;

	@ManyToOne
	@JoinColumn(name = "id_cat", insertable = false, updatable = false)
	private Categoria objCate;


}
