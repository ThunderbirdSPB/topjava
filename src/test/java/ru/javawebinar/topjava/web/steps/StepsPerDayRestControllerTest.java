package ru.javawebinar.topjava.web.steps;


import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.javawebinar.topjava.StepsPerDayTestData;
import ru.javawebinar.topjava.model.StepsPerDay;
import ru.javawebinar.topjava.service.StepsPerDayService;
import ru.javawebinar.topjava.to.StepsPerDayTo;
import ru.javawebinar.topjava.util.exception.NotFoundException;
import ru.javawebinar.topjava.web.AbstractControllerTest;
import ru.javawebinar.topjava.web.json.JsonUtil;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.javawebinar.topjava.StepsPerDayTestData.*;
import static ru.javawebinar.topjava.TestUtil.userHttpBasic;
import static ru.javawebinar.topjava.UserTestData.*;


class StepsPerDayRestControllerTest extends AbstractControllerTest {
    private static final String REST_URL = StepsPerDayRestController.REST_URL + '/';
    @Autowired
    private StepsPerDayService service;

    @Test
    void get_withValidStepIdAndUser_success() throws Exception {
        perform(get(REST_URL + STEP_ID)
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON)
                .with(userHttpBasic(admin))
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(STEPS_TO_MATCHER.contentJson(STEPS));
    }

    @Test
    void get_withNotExistingStepIdAndUser_unprocessableEntity() throws Exception {
        perform(get(REST_URL + NOT_EXISTING_ID)
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON)
                .with(userHttpBasic(admin))
        )
                .andExpect(status().isUnprocessableEntity())
                .andDo(print());
    }

    @Test
    void getAllWithExistingUser_success() throws Exception {
        perform(MockMvcRequestBuilders
                .get(REST_URL)
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON)
                .with(userHttpBasic(admin))
        )
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(STEPS_TO_MATCHER.contentJson(ALL_STEPS_TO));
    }

    @Test
    void getAllWithNotExistingUser_unauthorized() throws Exception {
        perform(MockMvcRequestBuilders
                .get(REST_URL)
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON)
                .with(userHttpBasic(NOT_EXISTING_USER))
        )
                .andExpect(status().isUnauthorized())
                .andDo(print());
    }

    @Test
    void getBetweenNullDatesAnd1130NumberOfSteps_success() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + "filter")
                .param("numberOfSteps", "1130")
                .with(userHttpBasic(admin)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(STEPS_TO_MATCHER.contentJson(STEPS_TO_WITH_NUMBER_OF_STEPS_MORE_THAN_OR_EQUAL_1130));
    }

    @Test
    void getBetweenNullDatesAnd0NumberOfSteps_returnsAll() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + "filter?startDate=&endTime=")
                .with(userHttpBasic(admin)))
                .andExpect(status().isOk())
                .andExpect(STEPS_TO_MATCHER.contentJson(ALL_STEPS_TO));
    }


    @Test
    void save_withValidData_success() throws Exception {
        ResultActions result = perform(post(REST_URL)
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON)
                .with(userHttpBasic(admin))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(StepsPerDayTestData.getNew()))
        )
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));

        StepsPerDayTo created = STEPS_TO_MATCHER.readFromJson(result);
        StepsPerDayTo newSteps = getToFromNew();
        newSteps.setId(created.getId());
        STEPS_TO_MATCHER.assertMatch(newSteps, created);
    }

    @Test
    void save_withNegativeNumberOfSteps_unprocessableEntity() throws Exception {
        StepsPerDay steps = StepsPerDayTestData.getNew();
        steps.setNumberOfSteps(-1);
        perform(post(REST_URL)
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON)
                .with(userHttpBasic(admin))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(steps))
        )
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void update_withValidData_success() throws Exception {
        perform(MockMvcRequestBuilders.put(REST_URL + STEP_ID).contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(admin))
                .content(JsonUtil.writeValue(StepsPerDayTestData.getUpdated())))
                .andExpect(status().isNoContent());

        STEPS_TO_MATCHER.assertMatch(service.get(STEP_ID, ADMIN_ID), getToFromUpdated());
    }

    @Test
    void update_withNegativeNumberOfSteps_unprocessableEntity() throws Exception {
        StepsPerDay steps = StepsPerDayTestData.getUpdated();
        steps.setNumberOfSteps(-1);
        perform(MockMvcRequestBuilders.put(REST_URL + STEP_ID).contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(admin))
                .content(JsonUtil.writeValue(steps)))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void deleteByExistingIds_success() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL + STEP_ID)
                .with(userHttpBasic(admin)))
                .andExpect(status().isNoContent());
        assertThrows(NotFoundException.class, () -> service.get(STEP_ID, ADMIN_ID));
    }

    @Test
    void deleteByNotExistingStepId_unprocessableEntity() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL + NOT_EXISTING_ID)
                .with(userHttpBasic(admin)))
                .andExpect(status().isUnprocessableEntity());
    }
}