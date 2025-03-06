package com.esgurg.gym.repository;

import com.esgurg.gym.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import com.esgurg.gym.entity.security.Rol;


@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByCorreoElectronico(String email);
    List<Usuario> findByRol(Rol rol);
    List<Usuario> findByActivoAndRol(Boolean activo, Rol rol);
}
