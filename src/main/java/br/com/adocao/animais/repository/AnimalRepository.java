package br.com.adocao.animais.repository;

import br.com.adocao.animais.model.Animal;

import java.util.List;

public interface AnimalRepository {

    void salvar(Animal animal);

    Animal buscarPorId(long id);

    List<Animal> listarTodos();

    void remover (long id);
}
