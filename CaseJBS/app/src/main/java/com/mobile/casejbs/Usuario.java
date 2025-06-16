package com.mobile.casejbs;

import java.util.Date;

public class Usuario {
    private String id;
    private Date dataCadastro;
    private String tipoUsuario;
    private String nome;
    private String email;
    private String senha;


    //    Construtores
    public Usuario() {
        this.dataCadastro = new Date();
        this.tipoUsuario = "cliente";
    }

    public Usuario(String email) {
        this.email = email;
    }

    public Usuario(String tipoUsuario, String nome, String email, String senha) {
        this.dataCadastro = new Date();
        this.tipoUsuario = tipoUsuario;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
    }




    //    Getters
    public String getId() {
        return id;
    }

    public Date getDataCadastro() {
        return dataCadastro;
    }

    public String getTipoUsuario() {
        return tipoUsuario;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public String getSenha() {
        return senha;
    }

    //    Setters


    public void setId(String id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setTipoUsuario(String tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    //    Métodos de verificação:
    public boolean isAdmin() {
        return "admin".equals(tipoUsuario);
    }

    public boolean isFuncionario() {
        return "funcionario".equals(tipoUsuario);
    }

    public boolean isCliente() {
        return "cliente".equals(tipoUsuario);
    }


    //    ToString...
    @Override
    public String toString() {
        return "Usuario:\n" +
                "   id: " + this.id + "\n" +
                "   dataCadastro: " + dataCadastro + "\n" +
                "   tipoUsuario: " + tipoUsuario + '\'' + "\n" +
                "   nome: "  + nome + '\'' + "\n" +
                "   email: " + email + '\'' + "\n";
    }
}