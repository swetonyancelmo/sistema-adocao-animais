package br.com.adocao.animais.repository;

import br.com.adocao.animais.model.Adotante;

import java.util.List;

public interface AdotanteRepository {

    void salvar(Adotante adotante);

    Adotante buscarPorId(long id);

    List<Adotante> listarTodos();

    void remover(long id);
}
