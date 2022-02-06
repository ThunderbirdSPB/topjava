package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.SecurityUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalDate;
import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalTime;

@Controller
@RequestMapping("/meals")
public class JspMealController {
    private static final Logger log = LoggerFactory.getLogger(JspMealController.class);

    private final MealService service;

    public JspMealController(MealService service) {
        this.service = service;
    }

    @GetMapping("")
    public String getAll(Model model) throws ServletException, IOException {
        int userId = SecurityUtil.authUserId();
        log.info("getAll for user {}", userId);

        model.addAttribute("meals", MealsUtil.getTos(service.getAll(userId), SecurityUtil.authUserCaloriesPerDay()));

        return "meals";
    }

    @GetMapping("/delete/{id}")
    public String deleteMeal(@PathVariable Integer id) {
        int userId = SecurityUtil.authUserId();
        log.info("delete meal {} for user {}", id, userId);
        service.delete(id, userId);

        return "redirect:/meals";
    }

    @GetMapping("/create")
    public String doGetCreate(Model model) throws ServletException, IOException {
        log.info("Create new meal");
        final MealTo mealTo =
                new MealTo(null, LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000, false);

        model.addAttribute("meal", mealTo);
        model.addAttribute("action", "create");
        return "mealForm";
    }

    @GetMapping("/update/{id}")
    public String doGetUpdate(@PathVariable Integer id, Model model) throws ServletException, IOException {
        int userId = SecurityUtil.authUserId();
        log.info("update meal with id={} for userId={}", id, userId);
        final MealTo mealTo =
                MealsUtil.createTo(service.get(id, userId), false);

        model.addAttribute("meal", mealTo);
        model.addAttribute("action", "update");

        return "mealForm";
    }

    @GetMapping("filter")
    public String filter(Model model, HttpServletRequest request) throws ServletException, IOException {
        int userId = SecurityUtil.authUserId();
        LocalDate startDate = parseLocalDate(request.getParameter("startDate"));
        LocalDate endDate = parseLocalDate(request.getParameter("endDate"));
        LocalTime startTime = parseLocalTime(request.getParameter("startTime"));
        LocalTime endTime = parseLocalTime(request.getParameter("endTime"));

        log.info("getBetween dates({} - {}) time({} - {}) for user {}", startDate, endDate, startTime, endTime, userId);

        List<Meal> mealsDateFiltered = service.getBetweenInclusive(startDate, endDate, userId);
        List<MealTo> filteredTos = MealsUtil.getFilteredTos(mealsDateFiltered, SecurityUtil.authUserCaloriesPerDay(), startTime, endTime);

        model.addAttribute("meals", filteredTos);
        request.setAttribute("meals", filteredTos);
        return "meals";
    }

    @PostMapping("create")
    public String doPostCreate(HttpServletRequest request) throws IOException {
        int userId = SecurityUtil.authUserId();
        request.setCharacterEncoding("UTF-8");

        Meal meal = new Meal(null,
                LocalDateTime.parse(request.getParameter("dateTime")),
                request.getParameter("description"),
                Integer.parseInt(request.getParameter("calories")));

        log.info("create {} for user {}", meal, userId);

        service.create(meal, userId);

        return "redirect:/meals";
    }

    @PostMapping("update")
    public String doPostUpdate(@RequestParam Integer id, HttpServletRequest request) throws IOException {
        int userId = SecurityUtil.authUserId();
        request.setCharacterEncoding("UTF-8");

        Meal meal = new Meal(id,
                LocalDateTime.parse(request.getParameter("dateTime")),
                request.getParameter("description"),
                Integer.parseInt(request.getParameter("calories")));

        log.info("update {} for user {}", meal, userId);

        service.update(meal, userId);
        return "redirect:/meals";
    }
}
