package test.java.ru.y_lab.task;

import main.java.ru.y_lab.task.Goal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class GoalTest {

    private Goal goal;

    @BeforeEach
    void setUp() {
        goal = new Goal(5000.0);
    }

    @Test
    void getTargetAmount() {
        assertThat(goal.getTargetAmount()).isEqualTo(5000.0);
    }

    @Test
    void setTargetAmount() {
        goal.setTargetAmount(6000.0);
        assertThat(goal.getTargetAmount()).isEqualTo(6000.0);
    }

    @Test
    void getSavedAmount() {
        assertThat(goal.getSavedAmount()).isEqualTo(0.0);
    }

    @Test
    void setSavedAmount() {
        goal.setSavedAmount(2000.0);
        assertThat(goal.getSavedAmount()).isEqualTo(2000.0);
    }

    @Test
    void testToString() {
        assertThat(goal.toString()).isEqualTo("Goal{targetAmount=5000.0, savedAmount=0.0}");
    }
}

