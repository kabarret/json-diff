package br.com.kauebarreto.jsondiff.repository;

import br.com.kauebarreto.jsondiff.model.Diff;
import org.springframework.data.repository.CrudRepository;

public interface DiffRepository extends CrudRepository<Diff, Long> {
}
