package main.java.ru.y_lab.task;

import java.time.LocalDate;
import java.util.UUID;

/**
 * Представляет финансовую транзакцию.
 * Хранит информацию о транзакции, такую как сумма, категория, описание,
 * является ли она доходом или расходом, и дата транзакции.
 */
public class Transaction {

    private UUID id;
    private double amount;
    private String category;
    private String description;
    private boolean isIncome;
    private LocalDate date;

    /**
     * Создает новый объект Transaction.
     *
     * @param id          Уникальный идентификатор транзакции.
     * @param amount      Сумма транзакции.
     * @param category    Категория транзакции (например, "Еда", "Аренда").
     * @param description Описание транзакции.
     * @param isIncome    `true`, если транзакция является доходом, `false`, если это расход.
     * @param date        Дата транзакции.
     */
    public Transaction(UUID id, double amount, String category, String description, boolean isIncome, LocalDate date) {
        this.id = id;
        this.amount = amount;
        this.category = category;
        this.description = description;
        this.isIncome = isIncome;
        this.date = date;
    }

    /**
     * Возвращает уникальный идентификатор транзакции.
     *
     * @return {@link UUID} транзакции.
     */
    public UUID getId() {
        return id;
    }

    /**
     * Возвращает сумму транзакции.
     *
     * @return Сумма транзакции.
     */
    public double getAmount() {
        return amount;
    }

    /**
     * Устанавливает сумму транзакции.
     *
     * @param amount Новая сумма транзакции.
     */
    public void setAmount(double amount) {
        this.amount = amount;
    }

    /**
     * Возвращает категорию транзакции.
     *
     * @return Категория транзакции.
     */
    public String getCategory() {
        return category;
    }

    /**
     * Устанавливает категорию транзакции.
     *
     * @param category Новая категория транзакции.
     */
    public void setCategory(String category) {
        this.category = category;
    }

    /**
     * Возвращает описание транзакции.
     *
     * @return Описание транзакции.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Устанавливает описание транзакции.
     *
     * @param description Новое описание транзакции.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Проверяет, является ли транзакция доходом.
     *
     * @return `true`, если транзакция является доходом, `false`, если это расход.
     */
    public boolean isIncome() {
        return isIncome;
    }

    /**
     * Устанавливает, является ли транзакция доходом или расходом.
     *
     * @param income `true`, если транзакция является доходом, `false`, если это расход.
     */
    public void setIncome(boolean income) {
        this.isIncome = income;
    }

    /**
     * Возвращает дату транзакции.
     *
     * @return {@link LocalDate} транзакции.
     */
    public LocalDate getDate() {
        return date;
    }

    /**
     * Устанавливает дату транзакции.
     *
     * @param date Новая дата транзакции.
     */
    public void setDate(LocalDate date) {
        this.date = date;
    }

    /**
     * Возвращает строковое представление объекта Transaction. Включает id, сумму, категорию, описание, isIncome и дату.
     *
     * @return Строковое представление объекта.
     */
    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", amount=" + amount +
                ", category='" + category + '\'' +
                ", description='" + description + '\'' +
                ", isIncome=" + isIncome +
                ", date=" + date +
                '}';
    }
}







