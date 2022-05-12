package com.viasoft.app.infrastructure.web.rest.controller.servico;


import com.viasoft.app.domain.historico.Historico;
import com.viasoft.app.domain.historico.HistoricoRepository;
import com.viasoft.app.domain.servico.Servico;
import com.viasoft.app.domain.servico.ServicoRepository;
import com.viasoft.app.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.List;

@RestController
@RequestMapping("/api/servicos")
@CrossOrigin(origins = "http://localhost:4200")
public class ServicoController {

    @Autowired
    ServicoRepository servicoRepository;

    @Autowired
    HistoricoRepository historicoRepository;

    @GetMapping
    public List<Servico> obterTodos() {
        return servicoRepository.findAll();
    }

    @GetMapping("{estado}")
    public Servico obterStatusAtual(@PathVariable String estado) {
        return servicoRepository.getBySiglaEstado(estado);
    }

    @GetMapping("/busca")
    @ResponseBody
    public List<Historico> filtrarPorData(@RequestParam(required = false) String dtInicial,
                                          @RequestParam(required = false) String dtFinal) {


        LocalDate dateInicial = DateUtils.returnLocalDate(dtInicial);
        LocalDate dateFinal = DateUtils.returnLocalDate(dtFinal);

        LocalDateTime localDateTimeInicial = LocalDateTime.of(dateInicial ,LocalTime.of(0, 0, 0));
        LocalDateTime localDateTimeFinal = LocalDateTime.of(dateFinal ,LocalTime.of(23, 59, 59));

        List<Historico> historicos =  historicoRepository.findHistorioByDateInterval(localDateTimeInicial , localDateTimeFinal);

        historicos.forEach(historico -> {
            historico.setDataFinal(DateUtils.localeDateTimeToString(historico.getDate()));
        });

        return historicos;
    }


    @GetMapping("/orderDisp")
    public List<Servico> obterTodosOrdenandosIndisponibilidade() {
        return servicoRepository.findAll(Sort.by(Sort.Direction.DESC, "quantidadeIndisponibilidade"));
    }



}
