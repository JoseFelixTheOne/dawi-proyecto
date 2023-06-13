package com.dawi.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "tb_menu")
@Data
public class Menu {
	@Id
	private int id_menu;
	private String nombreOpcion_menu;
	private String iconoOpcion_menu;
	private String activo_menu;
}
