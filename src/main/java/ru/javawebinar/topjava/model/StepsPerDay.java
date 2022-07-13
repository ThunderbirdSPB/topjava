package ru.javawebinar.topjava.model;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.DateTimeFormat;
import ru.javawebinar.topjava.View;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Table(name = "steps_per_day")

@NamedQueries({
        @NamedQuery(name = StepsPerDay.DELETE, query = "DELETE FROM StepsPerDay s WHERE s.id=:stepsId AND s.user.id=:userId"),
        @NamedQuery(name = StepsPerDay.ALL_SORTED, query = "SELECT s FROM StepsPerDay s WHERE s.user.id=:userId ORDER BY s.date DESC"),
        @NamedQuery(name = StepsPerDay.GET_BETWEEN, query = """
                    SELECT s FROM StepsPerDay s 
                    WHERE s.user.id=:userId AND s.date >= :startDate AND s.date < :endDate AND s.numberOfSteps >= :numberOfSteps 
                    ORDER BY s.date DESC
                """),
})


public class StepsPerDay extends AbstractBaseEntity{
    public static final String DELETE = "StepsPerDay.delete";
    public static final String ALL_SORTED = "StepsPerDay.getAll";
    public static final String GET_BETWEEN = "StepsPerDay.getBetween";


    @Column(name = "date", nullable = false)
    @NotNull
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate date;

    @Column(name = "number_of_steps", nullable = false)
    @NotNull
    @Range(min = 0, max = 100_000)
    private int numberOfSteps;

    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull(groups = View.Persist.class)
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    protected StepsPerDay() {}

    public StepsPerDay(Integer id, LocalDate date, int numberOfSteps) {
        super(id);
        this.date = date;
        this.numberOfSteps = numberOfSteps;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public int getNumberOfSteps() {
        return numberOfSteps;
    }

    public void setNumberOfSteps(int quantity) {
        this.numberOfSteps = quantity;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "StepsPerDay{" +
                "id=" + id +
                ", date=" + date +
                ", numberOfSteps=" + numberOfSteps +
                ", user=" + user +
                '}';
    }
}
