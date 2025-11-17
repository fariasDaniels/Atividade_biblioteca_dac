package com.example.Library.api.dto;

public record LivroResponse(
        Long id,
        String titulo,
        String autor,
        String isbn,
        Integer anoPublicacao,
        Integer quantidadeEstoque
) {}