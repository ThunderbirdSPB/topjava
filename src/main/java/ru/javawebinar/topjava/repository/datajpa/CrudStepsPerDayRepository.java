package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.StepsPerDay;

import java.time.LocalDate;
import java.util.List;

@Transactional(readOnly = true)
public interface CrudStepsPerDayRepository extends JpaRepository<StepsPerDay, Integer> {
    @Modifying
    @Transactional
    @Query("DELETE FROM StepsPerDay s WHERE s.id = :stepsId AND s.user.id = :userId")
    int delete(@Param("stepsId") int stepsId, @Param("userId") int userId);

    @Query("SELECT s FROM StepsPerDay s WHERE s.user.id=:userId ORDER BY s.date DESC")
    List<StepsPerDay> getAll(@Param("userId") int userId);

    @Query("""
                    SELECT s FROM StepsPerDay s 
                    WHERE s.user.id=:userId AND s.date >= :startDate AND s.date < :endDate 
                    AND s.numberOfSteps >= :numberOfSteps ORDER BY s.date DESC
                """)
    List<StepsPerDay> getBetweenHalfOpen(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate,
                                  @Param("numberOfSteps") int numberOfSteps, @Param("userId") int userId);
}
