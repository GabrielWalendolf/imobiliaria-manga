package br.edu.univille.poo.dao;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Classe modelo para representar a entidade Contrato.
 * Corresponde à tabela 'contratos' e representa o relacionamento
 * entre um Cliente e um Imovel.
 */
public class Contratos {

    private long id;
    private BigDecimal valorAluguelMensal; // BigDecimal é ideal para valores monetários.
    private Date dataInicio;
    private Date dataFim;
    private String statusContrato; // Ex: 'Ativo', 'Expirado'
    private Date dataAssinatura;

    // Objetos para representar as chaves estrangeiras (relacionamentos)
    private Cliente cliente;
    private Imovel imovel;

    // Getters e Setters para todos os atributos

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public BigDecimal getValorAluguelMensal() {
        return valorAluguelMensal;
    }

    public void setValorAluguelMensal(BigDecimal valorAluguelMensal) {
        this.valorAluguelMensal = valorAluguelMensal;
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

    public Date getDataAssinatura() {
        return dataAssinatura;
    }

    public void setDataAssinatura(Date dataAssinatura) {
        this.dataAssinatura = dataAssinatura;
    }

    // Getters e Setters para os objetos relacionados

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
}
