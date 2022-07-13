package ru.javawebinar.topjava.web.steps;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import ru.javawebinar.topjava.model.StepsPerDay;
import ru.javawebinar.topjava.service.StepsPerDayService;
import ru.javawebinar.topjava.to.StepsPerDayTo;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static ru.javawebinar.topjava.util.validation.ValidationUtil.assureIdConsistent;

public abstract class AbstractStepsPerDayController {
    private final Logger log = LoggerFactory.getLogger(getClass());
    @Autowired
    private StepsPerDayService service;

    public StepsPerDayTo create(StepsPerDay steps) {
        int userId = SecurityUtil.authUserId();
        log.info("create steps {} for user {}", steps, userId);
        return service.create(steps, userId);
    }

    public void update(StepsPerDay steps, int id){
        int userId = SecurityUtil.authUserId();
        log.info("update steps {} for user {}", steps, userId);
        assureIdConsistent(steps, id);
        service.update(steps, userId);
    }

    public void delete(int stepsId){
        int userId = SecurityUtil.authUserId();
        log.info("delete stepsId = {} for userId {}", stepsId, userId);
        service.delete(stepsId, userId);
    }

    public StepsPerDayTo get(int stepsId){
        int userId = SecurityUtil.authUserId();
        log.info("get stepsId = {} for userId {}", stepsId, userId);
        return service.get(stepsId, userId);
    }

    public List<StepsPerDayTo> getAll(){
        int userId = SecurityUtil.authUserId();
        log.info("get all for userId {}", userId);
        return service.getAll(userId);
    }

    public List<StepsPerDayTo> getBetween(LocalDate startDate, LocalDate endDate, Integer numberOfSteps){
        int userId = SecurityUtil.authUserId();
        log.info("getBetween dates({} - {}) numberOfSteps {} for user {}", startDate, endDate, numberOfSteps, userId);
        return service.getBetweenInclusive(startDate, endDate,
                Optional.ofNullable(numberOfSteps).orElse(0), userId);
    }
}
