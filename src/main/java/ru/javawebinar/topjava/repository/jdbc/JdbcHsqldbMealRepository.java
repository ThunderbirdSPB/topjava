package ru.javawebinar.topjava.repository.jdbc;

import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.DateTimeUtil;

import java.time.LocalDateTime;
import java.util.List;

@Repository
@Profile("hsqldb")
public class JdbcHsqldbMealRepository extends AbstractJdbcMealRepository {
    public JdbcHsqldbMealRepository(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        super(jdbcTemplate, namedParameterJdbcTemplate);
    }

    @Override
    public List<Meal> getBetweenHalfOpen(LocalDateTime startDateTime, LocalDateTime endDateTime, int userId) {
        return jdbcTemplate.query(
                "SELECT * FROM meals WHERE user_id=?  AND date_time >=  ? AND date_time < ? ORDER BY date_time DESC",
                ROW_MAPPER, userId,  DateTimeUtil.localDateTimeToDate(startDateTime), DateTimeUtil.localDateTimeToDate(endDateTime));
    }

    @Override
    protected MapSqlParameterSource getParametersForMap(int userId, Meal meal) {
        return new MapSqlParameterSource()
                .addValue("user_id", userId)
                .addValue("id", meal.getId())
                .addValue("description", meal.getDescription())
                .addValue("calories", meal.getCalories())
                .addValue("date_time", DateTimeUtil.localDateTimeToDate(meal.getDateTime()));
    }
}
