package br.edu.faculdadedelta.bibliotecaapi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import br.edu.faculdadedelta.bibliotecaapi.model.Genero;
import br.edu.faculdadedelta.bibliotecaapi.repository.GeneroRepository;

@Service
public class GeneroService {

	@Autowired
	private GeneroRepository repository;

	public Genero alterar(Genero genero) {
		
		pesquisaPorId(genero.getId());
		
		return this.repository.save(genero);
	}

	public Genero pesquisaPorId(Long id) {
		
		Genero generoPesquisado = this.repository.findOne(id);

		if (generoPesquisado == null) {
			throw new EmptyResultDataAccessException(0);
		}
		
		return generoPesquisado;
	}
	
	public Genero inserir(Genero genero) {
		return this.repository.save(genero);
	}
	
	public void excluir(Long id) {
		this.repository.delete(id);
	}
	
	public List<Genero> listar() {
		return this.repository.findAll();
	}
}

