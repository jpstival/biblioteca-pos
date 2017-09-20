package br.edu.faculdadedelta.bibliotecaapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.faculdadedelta.bibliotecaapi.model.Livro;

public interface LivroRepository 
				extends JpaRepository<Livro, Long> {

}
