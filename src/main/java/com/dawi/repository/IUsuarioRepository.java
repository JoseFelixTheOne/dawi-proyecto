package com.dawi.repository;

import org.springframework.data.repository.CrudRepository;

import com.dawi.models.Usuario;

public interface IUsuarioRepository extends CrudRepository<Usuario, Integer> {

}
