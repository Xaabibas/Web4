package web4.attempt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web4.exceptions.UnprocessableEntityException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class AttemptService {
    @Autowired
    private AttemptRepository repository;
    @Autowired
    private Checker checker;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");

    public Attempt process(Point point, String username) {
        if (!isValidR(point.getR())) {
            throw new UnprocessableEntityException("Значение поля R должно лежать в промежутке [-5; 3]");
        }
        LocalDateTime date = LocalDateTime.now();
        long start = System.nanoTime();
        Attempt attempt = new Attempt();
        attempt.setPoint(point);
        attempt.setStart(date.format(formatter));
        attempt.setResult(checker.check(point));
        long end = System.nanoTime();
        attempt.setWorkTime((end - start) / 1_000);
        attempt.setUsername(username);
        save(attempt);
        System.out.println(attempt.toString());
        return attempt;
    }

    private boolean isValidR(double r) {
        return r >= -5 && r <= 3;
    }

    public void save(Attempt attempt) {
        repository.save(attempt);
    }

    public void clear(String username) {
        repository.deleteAllByUsername(username);
    }

    public List<Attempt> getSlice(int page, int size, String username) {
        return repository.getSliceByUsername(page * size, size, username);
    }

    public long count(String username) {
        return repository.countByUsername(username);
    }

    public List<Attempt> getAll() {
        return (List<Attempt>) repository.findAll();
    }
}
