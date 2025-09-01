package br.edu.univille.poo.model;

import java.math.BigDecimal;
import java.sql.Date;

public class Contrato {

    private long id;
    private BigDecimal valorAluguel;
    private Date dataInicio;
    private Date dataFim;
    private String statusContrato;
    private Cliente cliente;
    private Imovel imovel;

    // Construtor padrão (existente)
    public Contrato() {
    }

    // <<< ADICIONE ESTE NOVO CONSTRUTOR >>>
    public Contrato(long id, BigDecimal valorAluguel, Date dataInicio, Date dataFim, String statusContrato, Cliente cliente, Imovel imovel) {
        this.id = id;
        this.valorAluguel = valorAluguel;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.statusContrato = statusContrato;
        this.cliente = cliente;
        this.imovel = imovel;
    }

    // Getters e Setters (existentes, sem alteração)
    public long getId() { return id; }
    public void setId(long id) { this.id = id; }
    public BigDecimal getValorAluguel() { return valorAluguel; }
    public void setValorAluguel(BigDecimal valorAluguel) { this.valorAluguel = valorAluguel; }
    public Date getDataInicio() { return dataInicio; }
    public void setDataInicio(Date dataInicio) { this.dataInicio = dataInicio; }
    public Date getDataFim() { return dataFim; }
    public void setDataFim(Date dataFim) { this.dataFim = dataFim; }
    public String getStatusContrato() { return statusContrato; }
    public void setStatusContrato(String statusContrato) { this.statusContrato = statusContrato; }
    public Cliente getCliente() { return cliente; }
    public void setCliente(Cliente cliente) { this.cliente = cliente; }
    public Imovel getImovel() { return imovel; }
    public void setImovel(Imovel imovel) { this.imovel = imovel; }

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
