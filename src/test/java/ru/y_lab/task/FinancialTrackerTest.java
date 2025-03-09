package test.java.ru.y_lab.task;

import main.java.ru.y_lab.task.FinancialTracker;
import main.java.ru.y_lab.task.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class FinancialTrackerTest {

    private FinancialTracker tracker;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        tracker = new FinancialTracker();
    }

    @Test
    void registerUser_success() {
        tracker.registerUser("John Doe", "john.doe@example.com", "password");
        assertNotNull(tracker.login("john.doe@example.com", "password"));
    }

    @Test
    void registerUser_invalidEmail() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            tracker.registerUser("Invalid User", "invalid-email", "password");
        });
        assertEquals("Неверный формат email.", exception.getMessage());
    }

    @Test
    void registerUser_existingEmail() {
        tracker.registerUser("John Doe", "john.doe@example.com", "password");
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            tracker.registerUser("Another User", "john.doe@example.com", "anotherPassword");
        });
        assertEquals("Пользователь с таким email уже существует.", exception.getMessage());
    }

    @Test
    void login_success() {
        tracker.registerUser("Test User", "test@example.com", "password");
        User user = tracker.login("test@example.com", "password");
        assertEquals("Test User", user.getName());
    }

    @Test
    void login_incorrectPassword() {
        tracker.registerUser("Test User", "test@example.com", "password");
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            tracker.login("test@example.com", "wrongPassword");
        });
        assertEquals("Неверный пароль.", exception.getMessage());
    }

    @Test
    void addTransaction_success() {
        tracker.registerUser("Test User", "test@example.com", "password");
        User user = tracker.login("test@example.com", "password");
        tracker.addTransaction(user, 100.0, "Food", "Groceries", false, LocalDate.now());
        assertEquals(1, user.getTransactions().size());
    }

    @Test
    void editTransaction_success() {
        tracker.registerUser("Test User", "test@example.com", "password");
        User user = tracker.login("test@example.com", "password");
        tracker.addTransaction(user, 100.0, "Food", "Groceries", false, LocalDate.now());
        UUID transactionId = user.getTransactions().get(0).getId();
        tracker.editTransaction(user, transactionId, 120.0, "Food", "Updated Groceries");
        assertEquals(120.0, user.getTransactions().get(0).getAmount());
    }

    @Test
    void deleteTransaction_success() {
        tracker.registerUser("Test User", "test@example.com", "password");
        User user = tracker.login("test@example.com", "password");
        tracker.addTransaction(user, 100.0, "Food", "Groceries", false, LocalDate.now());
        UUID transactionId = user.getTransactions().get(0).getId();
        tracker.deleteTransaction(user, transactionId);
        assertEquals(0, user.getTransactions().size());
    }

    @Test
    void setBudget_success() {
        tracker.registerUser("Test User", "test@example.com", "password");
        User user = tracker.login("test@example.com", "password");
        tracker.setBudget(user, 500.0);
        assertEquals(500.0, user.getBudget().getMonthlyLimit());
    }

    @Test
    void setGoal_success() {
        tracker.registerUser("Test User", "test@example.com", "password");
        User user = tracker.login("test@example.com", "password");
        tracker.setGoal(user, 1000.0);
        assertEquals(1000.0, user.getGoal().getTargetAmount());
    }

    @Test
    void addSavingsToGoal_success() {
        tracker.registerUser("Test User", "test@example.com", "password");
        User user = tracker.login("test@example.com", "password");
        tracker.setGoal(user, 1000.0);
        tracker.addSavingsToGoal(user, 200.0);
        assertEquals(200.0, user.getGoal().getSavedAmount());
    }

    @Test
    void getGoalProgress_success() {
        tracker.registerUser("Test User", "test@example.com", "password");
        User user = tracker.login("test@example.com", "password");
        tracker.setGoal(user, 1000.0);
        tracker.addSavingsToGoal(user, 500.0);
        assertEquals(50.0, tracker.getGoalProgress(user));
    }

    @Test
    void calculateBalance_success() {
        tracker.registerUser("Test User", "test@example.com", "password");
        User user = tracker.login("test@example.com", "password");
        tracker.addTransaction(user, 100.0, "Income", "Salary", true, LocalDate.now());
        tracker.addTransaction(user, 50.0, "Expenses", "Groceries", false, LocalDate.now());
        assertEquals(50.0, tracker.calculateBalance(user));
    }

    @Test
    void editUserProfile_success() {
        tracker.registerUser("Test User", "test@example.com", "password");
        tracker.editUserProfile("test@example.com", "New Name", "new@example.com", "newPassword");
        User user = tracker.login("new@example.com", "newPassword");
        assertEquals("New Name", user.getName());
    }

    @Test
    void deleteUser_success() {
        tracker.registerUser("Test User", "test@example.com", "password");
        tracker.deleteUser("test@example.com");
        assertThrows(IllegalArgumentException.class, () -> tracker.login("test@example.com", "password"));
    }

    @Test
    void blockUser_success() {
        tracker.registerUser("Test User", "test@example.com", "password");
        tracker.blockUser("test@example.com");
        assertThrows(IllegalArgumentException.class, () -> tracker.login("test@example.com", "password"));
    }

    @Test
    void unblockUser_success() {
        tracker.registerUser("Test User", "test@example.com", "password");
        tracker.blockUser("test@example.com");
        tracker.unblockUser("test@example.com");
        User user = tracker.login("test@example.com", "password");
        assertFalse(user.isBlocked());
    }
}


