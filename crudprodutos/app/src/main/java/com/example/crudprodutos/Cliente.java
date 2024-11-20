package com.example.crudprodutos;

public class Cliente {

    private int id;
    private String nome;
    private String cnpj;
    private String descricao;
    private String endereco;

    // Construtor da classe Cliente
    public Cliente(int id, String nome, String cnpj, String descricao, String endereco) {
        this.id = id;
        this.nome = nome;
        this.cnpj = cnpj;
        this.descricao = descricao;
        this.endereco = endereco;
    }

    // Métodos getters para acessar os valores dos atributos
    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getCnpj() {
        return cnpj;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getEndereco() {
        return endereco;
    }
}
