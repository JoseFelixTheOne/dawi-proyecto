package com.dawi.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "tb_tipousuario")
@Data
public class TipoUsuario {
	@Id
	private int id_tipousu;
	private String des_tipousu;
	private String activo_tipousu;
	private String nom_tipousu;
}
