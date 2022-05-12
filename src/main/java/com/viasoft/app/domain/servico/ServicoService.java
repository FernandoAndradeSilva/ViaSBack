package com.viasoft.app.domain.servico;


import com.viasoft.app.domain.historico.Historico;
import com.viasoft.app.domain.historico.HistoricoRepository;
import com.viasoft.app.utils.DateUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Date;

@Service
public class ServicoService {


    @Autowired
    private ServicoRepository servicoRepository;

    @Autowired
    private HistoricoRepository historicoRepository;


    public void verificacaoInicial() {
        Document doc = null;
        try {
            doc = Jsoup.connect("http://www.nfe.fazenda.gov.br/portal/disponibilidade.aspx").get();
            Element body = doc.select("table.tabelaListagemDados").first();

            Elements elementsTh = body.getElementsByTag("tbody").select("tr > th");
            Elements elementsTd = body.getElementsByTag("tbody").select("tr > td");

            int controlador = 0;
            Servico servico = new Servico();

            int indexStatusServico = 0;

            for(int i = 0 ; i < elementsTh.size() ; i++) {
                if(elementsTh.get(i).text().equalsIgnoreCase("Status Serviço4")) {
                    indexStatusServico = i;
                }
            }


            for(int i = 0 ; i < elementsTd.size() ; i++) {
                if(controlador == 0) {
                    servico = new Servico(elementsTd.get(i).text());
                }

                if (controlador == indexStatusServico) {
                    switch (elementsTd.get(i).select("td > img").attr("src")) {
                        case "imagens/bola_vermelho_P.png":
                            servico.setStatusAtual(StatusServico.VERMELHO);
                            break;
                        case "imagens/bola_amarela_P.png":
                            servico.setStatusAtual(StatusServico.AMARELO);
                            break;
                        case "imagens/bola_verde_P.png":
                            servico.setStatusAtual(StatusServico.VERDE);
                            break;
                    }
                }


                if (controlador == (elementsTh.size() -1)) {
                    controlador = 0;
                    servicoRepository.save(servico);
                } else {
                    controlador++;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Scheduled(fixedRate = 15000)
    public void jobReceita() {

        Document doc = null;
        try {
            doc = Jsoup.connect("http://www.nfe.fazenda.gov.br/portal/disponibilidade.aspx").get();
            Element body = doc.select("table.tabelaListagemDados").first();

            Elements elementsTh = body.getElementsByTag("tbody").select("tr > th");
            int indexStatusServico = 0;

            for(int i = 0 ; i < elementsTh.size() ; i++) {
                if(elementsTh.get(i).text().equalsIgnoreCase("Status Serviço4")) {
                    indexStatusServico = i;
                }
            }

            Elements elementsTd = body.getElementsByTag("tbody").select("tr > td");
            int controlador = 0;

            Servico servico = null;

            for(int i = 0 ; i < elementsTd.size() ; i++) {

                if(controlador == 0) {
                    servico = new Servico(elementsTd.get(i).text());
                }

                if (controlador == indexStatusServico) {

                    Servico servicoDb = servicoRepository.getBySiglaEstado(servico.getSiglaEstado());

                    Historico historico = new Historico();
                    historico.setServico(servicoDb);

                    historico.setDate(LocalDateTime.now());

                    boolean indisponibilidade = false;

                    switch (elementsTd.get(i).select("td > img").attr("src")) {
                        case "imagens/bola_vermelho_P.png":
                            historico.setStatusServico(StatusServico.VERMELHO);
                            servicoDb.incrementaIndisponibilidade();
                            indisponibilidade = true;
                            break;
                        case "imagens/bola_amarela_P.png":
                            historico.setStatusServico(StatusServico.AMARELO);
                            servicoDb.incrementaIndisponibilidade();
                            indisponibilidade = true;
                            break;
                        case "imagens/bola_verde_P.png":
                            historico.setStatusServico(StatusServico.VERDE);
                            break;
                    }

                    // ATUALIZA BANCO DE DADOS QUANDO O STATUS É DIFERENTE
                    if(servicoDb.getStatusAtual() != historico.getStatusServico()
                            || indisponibilidade) {
                        servicoDb.setStatusAtual(historico.getStatusServico());
                        servicoRepository.saveAndFlush(servicoDb);
                    }

                    historicoRepository.save(historico);

                }
                if (controlador == (elementsTh.size() -1)) {
                    controlador = 0;
                } else {
                    controlador++;
                }
            }



        } catch (IOException e) {
            e.printStackTrace();
        }






    }
}
