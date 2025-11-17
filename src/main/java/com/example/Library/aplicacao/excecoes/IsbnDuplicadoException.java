package com.example.Library.aplicacao.excecoes;

public class IsbnDuplicadoException extends RuntimeException {

    public IsbnDuplicadoException(String isbn) {
        super("Já existe um livro cadastrado com o ISBN: " + isbn);
    }
}
