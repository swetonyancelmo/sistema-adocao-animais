package br.com.adocao.animais.model;

public class Adotante {

    private long id;
    private String nome;
    private String cpf;
    private String endereco;
    private String telefone;
    private int quantidadeAcocoes;

    public Adotante(String nome, String cpf, String endereco, String telefone) {
        this.nome = nome;
        this.cpf = cpf;
        this.endereco = endereco;
        this.telefone = telefone;
        this.quantidadeAcocoes = 0;
    }

    public Adotante(String nome, String cpf, String telefone){
        this(nome, cpf, "Endereço não informado", telefone);
    }

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

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public int getQuantidadeAcocoes() {
        return quantidadeAcocoes;
    }

    public void incrementarAdocoes(){
        this.quantidadeAcocoes++;
    }
}
