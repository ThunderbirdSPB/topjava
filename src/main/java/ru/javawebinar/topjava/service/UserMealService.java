package ru.javawebinar.topjava.service;

import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.repository.UserRepository;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Service
public class UserMealService {
    private final UserRepository userRepo;
    private final MealRepository mealRepo;

    public UserMealService(UserRepository userRepo, MealRepository mealRepo) {
        this.userRepo = userRepo;
        this.mealRepo = mealRepo;
    }


    public Map<User, Collection<Meal>> getUserMeals(int userId){
       return new HashMap<>(){{
           put(userRepo.get(userId), mealRepo.getAll(userId));
       }};
    }

    public Map<User, Meal> getUserMeal(int userId, int mealId){
        return new HashMap<>(1){{
            put(userRepo.get(userId), mealRepo.get(mealId, userId));
        }};
    }
}
