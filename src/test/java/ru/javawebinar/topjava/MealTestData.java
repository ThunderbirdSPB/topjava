package ru.javawebinar.topjava;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import ru.javawebinar.topjava.UserTestData;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.jdbc.JdbcMealRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.List;

public class MealTestData {
    public final static Meal ADMINS_BREAKFAST = new Meal(100027, LocalDateTime.of(2021,5,24, 12,40,46),
            "Breakfast",1027);
    public final static Meal NOT_EXISTING_MEAL = new Meal(1, LocalDateTime.now(), "Doesn't exist",1027);
    public static LocalDate BEFORE = LocalDate.MIN;
    public static LocalDate AFTER = LocalDate.MAX;

    public static Meal getNewMeal(){
        return new Meal(null, LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS), "new desc", 1111);
    }

    public static Meal getUpdatedMeal(){
        Meal updatedMeal = new Meal(ADMINS_BREAKFAST);
        updatedMeal.setCalories(3333);
        updatedMeal.setDateTime(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
        updatedMeal.setDescription("updated desc");
        return updatedMeal;
    }

    public static Meal[] getMealsBetween(){
        return new Meal[1];
    }

    public static Meal[] getAllMeals(){
        return new Meal[1];
    }
}
