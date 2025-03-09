package test.java.ru.y_lab.task;

import main.java.ru.y_lab.task.Budget;
import main.java.ru.y_lab.task.Goal;
import main.java.ru.y_lab.task.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

public class UserTest {

    private User user;

    @BeforeEach
    void setUp() {
        user = new User("Test User", "test@example.com", "password");
    }

    @Test
    void getName() {
        assertThat(user.getName()).isEqualTo("Test User");
    }

    @Test
    void setName() {
        user.setName("New Name");
        assertThat(user.getName()).isEqualTo("New Name");
    }

    @Test
    void getEmail() {
        assertThat(user.getEmail()).isEqualTo("test@example.com");
    }

    @Test
    void setEmail() {
        user.setEmail("new@example.com");
        assertThat(user.getEmail()).isEqualTo("new@example.com");
    }

    @Test
    void getPassword() {
        assertThat(user.getPassword()).isEqualTo("password");
    }

    @Test
    void setPassword() {
        user.setPassword("newPassword");
        assertThat(user.getPassword()).isEqualTo("newPassword");
    }

    @Test
    void getTransactions() {
        assertThat(user.getTransactions()).isNotNull();
        assertThat(user.getTransactions()).isEmpty();
    }

    @Test
    void getBudget() {
        assertNull(user.getBudget());
    }

    @Test
    void setBudget() {
        Budget budget = new Budget(1000);
        user.setBudget(budget);
        assertThat(user.getBudget()).isEqualTo(budget);
    }

    @Test
    void getGoal() {
        assertNull(user.getGoal());
    }

    @Test
    void setGoal() {
        Goal goal = new Goal(5000);
        user.setGoal(goal);
        assertThat(user.getGoal()).isEqualTo(goal);
    }

    @Test
    void isBlocked() {
        assertFalse(user.isBlocked());
    }

    @Test
    void setBlocked() {
        user.setBlocked(true);
        assertTrue(user.isBlocked());
    }

    @Test
    void isAdmin() {
        assertFalse(user.isAdmin());
    }

    @Test
    void setAdmin() {
        user.setAdmin(true);
        assertTrue(user.isAdmin());
    }

    @Test
    void testToString() {
        String expected = "User{name='Test User', email='test@example.com', isBlocked=false, isAdmin=false}";
        assertThat(user.toString()).isEqualTo(expected);
    }
}

