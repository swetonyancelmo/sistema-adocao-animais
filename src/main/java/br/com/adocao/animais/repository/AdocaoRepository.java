package br.com.adocao.animais.repository;

import br.com.adocao.animais.model.Adocao;

import java.time.LocalDate;
import java.util.List;

public interface AdocaoRepository {

    void salvar(Adocao adocao);

    List<Adocao> listarTodos();

    List<Adocao> listarPorAdotante(long idAdotante);

    List<Adocao> listarPorPeriodo(LocalDate inicio, LocalDate fim);
}
