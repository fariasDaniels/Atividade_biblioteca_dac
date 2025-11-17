package com.example.Library.persistencia;

import com.example.Library.dominio.model.Livro;
import org.springframework.stereotype.Component;

@Component
public class LivroMapper {
    public LivroEntity toEntity(Livro dominioLivro) {
        if (dominioLivro == null) {
            return null;
        }
        LivroEntity entity = new LivroEntity();

        entity.setId(dominioLivro.getId());
        entity.setTitulo(dominioLivro.getTitulo());
        entity.setAutor(dominioLivro.getAutor());
        entity.setIsbn(dominioLivro.getIsbn());
        entity.setAnoPublicacao(dominioLivro.getAnoPublicacao());
        entity.setQuantidadeEstoque(dominioLivro.getQuantidadeEstoque());

        return entity;
    }
    public Livro toDomain(LivroEntity entity) {
        if (entity == null) {
            return null;
        }
        Livro dominioLivro = new Livro();

        dominioLivro.setId(entity.getId());
        dominioLivro.setTitulo(entity.getTitulo());
        dominioLivro.setAutor(entity.getAutor());
        dominioLivro.setIsbn(entity.getIsbn());
        dominioLivro.setAnoPublicacao(entity.getAnoPublicacao());
        dominioLivro.setQuantidadeEstoque(entity.getQuantidadeEstoque());

        return dominioLivro;
    }
}