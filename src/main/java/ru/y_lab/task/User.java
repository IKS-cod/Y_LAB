package main.java.ru.y_lab.task;

import java.util.ArrayList;
import java.util.List;

/**
 * Представляет пользователя в системе управления финансами.
 * Хранит информацию о пользователе, такую как имя, адрес электронной почты, пароль, транзакции, бюджет и цели.
 * Также отслеживает статус пользователя (заблокирован/активен) и роль (админ/обычный пользователь).
 */
public class User {

    private String name;
    private String email;
    private String password;
    private List<Transaction> transactions;
    private Budget budget;
    private Goal goal;
    private boolean isBlocked;
    private boolean isAdmin;

    /**
     * Создает новый объект User.
     *
     * @param name     Имя пользователя.
     * @param email    Адрес электронной почты пользователя.
     * @param password Пароль пользователя.
     */
    public User(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.transactions = new ArrayList<>();
        this.budget = null;
        this.goal = null;
        this.isBlocked = false;
        this.isAdmin = false;
    }

    /**
     * Возвращает имя пользователя.
     *
     * @return Имя пользователя.
     */
    public String getName() {
        return name;
    }

    /**
     * Устанавливает имя пользователя.
     *
     * @param name Новое имя пользователя.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Возвращает адрес электронной почты пользователя.
     *
     * @return Адрес электронной почты пользователя.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Устанавливает адрес электронной почты пользователя.
     *
     * @param email Новый адрес электронной почты пользователя.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Возвращает пароль пользователя.
     *  Учитывайте последствия для безопасности, прежде чем напрямую раскрывать пароль.
     *
     * @return Пароль пользователя.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Устанавливает пароль пользователя.
     *
     * @param password Новый пароль пользователя.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Возвращает список транзакций, связанных с пользователем.
     *
     * @return Список объектов {@link Transaction}.
     */
    public List<Transaction> getTransactions() {
        return transactions;
    }

    /**
     * Возвращает бюджет пользователя.
     *
     * @return Объект {@link Budget}, связанный с пользователем.
     */
    public Budget getBudget() {
        return budget;
    }

    /**
     * Устанавливает бюджет пользователя.
     *
     * @param budget Новый объект {@link Budget} для пользователя.
     */
    public void setBudget(Budget budget) {
        this.budget = budget;
    }

    /**
     * Возвращает финансовую цель пользователя.
     *
     * @return Объект {@link Goal}, связанный с пользователем.
     */
    public Goal getGoal() {
        return goal;
    }

    /**
     * Устанавливает финансовую цель пользователя.
     *
     * @param goal Новый объект {@link Goal} для пользователя.
     */
    public void setGoal(Goal goal) {
        this.goal = goal;
    }

    /**
     * Проверяет, заблокирован ли пользователь.
     *
     * @return `true`, если пользователь заблокирован, `false` в противном случае.
     */
    public boolean isBlocked() {
        return isBlocked;
    }

    /**
     * Устанавливает статус блокировки пользователя.
     *
     * @param blocked `true`, чтобы заблокировать пользователя, `false`, чтобы разблокировать.
     */
    public void setBlocked(boolean blocked) {
        isBlocked = blocked;
    }

    /**
     * Проверяет, является ли пользователь администратором.
     *
     * @return `true`, если пользователь является администратором, `false` в противном случае.
     */
    public boolean isAdmin() {
        return isAdmin;
    }

    /**
     * Устанавливает статус администратора пользователя.
     *
     * @param admin `true`, чтобы сделать пользователя администратором, `false` в противном случае.
     */
    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    /**
     * Возвращает строковое представление объекта User. Включает имя, адрес электронной почты, статус блокировки и статус администратора.
     *
     * @return Строковое представление объекта.
     */
    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", isBlocked=" + isBlocked +
                ", isAdmin=" + isAdmin +
                '}';
    }
}