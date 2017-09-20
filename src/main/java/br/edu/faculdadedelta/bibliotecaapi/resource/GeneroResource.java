package br.edu.faculdadedelta.bibliotecaapi.resource;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.edu.faculdadedelta.bibliotecaapi.model.Genero;
import br.edu.faculdadedelta.bibliotecaapi.service.GeneroService;

@RestController
@RequestMapping("/generos")
public class GeneroResource {

	@Autowired
	private GeneroService service;
	
	@PostMapping
	public ResponseEntity<Genero> inserir(
			@RequestBody @Valid Genero genero) {
		Genero generoCadastrado = this.service.inserir(genero);
		
		URI uri = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(generoCadastrado.getId())
				.toUri();
		
		return ResponseEntity.created(uri)
				.body(generoCadastrado);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Genero> alterar(
			@RequestBody @Valid Genero genero, 
			@PathVariable("id") Long id) {
		
		genero.setId(id);
		
		this.service.alterar(genero);
	
		return ResponseEntity.ok(genero);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Genero> pesquisarPorId(
			@PathVariable("id") Long id) {
		
		Genero genero = this.service.pesquisaPorId(id);
		
		return ResponseEntity.ok(genero);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> exlcuir(
			@PathVariable("id") Long id) {
		this.service.excluir(id);
		return ResponseEntity.noContent().build();
	}
	
	@GetMapping
	public ResponseEntity<List<Genero>> listar() {
		List<Genero> generos = this.service.listar();
		return ResponseEntity.ok(generos);
	}
	
}
