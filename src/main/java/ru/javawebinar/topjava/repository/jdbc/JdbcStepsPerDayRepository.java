package ru.javawebinar.topjava.repository.jdbc;

import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.StepsPerDay;
import ru.javawebinar.topjava.repository.StepsPerDayRepository;
import ru.javawebinar.topjava.util.validation.ValidationUtil;

import java.time.LocalDate;
import java.util.List;

@Repository
public class JdbcStepsPerDayRepository implements StepsPerDayRepository {
    protected static final BeanPropertyRowMapper<StepsPerDay> ROW_MAPPER = BeanPropertyRowMapper.newInstance(StepsPerDay.class);
    private final JdbcTemplate jdbcTemplate;
    protected final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final SimpleJdbcInsert insert;

    public JdbcStepsPerDayRepository(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        insert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("steps_per_day")
                .usingGeneratedKeyColumns("id");
    }

    @Override
    public StepsPerDay save(int userId, StepsPerDay steps) {
        ValidationUtil.validate(steps);

        MapSqlParameterSource map = new MapSqlParameterSource()
                .addValue("user_id", userId)
                .addValue("id", steps.getId())
                .addValue("date", steps.getDate())
                .addValue("number_of_steps", steps.getNumberOfSteps());

        if (steps.isNew()){
            Integer id = insert.executeAndReturnKey(map).intValue();
            steps.setId(id);
        }else if (namedParameterJdbcTemplate.update(
                "UPDATE steps_per_day SET number_of_steps=:number_of_steps, " +
                        "date=:date WHERE id=:id AND user_id=:user_id", map) == 0) {
            return null;
        }

        return steps;
    }

    @Override
    public boolean delete(int stepsId, int userId) {
        return jdbcTemplate.update("DELETE FROM steps_per_day WHERE id=? AND user_id=?", stepsId, userId) != 0;
    }

    @Override
    public StepsPerDay get(int stepsId, int userId) {
        List<StepsPerDay> steps = jdbcTemplate.query("SELECT * FROM steps_per_day WHERE id=? AND user_id=?", ROW_MAPPER, stepsId, userId);
        return DataAccessUtils.singleResult(steps);
    }

    @Override
    public List<StepsPerDay> getAll(int userId) {
        return jdbcTemplate.query("SELECT * FROM steps_per_day WHERE user_id=? ORDER BY date DESC", ROW_MAPPER, userId);
    }

    @Override
    public List<StepsPerDay> getBetweenHalfOpen(LocalDate startDate, LocalDate endDate, int numberOfSteps, int userId) {
        return jdbcTemplate.query(
                "SELECT * FROM steps_per_day WHERE user_id=? AND date >= ? AND date < ? " +
                        "AND number_of_steps >= ? ORDER BY date DESC", ROW_MAPPER, userId, startDate, endDate, numberOfSteps);
    }
}
