package web4.attempt;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface AttemptRepository extends CrudRepository<Attempt, Long> {
    @Modifying
    @Transactional
    @Query("DELETE FROM attempts WHERE username = :username")
    void deleteAllByUsername(String username);

    @Query("SELECT * FROM attempts WHERE username = :username ORDER BY id DESC OFFSET :skip LIMIT :size")
    List<Attempt> getSliceByUsername(int skip, int size, String username);

    @Query("SELECT COUNT(*) FROM attempts WHERE username = :username")
    long countByUsername(String username);

    @Query("SELECT x, y, result FROM attempts WHERE username = :username")
    List<GraphPointDTO> getPointsForGraph(String username);

    List<Attempt> findAllByUsername(String username);
}
