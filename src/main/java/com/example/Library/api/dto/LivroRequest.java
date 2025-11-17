package com.example.Library.api.dto;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
public record LivroRequest(
        @NotBlank(message = "O título é obrigatório.")
        String titulo,

        @NotBlank(message = "O autor é obrigatório.")
        String autor,

        @NotBlank(message = "O ISBN é obrigatório.")
        String isbn,

        @NotNull(message = "O ano de publicação é obrigatório.")
        @Min(value = 1800, message = "Ano de publicação inválido.")
        Integer anoPublicacao,

        @NotNull(message = "A quantidade em estoque é obrigatória.")
        @Min(value = 0, message = "A quantidade em estoque não pode ser negativa.")
        Integer quantidadeEstoque
) {}