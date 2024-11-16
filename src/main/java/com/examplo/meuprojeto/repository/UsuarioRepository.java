package com.examplo.meuprojeto.repository;

import com.examplo.meuprojeto.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Repository
@Component
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

}
