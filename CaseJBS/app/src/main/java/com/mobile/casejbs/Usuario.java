package com.mobile.casejbs;

import java.util.Date;

public class Usuario {
    private int id;
    private Date dataCadastro;
    private boolean funcAtivo;
    private String tipoUsuario;
    private String cpf;
    private String nome;
    private String email;
    private String senha;


    //    Construtores
    public Usuario() {
        this.dataCadastro = new Date();
        this.funcAtivo = true;
        this.tipoUsuario = "cliente";
    }

    public Usuario(int id, Date dataCadastro, boolean funcAtivo, String tipoUsuario, String cpf, String nome, String email, String senha) {
        this.id = id;
        this.dataCadastro = dataCadastro;
        this.funcAtivo = funcAtivo;
        this.tipoUsuario = tipoUsuario;
        this.cpf = cpf;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
    }


    //    Getters
    public int getId() {
        return id;
    }

    public Date getDataCadastro() {
        return dataCadastro;
    }

    public boolean isFuncAtivo() {
        return funcAtivo;
    }

    public String getTipoUsuario() {
        return tipoUsuario;
    }

    public String getCpf() {
        return cpf;
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
    public void setEmail(String email) {
        this.email = email;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setTipoUsuario(String tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    public void setFuncAtivo(boolean funcAtivo) {
        this.funcAtivo = funcAtivo;
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
                "   funcAtivo: " + funcAtivo + "\n" +
                "   tipoUsuario: " + tipoUsuario + '\'' + "\n" +
                "   cpf: " + cpf + '\'' + "\n" +
                "   nome: "  + nome + '\'' + "\n" +
                "   email: " + email + '\'' + "\n";
    }
}