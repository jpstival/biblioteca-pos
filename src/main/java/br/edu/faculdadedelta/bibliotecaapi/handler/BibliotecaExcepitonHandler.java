package br.edu.faculdadedelta.bibliotecaapi.handler;

import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class BibliotecaExcepitonHandler extends ResponseEntityExceptionHandler {

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		List<DetalheErro> erros = criarListaDeErro(ex.getBindingResult());

		return super.handleExceptionInternal(ex, erros, headers, status, request);
	}
	
	@ExceptionHandler(EmptyResultDataAccessException.class)
	public ResponseEntity<Object> handleEmptyResultDataAccess(
			EmptyResultDataAccessException ex,
			 WebRequest request) {
	
		
	    DetalheErro erro = 
	    		new DetalheErro("Recurso não encontrado",
						ex.toString());
		
		return super.handleExceptionInternal(ex, erro, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
	}

	private List<DetalheErro> criarListaDeErro(BindingResult bindingResult) {
		List<DetalheErro> erros = new ArrayList<>();

		bindingResult
		.getAllErrors()
		.forEach(f -> 
		erros.add(new DetalheErro(
				f.getDefaultMessage(), f.toString())));

		return erros;
	}

	public static class DetalheErro {
		private String mensagemUsuario;
		private String mensagemDesevolvedor;

		public DetalheErro(String mensagemUsuario, String mensagemDesevolvedor) {
			this.mensagemUsuario = mensagemUsuario;
			this.mensagemDesevolvedor = mensagemDesevolvedor;
		}

		public String getMensagemUsuario() {
			return mensagemUsuario;
		}

		public String getMensagemDesevolvedor() {
			return mensagemDesevolvedor;
		}

	}
}
