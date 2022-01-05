package ru.javawebinar.topjava.repository;

import java.time.LocalDateTime;
import ru.javawebinar.topjava.model.Meal;

import java.util.Collection;
import java.util.List;

public interface MealRepository {
    // null if updated meal do not belong to userId
    Meal save(int userId, Meal meal);

    // false if meal do not belong to userId
    boolean delete(int mealId, int userId);

    // null if meal do not belong to userId
    Meal get(int mealId, int userId);

    // ORDERED dateTime desc
    Collection<Meal> getAll(int userId);

    // ORDERED dateTime desc
    List<Meal> getBetweenHalfOpen(LocalDateTime startDateTime, LocalDateTime endDateTime, int userId);
}
