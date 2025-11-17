package com.example.Library;

import com.example.Library.aplicacao.GerenciamentoLivroService;
import com.example.Library.aplicacao.excecoes.IsbnDuplicadoException;
import com.example.Library.dominio.model.Livro;
import com.example.Library.dominio.repository.LivroRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GerenciamentoLivroServiceTest {

	@InjectMocks
	private GerenciamentoLivroService service; // Serviço a ser testado

	@Mock
	private LivroRepository repository; // Dependência simulada (Mock)

	private Livro livroValido;
	private final Long ID_VALIDO = 1L;

	@BeforeEach
	void setUp() {
		livroValido = new Livro("Clean Code", "Robert C. Martin", "978-0132350884", 2008, 10);
		livroValido.setId(ID_VALIDO);
	}

	@Test
	void registrarNovoLivro_deveSalvarLivroComSucesso() {
		when(repository.existePorIsbn(anyString())).thenReturn(false);
		when(repository.salvar(any(Livro.class))).thenReturn(livroValido);
		Livro resultado = service.registrarNovoLivro(livroValido);
		assertNotNull(resultado);
		verify(repository, times(1)).salvar(any(Livro.class));
	}

	@Test
	void registrarNovoLivro_deveLancarExcecaoSeIsbnDuplicado() {
		when(repository.existePorIsbn(anyString())).thenReturn(true);
		assertThrows(IsbnDuplicadoException.class, () -> {
			service.registrarNovoLivro(livroValido);
		});
		verify(repository, never()).salvar(any(Livro.class));
	}

	@Test
	void buscarLivroPorId_deveRetornarLivroSeEncontrado() {
		when(repository.buscarPorId(ID_VALIDO)).thenReturn(Optional.of(livroValido));
		Livro resultado = service.buscarLivroPorId(ID_VALIDO);
		assertNotNull(resultado);
		assertEquals(ID_VALIDO, resultado.getId());
	}

	@Test
	void buscarTodosLivros_deveRetornarListaDeLivros() {
		List<Livro> listaSimulada = List.of(livroValido,
				new Livro("Domain Driven Design", "Eric Evans", "978-0321834079", 2003, 5));

		when(repository.buscarTodos()).thenReturn(listaSimulada);

		List<Livro> resultado = service.buscarTodosLivros();

		assertFalse(resultado.isEmpty());
		assertEquals(2, resultado.size());
	}


	@Test
	void atualizarLivro_deveAtualizarLivroComSucesso() {
		when(repository.buscarPorId(ID_VALIDO)).thenReturn(Optional.of(livroValido));
		when(repository.existePorIsbn(anyString())).thenReturn(false);
		when(repository.salvar(any(Livro.class))).thenReturn(livroValido);

		Livro dadosAtualizados = new Livro(livroValido.getTitulo(), livroValido.getAutor(), "978-NOVO-ISBN", 2025, 100);

		Livro resultado = service.atualizarLivro(ID_VALIDO, dadosAtualizados);

		assertEquals("978-NOVO-ISBN", resultado.getIsbn());
		verify(repository, times(1)).salvar(livroValido);
	}

	@Test
	void atualizarLivro_deveLancarExcecaoSeNovoIsbnDuplicado() {
		Livro livroExistente = new Livro("Livro 1", "Autor", "978-111", 2000, 10);
		when(repository.buscarPorId(ID_VALIDO)).thenReturn(Optional.of(livroExistente));
		Livro dadosDuplicados = new Livro("Livro 1 Atualizado", "Autor", "978-222", 2020, 5);
		when(repository.existePorIsbn("978-222")).thenReturn(true);
		assertThrows(IsbnDuplicadoException.class, () -> {
			service.atualizarLivro(ID_VALIDO, dadosDuplicados);
		});
		verify(repository, never()).salvar(any(Livro.class));
	}
	@Test
	void removerLivro_deveRemoverLivroExistente() {
		when(repository.buscarPorId(ID_VALIDO)).thenReturn(Optional.of(livroValido));
		service.removerLivro(ID_VALIDO);
		verify(repository, times(1)).deletar(ID_VALIDO);
	}
}
