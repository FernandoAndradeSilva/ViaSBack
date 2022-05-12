package com.viasoft.app.domain.historico;
import com.viasoft.app.domain.servico.Servico;
import com.viasoft.app.domain.servico.StatusServico;
import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "historico")
public class Historico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private LocalDateTime date;

    @Transient
    private String dataFinal;

    @ManyToOne
    @JoinColumn(name = "servico_id")
    private Servico servico;

    private StatusServico statusServico;

    public Historico() {
    }

    public Integer getId() {
        return id;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public Servico getServico() {
        return servico;
    }

    public void setServico(Servico servico) {
        this.servico = servico;
    }

    public String getDataFinal() {
        return dataFinal;
    }

    public void setDataFinal(String dataFinal) {
        this.dataFinal = dataFinal;
    }

    public StatusServico getStatusServico() {
        return statusServico;
    }

    public void setStatusServico(StatusServico statusServico) {
        this.statusServico = statusServico;
    }
}
