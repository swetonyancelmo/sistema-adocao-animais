package br.com.adocao.animais.model;

public class Gato extends Animal implements CuidadosEspeciais{

    public Gato(String nome, String raca, int idade, String cor) {
        super(nome, raca, idade, cor);
    }

    @Override
    public String emitirSom() {
        return "Miau!";
    }


    @Override
    public void vacinar() {
        System.out.println("O gato " + getNome() + " foi vacinado.");
    }

    @Override
    public void vermifugar() {
        System.out.println("O gato " + getNome() + " foi vermifugado.");
    }
}
