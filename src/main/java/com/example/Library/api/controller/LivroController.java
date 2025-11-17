package com.example.Library.api.controller;

import com.example.Library.api.dto.LivroRequest;
import com.example.Library.api.dto.LivroResponse;
import com.example.Library.api.mapper.LivroApiMapper;
import com.example.Library.aplicacao.GerenciamentoLivroService;
import com.example.Library.aplicacao.excecoes.IsbnDuplicadoException;
import com.example.Library.dominio.model.Livro;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/livros")
public class LivroController {

    private final GerenciamentoLivroService livroService;
    private final LivroApiMapper apiMapper;

    public LivroController(GerenciamentoLivroService livroService, LivroApiMapper apiMapper) {
        this.livroService = livroService;
        this.apiMapper = apiMapper;
    }

    @PostMapping
    public ResponseEntity<LivroResponse> criarLivro(@Valid @RequestBody LivroRequest request) {
        Livro novoLivro = apiMapper.toDomain(request);
        Livro livroSalvo = livroService.registrarNovoLivro(novoLivro);

        return ResponseEntity.status(HttpStatus.CREATED).body(apiMapper.toResponse(livroSalvo));
    }

    @GetMapping
    public List<LivroResponse> listarTodosLivros() {
        return livroService.buscarTodosLivros().stream()
                .map(apiMapper::toResponse)
                .collect(Collectors.toList());
    }

    // RF02 - Buscar Livro por ID
    @GetMapping("/{id}")
    public LivroResponse buscarLivroPorId(@PathVariable Long id) {
        return apiMapper.toResponse(livroService.buscarLivroPorId(id));
    }

    @GetMapping("/isbn/{isbn}")
    public LivroResponse buscarLivroPorIsbn(@PathVariable String isbn) {
        return apiMapper.toResponse(livroService.buscarLivroPorIsbn(isbn));
    }

    @PutMapping("/{id}")
    public LivroResponse atualizarLivro(@PathVariable Long id, @Valid @RequestBody LivroRequest request) {
        Livro dadosAtualizados = apiMapper.toDomain(request);

        Livro livroAtualizado = livroService.atualizarLivro(id, dadosAtualizados);

        return apiMapper.toResponse(livroAtualizado);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removerLivro(@PathVariable Long id) {
        livroService.removerLivro(id);
    }

    @ExceptionHandler(IsbnDuplicadoException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public String handleIsbnDuplicado(IsbnDuplicadoException ex) {
        return ex.getMessage();
    }


    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleNotFound(RuntimeException ex) {
        if (ex.getMessage().contains("não encontrado")) {
            return ex.getMessage();
        }
        throw ex;
    }
}
