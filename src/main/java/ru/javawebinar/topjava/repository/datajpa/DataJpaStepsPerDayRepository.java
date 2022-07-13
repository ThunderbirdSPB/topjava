package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.StepsPerDay;
import ru.javawebinar.topjava.repository.StepsPerDayRepository;

import java.time.LocalDate;
import java.util.List;

@Repository
public class DataJpaStepsPerDayRepository implements StepsPerDayRepository {
    private final CrudStepsPerDayRepository stepsPerDayRepo;
    private final CrudUserRepository userRepo;

    public DataJpaStepsPerDayRepository(CrudStepsPerDayRepository stepsPerDayRepo, CrudUserRepository userRepo) {
        this.stepsPerDayRepo = stepsPerDayRepo;
        this.userRepo = userRepo;
    }

    @Override
    public StepsPerDay save(int userId, StepsPerDay steps) {
        if(!steps.isNew() && get(steps.getId(), userId) == null)
            return null;
        steps.setUser(userRepo.getById(userId));
        return stepsPerDayRepo.save(steps);
    }

    @Override
    public boolean delete(int stepsId, int userId) {
        return stepsPerDayRepo.delete(stepsId, userId) != 0;
    }

    @Override
    public StepsPerDay get(int stepsId, int userId) {
        return stepsPerDayRepo.findById(stepsId)
                .filter(stepsPerDay -> stepsPerDay.getUser().getId().equals(userId))
                .orElse(null);
    }

    @Override
    public List<StepsPerDay> getAll(int userId) {
        return stepsPerDayRepo.getAll(userId);
    }

    @Override
    public List<StepsPerDay> getBetweenHalfOpen(LocalDate startDate, LocalDate endDate, int numberOfSteps, int userId) {
        return stepsPerDayRepo.getBetweenHalfOpen(startDate, endDate, numberOfSteps, userId);
    }
}
