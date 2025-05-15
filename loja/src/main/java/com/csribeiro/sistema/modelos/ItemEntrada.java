package com.csribeiro.sistema.modelos;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
public class ItemEntrada implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
     private Double quantidade;
     private Double valor;
     private Double precoVenda;

    public Double getPrecoVenda() {
        return precoVenda;
    }

    public void setPrecoVenda(Double precoVenda) {
        this.precoVenda = precoVenda;
    }

    public String getValorCusto() {
        return String.valueOf(valorCusto);
    }

    public void setValorCusto(Double valorCusto) {
        this.valorCusto = valorCusto;
    }

    private Double valorCusto;
     @ManyToOne
     private Entrada entrada;
     @ManyToOne
     private Produto produto;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Double getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Double quantidade) {
        this.quantidade = quantidade;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public Entrada getEntrada() {
        return entrada;
    }

    public void setEntrada(Entrada entrada) {
        this.entrada = entrada;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }
}