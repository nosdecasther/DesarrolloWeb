package com.desarrolloweb.spring.app.repositories;
import org.springframework.data.repository.CrudRepository;

import com.desarrolloweb.spring.app.entities.Usuario;

public interface UserRepository extends CrudRepository<Usuario, Long>{

	public Usuario findByUsername(String username);
}
