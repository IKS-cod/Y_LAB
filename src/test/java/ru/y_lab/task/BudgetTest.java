package test.java.ru.y_lab.task;

import main.java.ru.y_lab.task.Budget;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class BudgetTest {

    private Budget budget;

    @BeforeEach
    void setUp() {
        budget = new Budget(1000.0);
    }

    @Test
    void getMonthlyLimit() {
        assertThat(budget.getMonthlyLimit()).isEqualTo(1000.0);
    }

    @Test
    void setMonthlyLimit() {
        budget.setMonthlyLimit(1500.0);
        assertThat(budget.getMonthlyLimit()).isEqualTo(1500.0);
    }

    @Test
    void testToString() {
        assertThat(budget.toString()).isEqualTo("Budget{monthlyLimit=1000.0}");
    }
}

