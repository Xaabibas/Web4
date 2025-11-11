package web4.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;

import javax.faces.context.FacesContext;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;

public class AttemptKeeper {
    //    @Resource
    //    private UserTransaction userTransaction;
    //
    //    @PersistenceContext
    //    private EntityManager entityManager;
    //
    //    private final Checker checker = new Checker();
    //    private final ObjectMapper mapper = new ObjectMapper();
    //    private List<Attempt> attempts;
    //
    //    @Transactional
    //    public void add(Attempt attempt) {
    //        try {
    //            userTransaction.begin();
    //
    //            processAttempt(attempt);
    //            entityManager.persist(attempt);
    //            attempts.add(0, attempt);
    //
    //            userTransaction.commit();
    //        } catch (Exception e) {
    //            try {
    //                if (userTransaction.getStatus() == jakarta.transaction.Status.STATUS_ACTIVE ||
    //                        userTransaction.getStatus() == jakarta.transaction.Status.STATUS_MARKED_ROLLBACK) {
    //                    userTransaction.rollback();
    //                }
    //            } catch (Exception rollbackEx) {
    //                rollbackEx.printStackTrace();
    //            }
    //            e.printStackTrace();
    //        }
    //    }
    //
    //    @Transactional
    //    public void clear() {
    //        try {
    //            userTransaction.begin();
    //
    //            entityManager.createNativeQuery("TRUNCATE TABLE attempts").executeUpdate();
    //            attempts.clear();
    //
    //            userTransaction.commit();
    //        } catch (Exception e) {
    //            try {
    //                if (userTransaction.getStatus() == jakarta.transaction.Status.STATUS_ACTIVE ||
    //                        userTransaction.getStatus() == jakarta.transaction.Status.STATUS_MARKED_ROLLBACK) {
    //                    userTransaction.rollback();
    //                }
    //            } catch (Exception rollbackEx) {
    //                rollbackEx.printStackTrace();
    //            }
    //            e.printStackTrace();
    //        }
    //    }
    //
    //    public void processAttempt(Attempt attempt) {
    //        long workStart = System.nanoTime();
    //        LocalTime now = LocalTime.now();
    //        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
    //        attempt.setStart(now.format(formatter));
    //        attempt.setResult(checker.check(attempt));
    //        long workEnd = System.nanoTime();
    //        attempt.setWorkTime((workEnd - workStart) / 1_000);
    //    }
    //
    //    public void addFromJS() {
    //        String x = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("x");
    //        String y = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("y");
    //        String r = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("r");
    //
    //        Point point = new Point(
    //                Double.parseDouble(x),
    //                Double.parseDouble(y),
    //                Double.parseDouble(r)
    //        );
    //        Attempt attempt = new Attempt();
    //        attempt.setPoint(point);
    //
    //        add(attempt);
    //    }
    //
    //    public List<Attempt> selectAttemptsList() {
    //        return entityManager.createQuery("SELECT a FROM web4.model.Attempt a", Attempt.class).getResultList();
    //    }
    //
    //    public List<Attempt> getAttempts() {
    //        if (attempts == null) {
    //            attempts = selectAttemptsList();
    //        }
    //        return attempts;
    //    }

    private final Checker checker = new Checker();
    private final ObjectMapper mapper = new ObjectMapper();
    private List<Attempt> attempts;

    public void add(Attempt attempt) {
        processAttempt(attempt);
        attempts.add(0, attempt);
    }

    @Transactional
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

    public void addFromJS() {
        String x = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("x");
        String y = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("y");
        String r = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("r");

        Point point = new Point(
                Double.parseDouble(x),
                Double.parseDouble(y),
                Double.parseDouble(r)
        );
        Attempt attempt = new Attempt();
        attempt.setPoint(point);

        add(attempt);
    }

    public List<Attempt> getAttempts() {
        if (attempts == null) {
            attempts = new LinkedList<>();
        }
        return attempts;
    }
}
