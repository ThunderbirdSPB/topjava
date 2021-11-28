package ru.javawebinar.topjava.service;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;


import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.util.Collection;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.Assert.assertThrows;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.*;
import static org.junit.Assert.assertThrows;


@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {
    @Autowired
    private MealService mealService;

    @Test
    public void update() {
        Meal updated = getUpdatedMeal();
        mealService.update(updated, ADMIN_ID);
        assertThat(mealService.get(ADMIN_ID, updated.getId())).usingRecursiveComparison().isEqualTo(updated);
    }

    @Test
    public void updateNotExistingMeal(){
        assertThatThrownBy(() -> mealService.update(NOT_EXISTING_MEAL, ADMIN_ID)).isInstanceOf(NotFoundException.class);
    }

    @Test
    public void updateMealWithWrongUserId(){
        assertThatThrownBy(() -> mealService.update(ADMINS_BREAKFAST, USER_ID)).isInstanceOf(NotFoundException.class);
    }

    @Test
    public void create() {
        Meal created = mealService.create(getNewMeal(), ADMIN_ID);
        Meal newMeal = getNewMeal();
        newMeal.setId(created.getId());

        assertThat(created).usingRecursiveComparison().isEqualTo(newMeal);
        assertThat(created).usingRecursiveComparison().isEqualTo(mealService.get(ADMIN_ID, created.getId()));
    }

    @Test
    public void createWithDuplicateDateTime() {
       Meal duplicate = new Meal(ADMINS_BREAKFAST);
       duplicate.setId(null);
       assertThrows(DataAccessException.class, () -> mealService.create(duplicate, ADMIN_ID));
    }

    @Test
    public void deleteExistingMealWithRightUserId() {
        assertThat(mealService.get(ADMIN_ID, ADMINS_BREAKFAST.getId())).isNotNull();
        mealService.delete(ADMIN_ID, ADMINS_BREAKFAST.getId());
        assertThatThrownBy(() -> mealService.get(ADMIN_ID, ADMINS_BREAKFAST.getId())).isInstanceOf(NotFoundException.class);
    }

    @Test
    public void deleteNotExistingMeal() {
        assertThatThrownBy(() -> mealService.delete(ADMIN_ID, NOT_EXISTING_MEAL.getId())).isInstanceOf(NotFoundException.class);
    }

    @Test
    public void deleteExistingMealWithWrongUserId() {
        assertThatThrownBy(() -> mealService.delete(USER_ID, ADMINS_BREAKFAST.getId())).isInstanceOf(NotFoundException.class);
    }

    @Test
    public void getMealWithRightUserId() {
        assertThat(ADMINS_BREAKFAST).usingRecursiveComparison().isEqualTo(mealService.get(ADMIN_ID, ADMINS_BREAKFAST.getId()));
    }

    @Test
    public void getMealWithWrongUserId() {
        assertThatThrownBy(() -> mealService.get(USER_ID, ADMINS_BREAKFAST.getId())).isInstanceOf(NotFoundException.class);
    }

    @Test
    @Ignore
    public void getBetweenInclusive() {
        List<Meal> mealsBetween = mealService.getBetweenInclusive(BEFORE, AFTER, ADMIN_ID);
        assertThat(mealsBetween).containsOnly(getMealsBetween());
    }

    @Test
    @Ignore
    public void getAll() {
        Collection<Meal> allMeals = mealService.getAll(ADMIN_ID);
        assertThat(allMeals).containsOnly(getAllMeals());
    }
}