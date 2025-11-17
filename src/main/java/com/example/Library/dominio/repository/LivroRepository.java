package com.example.Library.dominio.repository;

import com.example.Library.dominio.model.Livro;
import java.util.List;
import java.util.Optional;


public interface LivroRepository {
    Livro salvar(Livro livro);
    Optional<Livro> buscarPorId(Long id);
    List<Livro> buscarTodos();
    void deletar(Long id);
    Optional<Livro> buscarPorIsbn(String isbn);
    boolean existePorIsbn(String isbn);
}
