package com.viasoft.app.domain.servico;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServicoRepository extends JpaRepository<Servico, Integer> {


    public Servico getBySiglaEstado(String siglaEstado);



}
