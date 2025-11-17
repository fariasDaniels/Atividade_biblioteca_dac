package com.example.Library.aplicacao;

import com.example.Library.aplicacao.excecoes.IsbnDuplicadoException;
import com.example.Library.dominio.model.Livro;
import com.example.Library.dominio.repository.LivroRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class GerenciamentoLivroService {

    private final LivroRepository livroRepository;

    public GerenciamentoLivroService(LivroRepository livroRepository) {
        this.livroRepository = livroRepository;
    }

    @Transactional
    public Livro registrarNovoLivro(Livro livro) {
        if (livroRepository.existePorIsbn(livro.getIsbn())) {
            throw new IsbnDuplicadoException(livro.getIsbn());
        }
        return livroRepository.salvar(livro);
    }

    @Transactional
    public Livro atualizarLivro(Long id, Livro dadosLivro) {
        Livro livroExistente = livroRepository.buscarPorId(id)
                .orElseThrow(() -> new RuntimeException("Livro não encontrado com ID: " + id));
        if (!livroExistente.getIsbn().equals(dadosLivro.getIsbn())) {
            if (livroRepository.existePorIsbn(dadosLivro.getIsbn())) {
                throw new IsbnDuplicadoException(dadosLivro.getIsbn());
            }
            livroExistente.setIsbn(dadosLivro.getIsbn());
        }
        livroExistente.setTitulo(dadosLivro.getTitulo());
        livroExistente.setAutor(dadosLivro.getAutor());
        livroExistente.setAnoPublicacao(dadosLivro.getAnoPublicacao());
        livroExistente.setQuantidadeEstoque(dadosLivro.getQuantidadeEstoque());
        return livroRepository.salvar(livroExistente);
    }

    @Transactional
    public void removerLivro(Long id) {
        if (!livroRepository.buscarPorId(id).isPresent()) {
            throw new RuntimeException("Livro não pode ser removido pois não foi encontrado.");
        }
        livroRepository.deletar(id);
    }
    @Transactional(readOnly = true)
    public Livro buscarLivroPorId(Long id) {
        return livroRepository.buscarPorId(id)
                .orElseThrow(() -> new RuntimeException("Livro não encontrado."));
    }

    @Transactional(readOnly = true)
    public List<Livro> buscarTodosLivros() {
        return livroRepository.buscarTodos();
    }
    @Transactional(readOnly = true)
    public Livro buscarLivroPorIsbn(String isbn) {
        return livroRepository.buscarPorIsbn(isbn)
                .orElseThrow(() -> new RuntimeException("Livro com ISBN " + isbn + " não encontrado."));
    }

}