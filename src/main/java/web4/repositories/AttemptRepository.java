package web4.repositories;

import org.springframework.data.repository.CrudRepository;
import web4.model.Attempt;

public interface AttemptRepository extends CrudRepository<Attempt, Long> {
}
