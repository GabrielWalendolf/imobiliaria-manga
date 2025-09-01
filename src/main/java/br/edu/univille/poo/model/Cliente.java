package br.edu.univille.poo.model;

import java.sql.Date;

public class Cliente {

    private long id;
    private String nomeCompleto;
    private String cpf;
    private String telefone;
    private String email;
    private Date dataCadastro;

    // Construtor padrão (usado para criar um cliente novo antes de salvar)
    public Cliente() {
    }

    // Construtor completo (usado para criar um cliente a partir dos dados do banco)
    public Cliente(long id, String nomeCompleto, String cpf, String telefone, String email, Date dataCadastro) {
        this.id = id;
        this.nomeCompleto = nomeCompleto;
        this.cpf = cpf;
        this.telefone = telefone;
        this.email = email;
        this.dataCadastro = dataCadastro;
    }

    // Getters e Setters
    public long getId() { return id; }
    public void setId(long id) { this.id = id; }
    public String getNomeCompleto() { return nomeCompleto; }
    public void setNomeCompleto(String nomeCompleto) { this.nomeCompleto = nomeCompleto; }
    public String getCpf() { return cpf; }
    public void setCpf(String cpf) { this.cpf = cpf; }
    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public Date getDataCadastro() { return dataCadastro; }
    public void setDataCadastro(Date dataCadastro) { this.dataCadastro = dataCadastro; }

    @Override
    public String toString() {
        return nomeCompleto; // Simplificado para exibição em listas
    }
}
