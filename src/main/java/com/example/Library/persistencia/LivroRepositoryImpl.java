package com.example.Library.persistencia;

import com.example.Library.dominio.model.Livro;
import com.example.Library.dominio.repository.LivroRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Repository
public class LivroRepositoryImpl implements LivroRepository {

    private final LivroJpaRepository jpaRepository;
    private final LivroMapper mapper;

    public LivroRepositoryImpl(LivroJpaRepository jpaRepository, LivroMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public Livro salvar(Livro livro) {
        LivroEntity entity = mapper.toEntity(livro);
        LivroEntity savedEntity = jpaRepository.save(entity);
        return mapper.toDomain(savedEntity);
    }

    @Override
    public Optional<Livro> buscarPorId(Long id) {
        return jpaRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public List<Livro> buscarTodos() {
        return jpaRepository.findAll()
                .stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void deletar(Long id) {
        jpaRepository.deleteById(id);
    }
    @Override
    public boolean existePorIsbn(String isbn) {
        return jpaRepository.existsByIsbn(isbn);
    }
    @Override
    public Optional<Livro> buscarPorIsbn(String isbn) {
        // Delega para o JPA e usa o map para converter LivroEntity -> Livro
        return jpaRepository.findByIsbn(isbn)
                .map(mapper::toDomain);
    }
}
