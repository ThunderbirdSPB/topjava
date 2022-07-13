package ru.javawebinar.topjava.web.steps;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;
import ru.javawebinar.topjava.model.StepsPerDay;
import ru.javawebinar.topjava.to.StepsPerDayTo;
import springfox.documentation.annotations.ApiIgnore;

import java.time.LocalDate;
import java.util.List;

@ApiIgnore
@RestController
@RequestMapping(value = "/profile/steps", produces = MediaType.APPLICATION_JSON_VALUE)
public class StepsPerDayUIController extends AbstractStepsPerDayController{

    @PostMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void createOrUpdate(StepsPerDay steps) {
        if (steps.isNew())
            super.create(steps);
        else
            super.update(steps, steps.getId());
    }

    @Override
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        super.delete(id);
    }

    @Override
    @GetMapping("/{id}")
    public StepsPerDayTo get(@PathVariable int id) {
        return super.get(id);
    }

    @Override
    @GetMapping
    public List<StepsPerDayTo> getAll() {
        return super.getAll();
    }

    @Override
    @GetMapping("/filter")
    public List<StepsPerDayTo> getBetween(@RequestParam @Nullable LocalDate startDate,
                                          @RequestParam @Nullable LocalDate endDate,
                                          @RequestParam @Nullable Integer numberOfSteps) {
        return super.getBetween(startDate, endDate, numberOfSteps);
    }
}
