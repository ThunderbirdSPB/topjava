package ru.javawebinar.topjava.repository.inmemory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class InMemoryMealRepository implements MealRepository {
    private final Logger log = LoggerFactory.getLogger(getClass());
    // map: user - meal
    private final Map<Integer, Map<Integer, Meal>> repository = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.meals.forEach(meal -> save(SecurityUtil.authUserId(), meal));
    }

    @Override
    public Meal save(int userId, Meal meal) {
        log.info("save() userId {}, meal {}", userId, meal);
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());

            if (!repository.containsKey(userId))
                repository.put(userId, new ConcurrentHashMap<>());

            repository.get(userId).put(meal.getId(), meal);

            return meal;
        }

        // handle case: update, but not present in storage
        return repository.get(userId).computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
    }

    @Override
    public boolean delete(int userId, int mealId) {
        log.info("delete() userId {}, mealId {}", userId, mealId);
        Map<Integer, Meal> meals = repository.get(userId);
        if (meals != null)
            return meals.remove(mealId) != null;
        return false;
    }

    @Override
    public Meal get(int userId, int mealId) {
        log.info("get() userId {}, mealId {}", userId, mealId);
        Map<Integer, Meal> meals = repository.get(userId);
        if (meals != null)
            return meals.get(mealId);
        return null;
    }

    @Override
    public Collection<Meal> getAll(int userId) {
        log.info("getAll() userId {}", userId);
        Map<Integer, Meal> meals = repository.get(userId);
        if (meals != null)
            return meals.values();
        return Collections.emptyList();
    }
}

