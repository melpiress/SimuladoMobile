package com.mobile.casejbs;

import java.time.LocalDateTime;
import java.util.Date;

public class Veiculo {
    private String id;
    private String placa;
    private Date dataHoraEntrada;
    private Date dataHoraSaida;
    private Usuario usuario;
    private boolean ativo;

    public Veiculo() {
    }

    public Veiculo(String id, String placa, Date dataHoraEntrada, Date dataHoraSaida, Usuario usuario, boolean ativo) {
        this.id = id;
        this.placa = placa;
        this.dataHoraEntrada = dataHoraEntrada;
        this.dataHoraSaida = dataHoraSaida;
        this.usuario = usuario;
        this.ativo = ativo;
    }

    // Construtor para entrada (sem saída)
    public Veiculo(String placa, Date dataHoraEntrada, Usuario usuario) {
        this.placa = placa;
        this.dataHoraEntrada = dataHoraEntrada;
        this.usuario = usuario;
        this.ativo = true;
        this.dataHoraSaida = null;
    }

    // Getters
    public String getId() {
        return id;
    }

    public String getPlaca() {
        return placa;
    }

    public Date getDataHoraEntrada() {
        return dataHoraEntrada;
    }

    public Date getDataHoraSaida() {
        return dataHoraSaida;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public boolean isAtivo() {
        return ativo;
    }

    // Setters
    public void setId(String id) {
        this.id = id;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public void setDataHoraEntrada(Date dataHoraEntrada) {
        this.dataHoraEntrada = dataHoraEntrada;
    }

    public void setDataHoraSaida(Date dataHoraSaida) {
        this.dataHoraSaida = dataHoraSaida;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }


//    ToString
    @Override
    public String toString() {
        return "Veiculo{" +
                "id='" + id + '\'' +
                ", placa='" + placa + '\'' +
                ", dataHoraEntrada=" + dataHoraEntrada +
                ", dataHoraSaida=" + dataHoraSaida +
                ", usuario='" + usuario + '\'' +
                ", ativo=" + ativo +
                '}';
    }
}