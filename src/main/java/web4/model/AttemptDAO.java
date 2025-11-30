package web4.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web4.repositories.AttemptRepository;

import java.util.List;

@Service
public class AttemptDAO {
    @Autowired
    private AttemptRepository repository;

    public void save(Attempt attempt) {
        repository.save(attempt);
    }

    public void clear() {
        repository.deleteAll();
    }

    public List<Attempt> getAll() {
        return (List<Attempt>) repository.findAll();
    }
}
