package br.edu.faculdadedelta.bibliotecaapi.query;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;

import br.edu.faculdadedelta.bibliotecaapi.model.Genero;

public class GeneroRepositoryQueryImpl implements GeneroRepositoryQuery {

	@PersistenceContext
	private EntityManager manager;

	@Override
	public Page<Genero> filtrar(Genero generoFilter, Pageable pageable) {

		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Genero> criteria = builder.createQuery(Genero.class);
		Root<Genero> root = criteria.from(Genero.class);

		Predicate[] predicates = criarRestricoes(generoFilter, builder, root);
		criteria.where(predicates);

		TypedQuery<Genero> query = manager.createQuery(criteria);

		adicionarRestricoesDePaginacao(query, pageable);

		return new PageImpl<>(query.getResultList(), pageable, total(generoFilter));

	}
	
	private Predicate[] criarRestricoes(Genero generoFilter, CriteriaBuilder builder,
			Root<Genero> root) {
		List<Predicate> predicates = new ArrayList<>();

		if (!StringUtils.isEmpty(generoFilter.getDescricao())) {
			predicates.add(builder.like(builder.lower(root.get("descricao")),
					"%" + generoFilter.getDescricao().toLowerCase() + "%"));
		}

		return predicates.toArray(new Predicate[predicates.size()]);
	}

	private Long total(Genero generoFilter) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
		Root<Genero> root = criteria.from(Genero.class);

		Predicate[] predicates = criarRestricoes(generoFilter, builder, root);
		criteria.where(predicates);

		criteria.select(builder.count(root));
		return manager.createQuery(criteria).getSingleResult();
	}

	private void adicionarRestricoesDePaginacao(TypedQuery<?> query, Pageable pageable) {
		int paginaAtual = pageable.getPageNumber();
		int totalRegistrosPorPagina = pageable.getPageSize();
		int primeiroRegistroDaPagina = paginaAtual * totalRegistrosPorPagina;

		query.setFirstResult(primeiroRegistroDaPagina);
		query.setMaxResults(totalRegistrosPorPagina);
	}


}
