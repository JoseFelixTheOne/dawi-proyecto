package com.dawi.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "tb_usuario")
public class Usuario {

	@Id
	private int id_usu;
	private String nom_usu;
	private String contra_usu;
	private int id_per;
	private int id_tipousu;
	private String activo_usu;

	@ManyToOne
	@JoinColumn(name = "id_per",insertable = false,updatable = false)
    private Persona opersona;
	@ManyToOne
	@JoinColumn(name = "id_tipousu",insertable = false,updatable = false)
    private TipoUsuario otipousuario;

}
