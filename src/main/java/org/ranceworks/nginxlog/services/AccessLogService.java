package org.ranceworks.nginxlog.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.SingularAttribute;

import org.ranceworks.nginxlog.entities.AccessLog;
import org.ranceworks.nginxlog.entities.AccessLog_;
import org.ranceworks.nginxlog.repositories.AccessLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;

@Service
public class AccessLogService {
	@Autowired
	private AccessLogRepository repository;

	public Page<AccessLog> findAll(Pageable pageable) {

		return repository.findAll(pageable);
	}

	public Page<AccessLog> findAll(Optional<String> countryCode, Optional<String> uri, Optional<String> city, Date date,
			Pageable pageable) {
		final List<Optional<Specification<AccessLog>>> list = new ArrayList<>();
		list.add(buildEqualExpr(AccessLog_.dateGmt, Optional.of(new java.sql.Date(date.getTime()))));
		list.add(buildEqualExpr(AccessLog_.countryCode, countryCode));
		list.add(buildEqualExpr(AccessLog_.uri, uri));
		list.add(buildEqualExpr(AccessLog_.city, city));
		final List<Specification<AccessLog>> specs = list.stream().filter(a -> a.isPresent()).map(f -> f.get())
				.collect(Collectors.toList());

		return findBySpecs(specs, pageable);
	}

	public Page<AccessLog> findAll(Optional<String> countryCode, Optional<String> uri, Optional<String> city,
			Optional<Date> fromDate, Optional<Date> toDate, Pageable pageable) {
		final List<Optional<Specification<AccessLog>>> list = new ArrayList<>();
		list.add(buildEqualExpr(AccessLog_.countryCode, countryCode));
		list.add(buildEqualExpr(AccessLog_.uri, uri));
		list.add(buildEqualExpr(AccessLog_.city, city));

		final List<Specification<AccessLog>> specs = list.stream().filter(a -> a.isPresent()).map(f -> f.get())
				.collect(Collectors.toList());

		if (fromDate.isPresent()) {
			specs.add(new Specification<AccessLog>() {
				@Override
				public Predicate toPredicate(Root<AccessLog> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
					return cb.greaterThan(root.get(AccessLog_.dateGmt), fromDate.get());
				}
			});
		}
		if (toDate.isPresent()) {
			specs.add(new Specification<AccessLog>() {

				@Override
				public Predicate toPredicate(Root<AccessLog> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
					return cb.lessThan(root.get(AccessLog_.dateGmt), toDate.get());
				}

			});
		}
		return findBySpecs(specs, pageable);
	}

	private Page<AccessLog> findBySpecs(List<Specification<AccessLog>> specs, Pageable pageable) {
		if (specs.isEmpty()) {
			return repository.findAll(new PageRequest(0, 0));
		}
		Specifications<AccessLog> where = Specifications.where(specs.get(0));
		if (specs.size() > 1) {
			for (Specification<AccessLog> spec : specs.subList(1, specs.size())) {
				where = where.and(spec);
			}
		}
		return repository.findAll((Specification<AccessLog>) where, pageable);
	}

	private <A> Optional<Specification<AccessLog>> buildEqualExpr(SingularAttribute<AccessLog, A> attr,
			Optional<A> val) {
		if (!val.isPresent()) {
			return Optional.empty();
		}
		return Optional.of(new Specification<AccessLog>() {
			@Override
			public Predicate toPredicate(Root<AccessLog> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				return cb.equal(root.get(attr), val.get());
			}
		});
	}
}
