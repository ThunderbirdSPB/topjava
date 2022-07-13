package ru.javawebinar.topjava.service;

import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ru.javawebinar.topjava.model.StepsPerDay;
import ru.javawebinar.topjava.repository.StepsPerDayRepository;
import ru.javawebinar.topjava.to.StepsPerDayTo;

import java.time.LocalDate;
import java.util.List;

import static ru.javawebinar.topjava.util.DateTimeUtil.atStartOfDayOrMin;
import static ru.javawebinar.topjava.util.DateTimeUtil.atStartOfNextDayOrMax;
import static ru.javawebinar.topjava.util.StepsPerDayUtil.*;
import static ru.javawebinar.topjava.util.validation.ValidationUtil.checkNotFoundWithId;

@Service
public class StepsPerDayService {
    private final StepsPerDayRepository repository;

    public StepsPerDayService(StepsPerDayRepository repository) {
        this.repository = repository;
    }

    public StepsPerDayTo update(StepsPerDay steps, int userId) {
        Assert.notNull(steps, "stepsPerDay must not be null");
        return getTo(checkNotFoundWithId(repository.save(userId, steps), steps.id()));
    }

    public StepsPerDayTo create(StepsPerDay steps, int userId) {
        Assert.notNull(steps, "stepsPerDay must not be null");
        return getTo(repository.save(userId, steps));
    }

    public void delete(int stepsId, int userId) {
        checkNotFoundWithId(repository.delete(stepsId, userId), stepsId);
    }

    public StepsPerDayTo get(int stepsId, int userId) {
        return getTo(checkNotFoundWithId(repository.get(stepsId, userId), stepsId));
    }

    public List<StepsPerDayTo> getBetweenInclusive(@Nullable LocalDate startDate, @Nullable LocalDate endDate,
                                                    int numberOfSteps, int userId) {
        return getTos(repository.getBetweenHalfOpen(
                atStartOfDayOrMin(startDate).toLocalDate(), atStartOfNextDayOrMax(endDate).toLocalDate(), numberOfSteps,
                userId));
    }

    public List<StepsPerDayTo> getAll(int userId) {
        return getTos(repository.getAll(userId));
    }
}
