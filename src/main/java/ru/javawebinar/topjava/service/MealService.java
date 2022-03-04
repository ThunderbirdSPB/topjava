package ru.javawebinar.topjava.service;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import java.util.Collection;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.util.List;

import static ru.javawebinar.topjava.util.DateTimeUtil.atStartOfDayOrMin;
import static ru.javawebinar.topjava.util.DateTimeUtil.atStartOfNextDayOrMax;
import static ru.javawebinar.topjava.util.validation.ValidationUtil.*;


@Service
public class MealService {
    private final MealRepository repository;

    public MealService(MealRepository repository) {
        this.repository = repository;
    }

    @CacheEvict(value = "meals", allEntries = true)
    public void update(Meal meal, int userId) {
        Assert.notNull(meal, "meal must not be null");
        checkNotFoundWithId(repository.save(userId, meal), meal.id());
    }

    @CacheEvict(value = "meals", allEntries = true)
    @Transactional
    public void update(MealTo mealTo, int userId) {
        Meal meal = get(mealTo.id(), userId);
        Meal updatedMeal = MealsUtil.updateFromTo(meal, mealTo);
        repository.save(userId, updatedMeal);   // !! need only for JDBC implementation
    }

    @CacheEvict(value = "meals", allEntries = true)
    public Meal create(Meal meal, int userId) {
        Assert.notNull(meal, "meal must not be null");
        return repository.save(userId, meal);
    }

    @CacheEvict(value = "meals", allEntries = true)
    public void delete(int mealId, int userId) {
        checkNotFoundWithId(repository.delete(mealId, userId), mealId);
    }

    public Meal get(int mealId, int userId) {
        return checkNotFoundWithId(repository.get(mealId, userId), mealId);
    }

    public List<Meal> getBetweenInclusive(@Nullable LocalDate startDate, @Nullable LocalDate endDate, int userId) {
        return repository.getBetweenHalfOpen(atStartOfDayOrMin(startDate), atStartOfNextDayOrMax(endDate), userId);
    }

    @Cacheable("meals")
    public Collection<Meal> getAll(int userId) {
        return repository.getAll(userId);
    }

    public Meal getWithUser(int id, int userId) {
        return checkNotFoundWithId(repository.getWithUser(id, userId), id);
    }
}