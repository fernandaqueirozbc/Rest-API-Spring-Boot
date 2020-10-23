package br.com.bandtec.continuada.dominios;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
public class Cinema {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer codFilme;

    @NotNull
    @Column(nullable = false, length = 50, name = "nomeFilme")
    private String nomeFilme;

    @NotNull
    @Column(nullable = false, length = 20, name = "horario")
    private String horario;

    @Column(name = "valor_ingresso")
    private Double valorIngresso;

    public Cinema() {
    }

    public Cinema(String nomeFilme, String horario, Double valorIngresso) {
        this.nomeFilme = nomeFilme;
        this.horario = horario;
        this.valorIngresso = valorIngresso;
    }

    public Integer getCodFilme() {
        return codFilme;
    }

    public void setCodFilme(Integer codFilme) {
        this.codFilme = codFilme;
    }

    public String getNomeFilme() {
        return nomeFilme;
    }

    public void setNomeFilme(String nomeFilme) {
        this.nomeFilme = nomeFilme;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public Double getValorIngresso() {
        return valorIngresso;
    }

    public void setValorIngresso(Double valorIngresso) {
        this.valorIngresso = valorIngresso;
    }

    @Override
    public String toString() {
        return "Cinema{" +
                "codFilme=" + codFilme +
                ", nomeFilme='" + nomeFilme + '\'' +
                ", horario='" + horario + '\'' +
                ", valorIngresso=" + valorIngresso +
                '}';
    }
}


