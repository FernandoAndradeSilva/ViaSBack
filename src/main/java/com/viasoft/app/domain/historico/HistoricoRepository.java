package com.viasoft.app.domain.historico;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface HistoricoRepository extends JpaRepository<Historico, Integer> {

    @Query("SELECT h FROM Historico h WHERE h.date BETWEEN ?1 AND ?2")
    public List<Historico> findHistorioByDateInterval(LocalDateTime dataInicial, LocalDateTime dataFinal);



}
