package br.edu.univille.poo.dao;

import java.sql.Date;

/**
 * Modelo de dados para a entidade Cliente.
 * Representa um cliente da imobiliária.
 */
public class Cliente {

    private long id;
    private String nomeCompleto;
    private String cpf;
    private String telefone;
    private String email;
    private Date dataCadastro; // java.sql.Date é compatível com o JDBC

    // Construtor padrão
    public Cliente() {
    }

    // Getters e Setters
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNomeCompleto() {
        return nomeCompleto;
    }

    public void setNomeCompleto(String nomeCompleto) {
        this.nomeCompleto = nomeCompleto;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(Date dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    @Override
    public String toString() {
        // Útil para exibir clientes em ComboBoxes ou listas
        return nomeCompleto + " (CPF: " + cpf + ")";
    }
}
