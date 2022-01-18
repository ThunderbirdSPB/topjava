package ru.javawebinar.topjava.repository.jdbc;

import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;

import java.util.List;

public abstract class AbstractJdbcMealRepository implements MealRepository {
    protected static final BeanPropertyRowMapper<Meal> ROW_MAPPER = BeanPropertyRowMapper.newInstance(Meal.class);
    protected final JdbcTemplate jdbcTemplate;
    protected final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    protected final SimpleJdbcInsert insertMeal;

    public AbstractJdbcMealRepository(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.insertMeal = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("meals")
                .usingGeneratedKeyColumns("id");

        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    // Pattern template method
    public Meal save(int userId, Meal meal) {
        MapSqlParameterSource map = getParametersForMap(userId, meal);
        if (meal.isNew()) {
            Number newKey = insertMeal.executeAndReturnKey(map);
            meal.setId(newKey.intValue());
        } else if (namedParameterJdbcTemplate.update(
                "UPDATE meals SET description=:description, " +
                        "calories=:calories, date_time=:date_time WHERE id=:id AND user_id=:user_id", map) == 0) {
            return null;
        }
        return meal;
    }

    @Override
    public boolean delete(int mealId, int userId) {
        return jdbcTemplate.update("DELETE FROM meals WHERE id=? AND user_id=?", mealId, userId) != 0;
    }

    @Override
    public Meal get(int mealId, int userId ) {
        List<Meal> users = jdbcTemplate.query("SELECT * FROM meals WHERE id=? AND user_id=?", ROW_MAPPER, mealId, userId);
        return DataAccessUtils.singleResult(users);
    }

    @Override
    public List<Meal> getAll(int userId) {
        return jdbcTemplate.query("SELECT * FROM meals WHERE user_id=?", ROW_MAPPER,  userId);
    }

    // implementation specific method
    protected abstract MapSqlParameterSource getParametersForMap(int userId, Meal meal);
}
