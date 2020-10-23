package br.com.bandtec.continuada.dominios;


import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
public class Teatro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer codPeca;

    @NotNull
    @Column(nullable = false, length = 50, name = "nomePeca")
    private String nomePeca;

    @NotNull
    @Column(nullable = false, length = 20, name = "horario")
    private String horario;

    @Column(name = "valor_ingresso")
    private Double valorIngresso;

    public Teatro() {
    }

    public Teatro(Integer codPeca, @NotNull String nomePeca, @NotNull String horario, Double valorIngresso) {
        this.codPeca = codPeca;
        this.nomePeca = nomePeca;
        this.horario = horario;
        this.valorIngresso = valorIngresso;
    }

    public Integer getCodPeca() {
        return codPeca;
    }

    public void setCodPeca(Integer codPeca) {
        this.codPeca = codPeca;
    }

    public String getNomePeca() {
        return nomePeca;
    }

    public void setNomePeca(String nomePeca) {
        this.nomePeca = nomePeca;
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
        return "Teatro{" +
                "codPeca=" + codPeca +
                ", nomePeca='" + nomePeca + '\'' +
                ", horario='" + horario + '\'' +
                ", valorIngresso=" + valorIngresso +
                '}';
    }
}
