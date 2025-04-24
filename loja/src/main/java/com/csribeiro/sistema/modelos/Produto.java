package com.csribeiro.sistema.modelos;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
public class Produto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String nome;
    private String codigoBarras;
    private String unidadeMedida;
    private String estoque;
    private String precoCusto;
    private String precoVenda;
    private String lucro;
    private String margemLucro;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCodigoBarras() {
        return codigoBarras;
    }

    public void setCodigoBarras(String codigoBarras) {
        this.codigoBarras = codigoBarras;
    }

    public String getUnidadeMedida() {
        return unidadeMedida;
    }

    public void setUnidadeMedida(String unidadeMedida) {
        this.unidadeMedida = unidadeMedida;
    }

    public String getEstoque() {
        return estoque;
    }

    public void setEstoque(String estoque) {
        this.estoque = estoque;
    }

    public String getPrecoCusto() {
        return precoCusto;
    }

    public void setPrecoCusto(String precoCusto) {
        this.precoCusto = precoCusto;
    }

    public String getPrecoVenda() {
        return precoVenda;
    }

    public void setPrecoVenda(String precoVenda) {
        this.precoVenda = precoVenda;
    }

    public String getLucro() {
        return lucro;
    }

    public void setLucro(String lucro) {
        this.lucro = lucro;
    }

    public String getMargemLucro() {
        return margemLucro;
    }

    public void setMargemLucro(String margemLucro) {
        this.margemLucro = margemLucro;
    }
}