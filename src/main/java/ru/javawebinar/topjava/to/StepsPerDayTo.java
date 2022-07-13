package ru.javawebinar.topjava.to;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.Range;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Objects;

public class StepsPerDayTo extends BaseTo{
    @NotNull
    private final LocalDate date;

    @NotNull
    @Range(min = 0, max = 100_000)
    private final int numberOfSteps;

    @NotNull
    @Range(min = 0, max = 5_000)
    private final int burnedCalories;

    @JsonCreator
    public StepsPerDayTo(@JsonProperty("id") Integer id, @JsonProperty("date") LocalDate date,
                         @JsonProperty("numberOfSteps")int numberOfSteps, @JsonProperty("burnedCalories")int burnedCalories) {
        super(id);
        this.date = date;
        this.numberOfSteps = numberOfSteps;
        this.burnedCalories = burnedCalories;
    }

    @Override
    public String toString() {
        return "StepsPerDayTo{" +
                "id=" + id +
                ", date=" + date +
                ", numberOfSteps=" + numberOfSteps +
                ", burnedCalories=" + burnedCalories +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StepsPerDayTo that = (StepsPerDayTo) o;
        return numberOfSteps == that.numberOfSteps && burnedCalories == that.burnedCalories && date.equals(that.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(date, numberOfSteps, burnedCalories);
    }
}
