package com.example.Library.api.mapper;

import com.example.Library.api.dto.LivroRequest;
import com.example.Library.api.dto.LivroResponse;
import com.example.Library.dominio.model.Livro;
import org.springframework.stereotype.Component;

@Component
public class LivroApiMapper {

    public Livro toDomain(LivroRequest request) {
        return new Livro(
                request.titulo(),
                request.autor(),
                request.isbn(),
                request.anoPublicacao(),
                request.quantidadeEstoque()
        );
    }

    public LivroResponse toResponse(Livro livro) {
        return new LivroResponse(
                livro.getId(),
                livro.getTitulo(),
                livro.getAutor(),
                livro.getIsbn(),
                livro.getAnoPublicacao(),
                livro.getQuantidadeEstoque()
        );
    }
}