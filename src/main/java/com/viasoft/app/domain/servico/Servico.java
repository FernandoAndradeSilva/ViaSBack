package com.viasoft.app.domain.servico;
import com.viasoft.app.domain.historico.Historico;
import org.hibernate.procedure.spi.ParameterRegistrationImplementor;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "servico")
public class Servico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String siglaEstado;

    private int quantidadeIndisponibilidade;

    private StatusServico statusAtual;

    @OneToMany(mappedBy = "servico", fetch = FetchType.EAGER)
    private Set<Historico> historicos;

    public Servico(String sigla) {
        this.siglaEstado = sigla;
    }

    public Servico() {

    }

    public String getSiglaEstado() {
        return siglaEstado;
    }

    public void setSiglaEstado(String siglaEstado) {
        this.siglaEstado = siglaEstado;
    }

    public StatusServico getStatusAtual() {
        return statusAtual;
    }

    public void setStatusAtual(StatusServico statusAtual) {
        this.statusAtual = statusAtual;
    }

    public Integer getId() {
        return id;
    }

    public int getQuantidadeIndisponibilidade() {
        return quantidadeIndisponibilidade;
    }

    public void setQuantidadeIndisponibilidade(int quantidadeIndisponibilidade) {
        this.quantidadeIndisponibilidade = quantidadeIndisponibilidade;
    }

    public void incrementaIndisponibilidade() {
        this.quantidadeIndisponibilidade++;
    }
}
