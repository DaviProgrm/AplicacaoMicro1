package com.example.vendas.domain.reponse;

import com.fasterxml.jackson.annotation.JsonProperty;

public class EstoqueResponse {
    private long timestamp;
    private String date;

    @JsonProperty("codBarras")
    private String codigoDeBarras;

    private int quantidade;

    // Getters e setters

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCodigoDeBarras() {
        return codigoDeBarras;
    }

    public void setCodigoDeBarras(String codigoDeBarras) {
        this.codigoDeBarras = codigoDeBarras;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }
}





