package ru.javawebinar.topjava.repository;


import ru.javawebinar.topjava.model.StepsPerDay;
import java.time.LocalDate;
import java.util.List;

public interface StepsPerDayRepository {
    // null if updated stepsPerDay do not belong to userId
    StepsPerDay save(int userId, StepsPerDay steps);

    // false if meal do not belong to userId
    boolean delete(int stepsId, int userId);

    // null if meal do not belong to userId
    StepsPerDay get(int stepsId, int userId);

    // ORDERED date desc
    List<StepsPerDay> getAll(int userId);

    // ORDERED date desc
    List<StepsPerDay> getBetweenHalfOpen(LocalDate startDate, LocalDate endDate, int numberOfSteps, int userId);
}
