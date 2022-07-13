package ru.javawebinar.topjava.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.javawebinar.topjava.model.StepsPerDay;
import ru.javawebinar.topjava.to.StepsPerDayTo;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import javax.validation.ConstraintViolationException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.javawebinar.topjava.StepsPerDayTestData.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;
import static ru.javawebinar.topjava.UserTestData.USER_ID;


public abstract class AbstractStepsPerDayServiceTest extends AbstractServiceTest {
    @Autowired
    StepsPerDayService service;

    @Test
    void createWithValidData_success() {
        StepsPerDayTo created = service.create(getNew(), USER_ID);
        StepsPerDayTo newSteps = getToFromNew();
        newSteps.setId(created.getId());
        STEPS_TO_MATCHER.assertMatch(newSteps, created);
    }

    @Test
    void createWithNullStepsPerDay_exception() {
        assertThrows(IllegalArgumentException.class, () -> service.create(null, USER_ID));
    }

    @Test
    void createWithNegativeNumberOfSteps_exception() {
        StepsPerDay steps = getNew();
        steps.setNumberOfSteps(-1);
        validateRootCause(ConstraintViolationException.class, () -> service.create(steps, USER_ID));
    }

    @Test
    void deleteWithExistingIds_success(){
        service.delete(STEP_ID, ADMIN_ID);
        assertThrows(NotFoundException.class, () -> service.delete(STEP_ID, USER_ID));
    }

    @Test
    void deleteWithNotExistingStepIdAndUserId_exception(){
        assertThrows(NotFoundException.class, () -> service.delete(NOT_EXISTING_ID, USER_ID));
        assertThrows(NotFoundException.class, () -> service.delete(STEP_ID, NOT_EXISTING_ID));
        assertThrows(NotFoundException.class, () -> service.delete(NOT_EXISTING_ID, NOT_EXISTING_ID));
    }

    @Test
    void getByExistingIds_success(){
        STEPS_TO_MATCHER.assertMatch(service.get(STEP_ID, ADMIN_ID), STEPS);
    }

    @Test
    void getByNotExistingStepIdAndUserId_exception(){
        assertThrows(NotFoundException.class, () -> service.get(NOT_EXISTING_ID, USER_ID));
        assertThrows(NotFoundException.class, () -> service.get(STEP_ID, NOT_EXISTING_ID));
        assertThrows(NotFoundException.class, () -> service.get(NOT_EXISTING_ID, NOT_EXISTING_ID));
    }

    @Test
    void getAllByExistingUserId_success(){
        STEPS_TO_MATCHER.assertMatch(service.getAll(ADMIN_ID), ALL_STEPS_TO);
    }

    @Test
    void getAllByNotExistingUserId_emptyCollection(){
        assertEquals(service.getAll(NOT_EXISTING_ID).size(), 0);
    }

    @Test
    void getBetweenWithNullDatesAnd1000NumberOfSteps_success(){
        STEPS_TO_MATCHER.assertMatch(service.getBetweenInclusive(null, null, 1000, ADMIN_ID), ALL_STEPS_TO);
    }

    @Test
    void updateWithValidData_success(){
        StepsPerDayTo updated = service.update(getUpdated(), ADMIN_ID);
        StepsPerDayTo updatedSteps = getToFromUpdated();
        updatedSteps.setId(updated.getId());
        STEPS_TO_MATCHER.assertMatch(updatedSteps, updated);
    }

    @Test
    void updateNotOwningSteps_exception(){
        assertThrows(NotFoundException.class, () -> service.update(getUpdated(), USER_ID));
    }

    @Test
    void updateToInvalidData_exception(){
        StepsPerDay updated = getUpdated();
        updated.setNumberOfSteps(-1);
        validateRootCause(ConstraintViolationException.class, () -> service.update(updated, ADMIN_ID));
    }
}
