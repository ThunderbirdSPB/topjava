package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.StepsPerDay;
import ru.javawebinar.topjava.to.StepsPerDayTo;


import java.time.LocalDate;
import java.util.List;

import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;
import static ru.javawebinar.topjava.util.StepsPerDayUtil.*;

public class StepsPerDayTestData {
    public static final MatcherFactory.Matcher<StepsPerDayTo> STEPS_TO_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(StepsPerDayTo.class, "user");
    public static final Integer STEP_ID = START_SEQ + 11;
    public static final Integer NOT_EXISTING_ID = Integer.MAX_VALUE;
    public static final StepsPerDayTo STEPS = getTo(new StepsPerDay(STEP_ID, LocalDate.of(2020,01,30), 1000));

    public static final List<StepsPerDayTo> ALL_STEPS_TO = getTos(List.of(
            new StepsPerDay(STEP_ID + 8, LocalDate.of(2020,2,8), 1060),
            new StepsPerDay(STEP_ID + 7, LocalDate.of(2020,2,7), 1150),
            new StepsPerDay(STEP_ID + 6, LocalDate.of(2020,2,6), 1140),
            new StepsPerDay(STEP_ID + 5, LocalDate.of(2020,2,5), 1130),
            new StepsPerDay(STEP_ID + 4, LocalDate.of(2020,2,4), 1120),
            new StepsPerDay(STEP_ID + 3, LocalDate.of(2020,2,3), 1110),
            new StepsPerDay(STEP_ID + 2, LocalDate.of(2020,2,2), 1100),
            new StepsPerDay(STEP_ID + 1, LocalDate.of(2020,2,1), 1100),
            new StepsPerDay(STEP_ID, LocalDate.of(2020,1,30), 1000)));

    public static final List<StepsPerDayTo> STEPS_TO_WITH_NUMBER_OF_STEPS_MORE_THAN_OR_EQUAL_1130 = getTos(List.of(
            new StepsPerDay(STEP_ID + 7, LocalDate.of(2020,2,7), 1150),
            new StepsPerDay(STEP_ID + 6, LocalDate.of(2020,2,6), 1140),
            new StepsPerDay(STEP_ID + 5, LocalDate.of(2020,2,5), 1130)));

    public static StepsPerDay getNew(){
        return new StepsPerDay(null, LocalDate.now(), 1000);
    }

    public static StepsPerDay getUpdated(){
        return new StepsPerDay(STEP_ID, LocalDate.now(), 1000);
    }

    public static StepsPerDayTo getToFromNew(){
        return getTo(getNew());
    }

    public static StepsPerDayTo getToFromUpdated(){
        return getTo(getUpdated());
    }
}
