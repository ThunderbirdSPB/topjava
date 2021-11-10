package ru.javawebinar.topjava;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.web.meal.MealRestController;
import ru.javawebinar.topjava.web.user.AdminRestController;

import java.time.LocalDateTime;
import java.util.Arrays;

public class SpringMain {
    public static void main(String[] args) {
        // java 7 automatic resource management (ARM)
        try (ConfigurableApplicationContext appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml")) {
            System.out.println("Bean definition names: " + Arrays.toString(appCtx.getBeanDefinitionNames()));
            AdminRestController adminUserController = appCtx.getBean(AdminRestController.class);
            adminUserController.create(new User(null, "userName", "email@mail.ru", "password", Role.ADMIN));

            MealRestController mealRestController = appCtx.getBean(MealRestController.class);
            Meal breakfast = new Meal(LocalDateTime.now(), "Breakfast", 1300);
            Meal lunch = new Meal(LocalDateTime.now(), "lunch", 302);
            Meal dinner = new Meal(LocalDateTime.now(), "dinner", 400);

            mealRestController.create(breakfast);
            mealRestController.create(lunch);
            mealRestController.create(dinner);

            System.out.println(mealRestController.getAll());
            mealRestController.update(lunch.setDescription("Supper lunch"));
            System.out.println(mealRestController.getAll());
            mealRestController.delete(1);
            System.out.println(mealRestController.getAll());
        }
    }
}
