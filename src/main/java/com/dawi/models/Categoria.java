package com.dawi.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tb_categoria")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Categoria {
	@Id
	private int id_cat;
	private String nom_cat;
	private String desc_cat;
	private String activo_cat;
	private String imagen_cat;
	

}
