package com.ferias.repository.jpa;

import com.ferias.entity.TbUsuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TbUsuarioJpaRepository extends JpaRepository<TbUsuario, Integer> {

    Optional<TbUsuario> findByLogin(String login);
}
