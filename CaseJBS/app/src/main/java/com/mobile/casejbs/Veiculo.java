package com.mobile.casejbs;

import java.time.LocalDateTime;
import java.util.Date;

public class Veiculo {
    private String id;
    private String placa;
    private LocalDateTime dataHoraEntrada;
    private LocalDateTime dataHoraSaida;

    private String usuario;
    private boolean ativo;

    public Veiculo() {
    }

    public Veiculo(String id, String placa, LocalDateTime dataHoraEntrada, LocalDateTime dataHoraSaida, String usuario, boolean ativo) {
        this.id = id;
        this.placa = placa;
        this.dataHoraEntrada = dataHoraEntrada;
        this.dataHoraSaida = dataHoraSaida;
        this.usuario = usuario;
        this.ativo = ativo;
    }

    // Construtor para entrada (sem saída)
    public Veiculo(String placa, LocalDateTime dataHoraEntrada, String usuario) {
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

    public LocalDateTime getDataHoraEntrada() {
        return dataHoraEntrada;
    }

    public LocalDateTime getDataHoraSaida() {
        return dataHoraSaida;
    }

    public String getUsuario() {
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

    public void setDataHoraEntrada(LocalDateTime dataHoraEntrada) {
        this.dataHoraEntrada = dataHoraEntrada;
    }

    public void setDataHoraSaida(LocalDateTime dataHoraSaida) {
        this.dataHoraSaida = dataHoraSaida;
    }

    public void setUsuario(String usuario) {
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