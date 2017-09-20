package br.edu.faculdadedelta.bibliotecaapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.faculdadedelta.bibliotecaapi.model.Genero;

public interface GeneroRepository 
				extends JpaRepository<Genero, Long> {

}
