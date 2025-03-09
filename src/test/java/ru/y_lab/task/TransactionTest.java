package test.java.ru.y_lab.task;

import main.java.ru.y_lab.task.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class TransactionTest {

    private Transaction transaction;
    private UUID transactionId;
    private LocalDate transactionDate;

    @BeforeEach
    void setUp() {
        transactionId = UUID.randomUUID();
        transactionDate = LocalDate.now();
        transaction = new Transaction(transactionId, 100.0, "Food", "Groceries", false, transactionDate);
    }

    @Test
    void getId() {
        assertThat(transaction.getId()).isEqualTo(transactionId);
    }

    @Test
    void getAmount() {
        assertThat(transaction.getAmount()).isEqualTo(100.0);
    }

    @Test
    void setAmount() {
        transaction.setAmount(150.0);
        assertThat(transaction.getAmount()).isEqualTo(150.0);
    }

    @Test
    void getCategory() {
        assertThat(transaction.getCategory()).isEqualTo("Food");
    }

    @Test
    void setCategory() {
        transaction.setCategory("Entertainment");
        assertThat(transaction.getCategory()).isEqualTo("Entertainment");
    }

    @Test
    void getDescription() {
        assertThat(transaction.getDescription()).isEqualTo("Groceries");
    }

    @Test
    void setDescription() {
        transaction.setDescription("Movie night");
        assertThat(transaction.getDescription()).isEqualTo("Movie night");
    }

    @Test
    void isIncome() {
        assertThat(transaction.isIncome()).isFalse();
    }

    @Test
    void setIncome() {
        transaction.setIncome(true);
        assertThat(transaction.isIncome()).isTrue();
    }

    @Test
    void getDate() {
        assertThat(transaction.getDate()).isEqualTo(transactionDate);
    }

    @Test
    void setDate() {
        LocalDate newDate = LocalDate.of(2024, 1, 1);
        transaction.setDate(newDate);
        assertThat(transaction.getDate()).isEqualTo(newDate);
    }

    @Test
    void testToString() {
        String expected = "Transaction{id=" + transactionId + ", amount=100.0, category='Food', description='Groceries', isIncome=false, date=" + transactionDate + "}";
        assertThat(transaction.toString()).isEqualTo(expected);
    }
}

