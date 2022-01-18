package ru.javawebinar.topjava.service.jpa;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.AbstractMealServiceTest;
import ru.javawebinar.topjava.service.MealService;

import javax.persistence.PersistenceException;

import static org.junit.Assert.assertThrows;
import static ru.javawebinar.topjava.MealTestData.meal1;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

@ActiveProfiles("jpa")
public class JpaMealServiceTest extends AbstractMealServiceTest {
    @Autowired
    private MealService service;

    @Test
    @Transactional
    @Override
    public void duplicateDateTimeCreate() {
        assertThrows(PersistenceException.class, () ->
                service.create(new Meal(null, meal1.getDateTime(), "duplicate", 100), USER_ID)
        );
    }
}
