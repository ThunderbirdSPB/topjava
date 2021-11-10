package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.ValidationUtil;

import java.util.List;

import static ru.javawebinar.topjava.web.SecurityUtil.*;

@Controller
public class MealRestController {
    private final Logger log = LoggerFactory.getLogger(getClass());
    private final MealService service;

    public MealRestController(MealService service) {
        this.service = service;
    }

    public void create(Meal meal){
        log.info("create{}", meal);
        ValidationUtil.checkNew(meal);
        service.save(authUserId(), meal);
    }

    public void update(Meal meal){
        log.info("update{}", meal);
        service.save(authUserId(), meal);
    }

    public void delete(int mealId){
        log.info("delete {}", mealId);
        service.delete(authUserId(), mealId);
    }

    public MealTo get(int mealId){
        log.info("get {}", mealId);
        return getAll().stream().filter(meal -> mealId == meal.getId()).findFirst().orElse(null);
    }

    public List<MealTo> getAll(){
        log.info("getAll");
        return MealsUtil.getTos(service.getAll(authUserId()), authUserCaloriesPerDay());
    }
}