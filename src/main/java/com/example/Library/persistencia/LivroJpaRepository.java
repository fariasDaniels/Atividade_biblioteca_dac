package com.example.Library.persistencia;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LivroJpaRepository extends JpaRepository<LivroEntity, Long> {
    boolean existsByIsbn(String isbn);
    Optional<LivroEntity> findByIsbn(String isbn);
}
