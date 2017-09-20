package br.edu.faculdadedelta.bibliotecaapi.query;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.edu.faculdadedelta.bibliotecaapi.model.Genero;

public interface GeneroRepositoryQuery {

	public Page<Genero> filtrar(Genero genero, Pageable pageable);
}
