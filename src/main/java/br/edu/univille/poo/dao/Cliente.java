package br.edu.univille.poo.dao;

import java.util.Date;

/**
 * Classe modelo para representar a entidade Cliente.
 * Corresponde à tabela 'clientes' no banco de dados.
 */
public class Cliente {

    private long id;
    private String nomeCompleto;
    private String cpf;
    private String telefone;
    private String email;
    private Date dataCadastro;

    // Getters e Setters para todos os atributos

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
}
