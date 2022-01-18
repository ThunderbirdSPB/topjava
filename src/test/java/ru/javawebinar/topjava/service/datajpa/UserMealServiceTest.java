package ru.javawebinar.topjava.service.datajpa;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.ActiveDbProfileResolver;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.UserTestData;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.service.UserMealService;

import java.util.Collection;
import java.util.Map;

import static org.junit.Assert.*;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.*;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
@ActiveProfiles(resolver = ActiveDbProfileResolver.class)
public class UserMealServiceTest {

    @Autowired
    private UserMealService service;

    @Test
    public void getUserMeals() {
        Map<User, Collection<Meal>> userMeals = service.getUserMeals(USER_ID);
        User user = userMeals.keySet().stream().findFirst().orElseThrow();
        Collection<Meal> meals = userMeals.get(user);
        USER_MATCHER.assertMatch(user, UserTestData.user);
        MEAL_MATCHER.assertMatch(meals, MealTestData.meals);
    }

    @Test
    public void getUserMeal() {
        Map<User, Meal> userMeal = service.getUserMeal(USER_ID, MEAL1_ID);
        User user = userMeal.keySet().stream().findFirst().orElseThrow();
        Meal meal = userMeal.get(user);

        USER_MATCHER.assertMatch(user, UserTestData.user);
        MEAL_MATCHER.assertMatch(meal, meal1);
    }
}