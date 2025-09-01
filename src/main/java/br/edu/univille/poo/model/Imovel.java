package br.edu.univille.poo.model;

import java.math.BigDecimal;
import java.sql.Date;

public class Imovel {

    private long id;
    private String endereco;
    private String bairro;
    private String cidade;
    private String cep;
    private String tipoImovel;
    private BigDecimal areaM2; // Alterado para BigDecimal para melhor precisão
    private int quartos;
    private int banheiros;
    private int vagasGaragem;
    private String status;
    private Date dataCadastro;

    // Construtor padrão
    public Imovel() {
    }

    // <<< NOVO CONSTRUTOR COMPLETO >>>
    public Imovel(long id, String endereco, String bairro, String cidade, String cep, String tipoImovel, BigDecimal areaM2, int quartos, int banheiros, int vagasGaragem, String status, Date dataCadastro) {
        this.id = id;
        this.endereco = endereco;
        this.bairro = bairro;
        this.cidade = cidade;
        this.cep = cep;
        this.tipoImovel = tipoImovel;
        this.areaM2 = areaM2;
        this.quartos = quartos;
        this.banheiros = banheiros;
        this.vagasGaragem = vagasGaragem;
        this.status = status;
        this.dataCadastro = dataCadastro;
    }

    // Getters e Setters
    public long getId() { return id; }
    public void setId(long id) { this.id = id; }
    public String getEndereco() { return endereco; }
    public void setEndereco(String endereco) { this.endereco = endereco; }
    public String getBairro() { return bairro; }
    public void setBairro(String bairro) { this.bairro = bairro; }
    public String getCidade() { return cidade; }
    public void setCidade(String cidade) { this.cidade = cidade; }
    public String getCep() { return cep; }
    public void setCep(String cep) { this.cep = cep; }
    public String getTipoImovel() { return tipoImovel; }
    public void setTipoImovel(String tipoImovel) { this.tipoImovel = tipoImovel; }
    public BigDecimal getAreaM2() { return areaM2; }
    public void setAreaM2(BigDecimal areaM2) { this.areaM2 = areaM2; }
    public int getQuartos() { return quartos; }
    public void setQuartos(int quartos) { this.quartos = quartos; }
    public int getBanheiros() { return banheiros; }
    public void setBanheiros(int banheiros) { this.banheiros = banheiros; }
    public int getVagasGaragem() { return vagasGaragem; }
    public void setVagasGaragem(int vagasGaragem) { this.vagasGaragem = vagasGaragem; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public Date getDataCadastro() { return dataCadastro; }
    public void setDataCadastro(Date dataCadastro) { this.dataCadastro = dataCadastro; }

    @Override
    public String toString() {
        return tipoImovel + " - " + endereco;
    }
}
