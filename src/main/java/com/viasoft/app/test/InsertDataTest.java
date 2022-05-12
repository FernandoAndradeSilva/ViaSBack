package com.viasoft.app.test;

import com.viasoft.app.domain.servico.ServicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class InsertDataTest {

    private ServicoService servicoService;

    @Autowired
    public InsertDataTest(ServicoService servicoService) {
        this.servicoService = servicoService;
    }

    @EventListener
    public void onApplicationEvent(ContextRefreshedEvent event) {
        this.servicoService.verificacaoInicial();
    }
}
