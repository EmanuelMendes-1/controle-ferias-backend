package com.ferias.repository.jpa;

import com.ferias.entity.TbPessoaTipo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TbPessoaTipoJpaRepository extends JpaRepository<TbPessoaTipo, Integer> {

    Optional<TbPessoaTipo> findByDescricao(String descricao);
}
