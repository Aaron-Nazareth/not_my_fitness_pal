package com.group6.not_my_fitness_pal.food;

import com.group6.not_my_fitness_pal.person.Person;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

// Some general Qs to ask



@Repository("food_postgres")
public class FoodDataAccessService implements FoodDao{

    private JdbcTemplate jdbcTemplate;

    public FoodDataAccessService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int addFood(Food food) {
        // THIS IS THE SQL IMPLEMENTATION
        // Note the sql command doesn't need the id as it is a SERIAL and is generated automatically
        String sql = """
                INSERT INTO food_entries (person_id, name, meal_type, notes, calories, week, day)
                VALUES(?, ?, ?, ?, ?, ?, ?)
                """;

        // Use .update Method when Inserting/deleting/updating
        int rowsAffected = jdbcTemplate.update(
                sql,
                food.getPerson_id(),
                food.getName(),
                food.getMealType().name(),
                food.getNotes(),
                food.getCalories(),
                food.getWeek(),
                food.getDay().name()
        );
        // Should return 1 as only 1 row is added - anything else it should throw error in the service!!
        return rowsAffected;
    }


    @Override
    public int deleteFoodById(Integer id) {
        return 0;
    }

    @Override
    public int updateFoodById(Integer id, Food update) {
        return 0;
    }

    @Override
    public Food getFoodById(Integer id) {
        String sql = """
            SELECT id, person_id, name, meal_type, notes, calories, week, day
            FROM food WHERE id = ?
            """;

        RowMapper<Food> foodRowMapper = (rs, rowNum) -> {
            return new Food(
                    rs.getInt("id"),
                    rs.getInt("person_id"),
                    rs.getString("name"),
                    MealType.valueOf(rs.getString("meal_type")),
                    rs.getString("notes"),
                    rs.getInt("calories"),
                    rs.getInt("week"),
                    Day.valueOf(rs.getString("day"))
            );
        };

        List<Food> foodList = jdbcTemplate.query(sql, foodRowMapper, id);


        if (foodList.isEmpty()){
            return null;
        } else {
            return foodList.get(0);
        }
    }

    @Override
    public List<Food> getAllFood() {
        return null;
    }

    @Override
    public List<Food> getFoodEntriesByPersonId(Integer person_id) {
        return null;
    }

    @Override
    public List<Food> getFoodEntriesByPersonIdByWeek(Integer person_id, Integer week) {
        return null;
    }

    @Override
    public List<Food> getFoodEntriesByPersonIdByWeekByDay(Integer person_id, Integer week, Day day) {
        return null;
    }

    @Override
    public List<Food> getFoodEntriesByMealType(MealType mealType) {
        return null;
    }
}
