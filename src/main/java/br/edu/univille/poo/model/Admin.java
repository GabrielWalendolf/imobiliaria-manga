package br.edu.univille.poo.model;

/**
 * Modelo de dados para a entidade Administrador.
 * Representa um usuário com permissões de administrador no sistema.
 */
public class Admin {

    private long id;
    private String usuario;
    private String senhaHash; // Armazena o hash da senha, nunca a senha em texto plano

    // Construtor padrão
    public Admin() {
    }

    // Getters e Setters
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getSenhaHash() {
        return senhaHash;
    }

    public void setSenhaHash(String senhaHash) {
        this.senhaHash = senhaHash;
    }

    @Override
    public String toString() {
        return "Admin{" +
                "id=" + id +
                ", usuario='" + usuario + '\'' +
                '}';
    }
}
