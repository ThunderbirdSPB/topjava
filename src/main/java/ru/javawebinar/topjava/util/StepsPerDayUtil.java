package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.StepsPerDay;
import ru.javawebinar.topjava.to.StepsPerDayTo;

import java.util.List;
import java.util.stream.Collectors;

public class StepsPerDayUtil {
    private static final double coefficient = 0.05;
    public static StepsPerDayTo getTo(StepsPerDay steps){
        return new StepsPerDayTo(steps.getId(), steps.getDate(), steps.getNumberOfSteps(), countCalories(steps.getNumberOfSteps()));
    }

    public static List<StepsPerDayTo> getTos(List<StepsPerDay> stepsPerDays){
        return stepsPerDays.stream()
                .map(StepsPerDayUtil::getTo)
                .collect(Collectors.toList());
    }

    private static int countCalories(int numberOfSteps){
        return (int)Math.ceil(numberOfSteps * coefficient);
    }
}
