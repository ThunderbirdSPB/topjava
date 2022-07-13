package ru.javawebinar.topjava.repository.jpa;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ru.javawebinar.topjava.model.StepsPerDay;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.StepsPerDayRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDate;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public class JpaStepsPerDayRepository implements StepsPerDayRepository {
    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public StepsPerDay save(int userId, StepsPerDay steps) {
        steps.setUser(em.getReference(User.class, userId));
        if(steps.isNew()){
            em.persist(steps);
            em.flush();
            return steps;
        }else if (get(steps.id(), userId) == null) {
            return null;
        }
        return em.merge(steps);
    }

    @Override
    @Transactional
    public boolean delete(int stepsId, int userId) {
        return em.createNamedQuery(StepsPerDay.DELETE)
                .setParameter("userId", userId)
                .setParameter("stepsId", stepsId).executeUpdate() != 0;
    }

    @Override
    public StepsPerDay get(int stepsId, int userId) {
        StepsPerDay steps = em.find(StepsPerDay.class, stepsId);
        return steps != null && steps.getUser().getId() == userId ? steps : null;
    }

    @Override
    public List<StepsPerDay> getAll(int userId) {
        return em.createNamedQuery(StepsPerDay.ALL_SORTED, StepsPerDay.class)
                .setParameter("userId", userId)
                .getResultList();
    }

    @Override
    public List<StepsPerDay> getBetweenHalfOpen(LocalDate startDate, LocalDate endDate, int numberOfSteps, int userId) {
        return em.createNamedQuery(StepsPerDay.GET_BETWEEN, StepsPerDay.class)
                .setParameter("userId", userId)
                .setParameter("startDate", startDate)
                .setParameter("endDate", endDate)
                .setParameter("numberOfSteps", numberOfSteps)
                .getResultList();
    }
}
