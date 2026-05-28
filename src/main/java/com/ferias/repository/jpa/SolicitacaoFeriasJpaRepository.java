package com.ferias.repository.jpa;

import com.ferias.entity.TbSolicitacaoFerias;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface SolicitacaoFeriasJpaRepository extends JpaRepository<TbSolicitacaoFerias, Long> {
    
    List<TbSolicitacaoFerias> findByFuncionarioId(Long funcionarioId);
    
    List<TbSolicitacaoFerias> findByStatus(String status);
}