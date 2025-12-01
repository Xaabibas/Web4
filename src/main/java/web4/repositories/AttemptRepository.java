package web4.repositories;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import web4.model.Attempt;

import java.util.List;

public interface AttemptRepository extends CrudRepository<Attempt, Long> {
    @Query("SELECT * FROM attempts ORDER BY id DESC OFFSET :skip LIMIT :size")
    List<Attempt> getSlice(int skip, int size);
}
