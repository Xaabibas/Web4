package web4.model;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;

public class AttemptKeeper {
    private final Checker checker = new Checker();
    private final ObjectMapper mapper = new ObjectMapper();
    private List<Attempt> attempts;

    public void add(Attempt attempt) {
        processAttempt(attempt);
        attempts.add(0, attempt);
    }

    public void clear() {
        attempts.clear();
    }

    public void processAttempt(Attempt attempt) {
        long workStart = System.nanoTime();
        LocalTime now = LocalTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        attempt.setStart(now.format(formatter));
        attempt.setResult(checker.check(attempt));
        long workEnd = System.nanoTime();
        attempt.setWorkTime((workEnd - workStart) / 1_000);
    }

    public List<Attempt> getAttempts() {
        if (attempts == null) {
            attempts = new LinkedList<>();
        }
        return attempts;
    }
}
