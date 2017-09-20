package br.edu.faculdadedelta.bibliotecaapi.resource;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.edu.faculdadedelta.bibliotecaapi.model.Livro;
import br.edu.faculdadedelta.bibliotecaapi.service.LivroService;

@RestController
@RequestMapping("/livros")
public class LivroResource {

	@Autowired
	private LivroService service;
	
	@PostMapping
	public ResponseEntity<Livro> inserir(@RequestBody @Valid Livro livro) {
		
		Livro livroCadastrado = this.service.inserir(livro);
		
		URI uri = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(livroCadastrado.getId())
				.toUri();
		
		return ResponseEntity.created(uri).body(livroCadastrado);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Livro> alterar(
			@RequestBody @Valid Livro livro,
			@PathVariable("id") Long id) {
		
		livro.setId(id);
		
		Livro livroAlterado = this.service.alterar(livro);
		
		return ResponseEntity.ok(livroAlterado);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> excluir(
			@PathVariable("id") Long id) {
		this.service.excluir(id);
		
		return ResponseEntity.noContent().build();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Livro> pesquisarPorId(
			@PathVariable("id") Long id) {
		Livro livroPesquisado = this.service.pesquisarPorId(id);
		
		return ResponseEntity.ok(livroPesquisado);
	}
	
	@CrossOrigin
	@GetMapping
	public ResponseEntity<List<Livro>> listar() {
		List<Livro> livros = this.service.listar();
		
		return ResponseEntity.ok(livros);
	}
}
