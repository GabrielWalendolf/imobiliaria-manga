package br.edu.univille.poo.dao;

import java.math.BigDecimal;
import java.sql.Date;

/**
 * Modelo de dados para a entidade Contrato.
 * Representa um contrato de aluguel entre um cliente e um imóvel.
 */
public class Contrato { // Nome da classe no singular

    private long id;
    private BigDecimal valorAluguel; // Usar BigDecimal para valores monetários é a melhor prática
    private Date dataInicio;
    private Date dataFim;
    private String statusContrato;

    // Relacionamentos com outras entidades
    private Cliente cliente;
    private Imovel imovel;

    // Construtor padrão
    public Contrato() {
    }

    // Getters e Setters
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public BigDecimal getValorAluguel() {
        return valorAluguel;
    }

    public void setValorAluguel(BigDecimal valorAluguel) {
        this.valorAluguel = valorAluguel;
    }

    public Date getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(Date dataInicio) {
        this.dataInicio = dataInicio;
    }

    public Date getDataFim() {
        return dataFim;
    }

    public void setDataFim(Date dataFim) {
        this.dataFim = dataFim;
    }

    public String getStatusContrato() {
        return statusContrato;
    }

    public void setStatusContrato(String statusContrato) {
        this.statusContrato = statusContrato;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Imovel getImovel() {
        return imovel;
    }

    public void setImovel(Imovel imovel) {
        this.imovel = imovel;
    }

    @Override
    public String toString() {
        return "Contrato{" +
                "id=" + id +
                ", status='" + statusContrato + '\'' +
                ", cliente=" + (cliente != null ? cliente.getNomeCompleto() : "N/A") +
                ", imovel=" + (imovel != null ? imovel.getEndereco() : "N/A") +
                '}';
    }
}
