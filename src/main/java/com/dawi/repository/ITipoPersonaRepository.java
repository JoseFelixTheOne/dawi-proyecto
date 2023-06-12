package com.dawi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dawi.models.TipoUsuario;

public interface ITipoPersonaRepository extends JpaRepository<TipoUsuario, Integer>{

}
