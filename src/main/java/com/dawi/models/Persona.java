package com.dawi.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name ="tb_persona" )
@Data
public class Persona {
	
	@Id
	
	private int id_per;
	
	private String nom_per;
	
	private String apepat_per;
	
	private String apemat_per;
	
	private String correo_per;
	
	private String dir_per;
	
	private String activo_per;
	
	private String flagcliente_per;

	private int btieneusuario;


}
