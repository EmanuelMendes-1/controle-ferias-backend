package com.ferias.repository.jpa;

import com.ferias.entity.TbPessoa;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TbPessoaJpaRepository extends JpaRepository<TbPessoa, Integer> {

    List<TbPessoa> findByPessoaTipo_Descricao(String descricao);

    Optional<TbPessoa> findByCpf(String cpf);
}
