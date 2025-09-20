package br.com.adocao.animais.model;

public class Cachorro extends Animal implements CuidadosEspeciais{

    public Cachorro(String nome, String raca, int idade, String cor) {
        super(nome, raca, idade, cor);
    }

    @Override
    public String emitirSom(){
        return "Au au!";
    }

    @Override
    public void vacinar() {
        System.out.println("O cachorro " + getNome() + " foi vacinado.");
    }

    @Override
    public void vermifugar() {
        System.out.println("O cachorro " + getNome() + " foi vermifugado.");
    }
}
