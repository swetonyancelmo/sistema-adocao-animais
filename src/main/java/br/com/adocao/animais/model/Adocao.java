package br.com.adocao.animais.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Adocao {

    private long id;
    private Animal animal;
    private Adotante adotante;
    private LocalDate dataAdocao;

    public Adocao(Animal animal, Adotante adotante){
        this.animal = animal;
        this.adotante = adotante;
        this.dataAdocao = LocalDate.now();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Animal getAnimal() {
        return animal;
    }

    public void setAnimal(Animal animal) {
        this.animal = animal;
    }

    public Adotante getAdotante() {
        return adotante;
    }

    public void setAdotante(Adotante adotante) {
        this.adotante = adotante;
    }

    public LocalDate getDataAdocao() {
        return dataAdocao;
    }

    public void setDataAdocao(LocalDate dataAdocao) {
        this.dataAdocao = dataAdocao;
    }

    @Override
    public String toString() {

        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        return "Adoção [" +
                "ID: " + id +
                ", Animal: " + (animal != null ? animal.getNome() : "N/A") +
                ", Adotante: " + (adotante != null ? adotante.getNome() : "N/A") +
                ", Data: " + dataAdocao.format(fmt) +
                ']';
    }

}
