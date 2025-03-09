package main.java.ru.y_lab.task;

import java.time.LocalDate;
import java.util.*;

/**
 * Класс `FinancialTracker` предоставляет функциональность для отслеживания финансов пользователей,
 * включая регистрацию пользователей, аутентификацию, добавление/редактирование/удаление транзакций,
 * установку бюджета и финансовых целей, а также фильтрацию транзакций.
 */
public class FinancialTracker {

    private final Map<String, User> users = new HashMap<>();

    /**
     * Конструктор класса `FinancialTracker`.
     * <p>
     * При создании экземпляра класса автоматически регистрирует администратора с email "admin@example.com" и паролем "password".
     * Администратору устанавливается флаг `isAdmin = true`.
     */
    public FinancialTracker() {
        registerUser("Admin", "admin@example.com", "password");
        User admin = users.get("admin@example.com");
        if (admin != null) {
            admin.setAdmin(true);
        }
    }

    /**
     * Регистрирует нового пользователя в системе.
     *
     * @param name     Имя пользователя.
     * @param email    Email пользователя (используется как логин).
     * @param password Пароль пользователя.
     * @throws IllegalArgumentException Если имя, email или пароль не соответствуют требованиям,
     *                                  или если пользователь с таким email уже существует.
     */
    public void registerUser(String name, String email, String password) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Имя не может быть пустым.");
        }
        if (email == null || !email.matches("[^@]+@[^@]+\\.[^@]+")) {
            throw new IllegalArgumentException("Неверный формат email.");
        }
        if (password == null || password.length() < 4) {
            throw new IllegalArgumentException("Пароль должен быть не менее 4 символов.");
        }

        if (users.containsKey(email)) {
            throw new IllegalArgumentException("Пользователь с таким email уже существует.");
        }

        users.put(email, new User(name, email, password));
        System.out.println("Пользователь зарегистрирован.");
    }

    /**
     * Аутентифицирует пользователя в системе.
     *
     * @param email    Email пользователя (логин).
     * @param password Пароль пользователя.
     * @return {@link User} Объект пользователя, если аутентификация прошла успешно, иначе выбрасывает исключение.
     * @throws IllegalArgumentException Если пользователь с таким email не найден,
     *                                  если пароль неверный или если пользователь заблокирован.
     */
    public User login(String email, String password) {
        User user = users.get(email);

        if (user == null) {
            throw new IllegalArgumentException("Пользователь с таким email не найден.");
        }

        if (!user.getPassword().equals(password)) {
            throw new IllegalArgumentException("Неверный пароль.");
        }

        if (user.isBlocked()) {
            throw new IllegalArgumentException("Пользователь заблокирован.");
        }

        return user;
    }

    /**
     * Добавляет новую транзакцию для указанного пользователя.
     *
     * @param user        Пользователь, которому принадлежит транзакция.
     * @param amount      Сумма транзакции.
     * @param category    Категория транзакции.
     * @param description Описание транзакции.
     * @param isIncome    Является ли транзакция доходом (true) или расходом (false).
     * @param date        Дата транзакции.
     * @throws IllegalArgumentException Если сумма <= 0, категория пуста или дата равна null.
     */
    public void addTransaction(User user, double amount, String category, String description, boolean isIncome, LocalDate date) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Сумма должна быть больше нуля.");
        }
        if (category == null || category.trim().isEmpty()) {
            throw new IllegalArgumentException("Категория не может быть пустой.");
        }
        if (date == null) {
            throw new IllegalArgumentException("Дата не может быть пустой.");
        }

        Transaction transaction = new Transaction(UUID.randomUUID(), amount, category, description, isIncome, date); // Создаем ID
        user.getTransactions().add(transaction);
        checkBudgetAndNotify(user); // Уведомление о бюджете
    }

    /**
     * Редактирует существующую транзакцию для указанного пользователя.
     *
     * @param user          Пользователь, которому принадлежит транзакция.
     * @param transactionId Уникальный ID транзакции, которую нужно отредактировать.
     * @param amount        Новая сумма транзакции.
     * @param category      Новая категория транзакции.
     * @param description   Новое описание транзакции.
     * @throws IllegalArgumentException Если ID транзакции пуст, сумма <= 0, категория пуста
     *                                  или транзакция с указанным ID не найдена.
     */
    public void editTransaction(User user, UUID transactionId, double amount, String category, String description) {
        if (transactionId == null) {
            throw new IllegalArgumentException("ID транзакции не может быть пустым.");
        }
        if (amount <= 0) {
            throw new IllegalArgumentException("Сумма должна быть больше нуля.");
        }
        if (category == null || category.trim().isEmpty()) {
            throw new IllegalArgumentException("Категория не может быть пустой.");
        }

        for (Transaction transaction : user.getTransactions()) {
            if (transaction.getId().equals(transactionId)) {
                transaction.setAmount(amount);
                transaction.setCategory(category);
                transaction.setDescription(description);
                checkBudgetAndNotify(user);  // Уведомление о бюджете
                return;
            }
        }
        throw new IllegalArgumentException("Транзакция с указанным ID не найдена.");
    }

    /**
     * Удаляет транзакцию для указанного пользователя по ID.
     *
     * @param user          Пользователь, которому принадлежит транзакция.
     * @param transactionId Уникальный ID транзакции, которую нужно удалить.
     * @throws IllegalArgumentException Если ID транзакции пуст или транзакция с указанным ID не найдена.
     */
    public void deleteTransaction(User user, UUID transactionId) {
        if (transactionId == null) {
            throw new IllegalArgumentException("ID транзакции не может быть пустым.");
        }
        for (int i = 0; i < user.getTransactions().size(); i++) {
            if (user.getTransactions().get(i).getId().equals(transactionId)) {
                user.getTransactions().remove(i);
                checkBudgetAndNotify(user);  // Уведомление о бюджете
                return;
            }
        }
        throw new IllegalArgumentException("Транзакция с указанным ID не найдена.");
    }

    /**
     * Устанавливает месячный лимит бюджета для указанного пользователя.
     *
     * @param user         Пользователь, для которого устанавливается бюджет.
     * @param monthlyLimit Месячный лимит бюджета.
     * @throws IllegalArgumentException Если пользователь не найден или лимит бюджета отрицательный.
     */
    public void setBudget(User user, double monthlyLimit) {
        if (user == null) {
            throw new IllegalArgumentException("Пользователь не найден.");
        }

        if (monthlyLimit < 0) {
            throw new IllegalArgumentException("Лимит бюджета не может быть отрицательным.");
        }

        Budget budget = new Budget(monthlyLimit);
        user.setBudget(budget);
        checkBudgetAndNotify(user); // Уведомление о бюджете
    }

    /**
     * Устанавливает финансовую цель для указанного пользователя.
     *
     * @param user         Пользователь, для которого устанавливается цель.
     * @param targetAmount Целевая сумма.
     * @throws IllegalArgumentException Если пользователь не найден или целевая сумма не положительная.
     */
    public void setGoal(User user, double targetAmount) {
        if (user == null) {
            throw new IllegalArgumentException("Пользователь не найден.");
        }

        if (targetAmount <= 0) {
            throw new IllegalArgumentException("Целевая сумма должна быть больше нуля.");
        }
        Goal goal = new Goal(targetAmount);
        user.setGoal(goal);
    }

    /**
     * Добавляет накопления к цели пользователя.
     *
     * @param user   Пользователь, для которого добавляются накопления.
     * @param amount Сумма накоплений для добавления.
     * @throws IllegalArgumentException Если пользователь не найден, у пользователя не установлена цель или сумма накоплений не положительная.
     */
    public void addSavingsToGoal(User user, double amount) {
        if (user == null) {
            throw new IllegalArgumentException("Пользователь не найден.");
        }
        if (user.getGoal() == null) {
            throw new IllegalArgumentException("У пользователя не установлена цель.");
        }
        if (amount <= 0) {
            throw new IllegalArgumentException("Сумма накоплений должна быть больше нуля.");
        }
        user.getGoal().setSavedAmount(user.getGoal().getSavedAmount() + amount);

    }

    /**
     * Возвращает прогресс достижения цели в процентах.
     *
     * @param user Пользователь, для которого вычисляется прогресс.
     * @return double Прогресс достижения цели в процентах (от 0 до 100).
     * @throws IllegalArgumentException Если пользователь не найден или у пользователя не установлена цель.
     */
    public double getGoalProgress(User user) {
        if (user == null) {
            throw new IllegalArgumentException("Пользователь не найден.");
        }
        if (user.getGoal() == null) {
            throw new IllegalArgumentException("У пользователя не установлена цель.");
        }
        double targetAmount = user.getGoal().getTargetAmount();
        double savedAmount = user.getGoal().getSavedAmount();
        if (targetAmount <= 0) {
            return 0;
        }
        return (savedAmount / targetAmount) * 100;
    }

    /**
     * Вычисляет текущий баланс для указанного пользователя на основе его транзакций.
     *
     * @param user Пользователь, для которого вычисляется баланс.
     * @return double Текущий баланс пользователя.
     * @throws IllegalArgumentException Если пользователь не найден.
     */
    public double calculateBalance(User user) {
        if (user == null) {
            throw new IllegalArgumentException("Пользователь не найден.");
        }
        double balance = 0;
        for (Transaction transaction : user.getTransactions()) {
            balance += transaction.isIncome() ? transaction.getAmount() : -transaction.getAmount();
        }
        return balance;
    }

    /**
     * Редактирует профиль пользователя.
     *
     * @param email       Email пользователя, профиль которого нужно отредактировать.
     * @param newName     Новое имя пользователя.
     * @param newEmail    Новый email пользователя.
     * @param newPassword Новый пароль пользователя.
     * @throws IllegalArgumentException Если пользователь не найден, новое имя или email не валидны,
     *                                  пароль слишком короткий или новый email уже занят другим пользователем.
     */
    public void editUserProfile(String email, String newName, String newEmail, String newPassword) {
        if (!users.containsKey(email)) {
            throw new IllegalArgumentException("Пользователь не найден.");
        }
        if (newName == null || newName.trim().isEmpty()) {
            throw new IllegalArgumentException("Имя не может быть пустым.");
        }
        if (newEmail == null || !newEmail.matches("[^@]+@[^@]+\\.[^@]+")) {
            throw new IllegalArgumentException("Неверный формат email.");
        }
        if (newPassword == null || newPassword.length() < 4) {
            throw new IllegalArgumentException("Пароль должен быть не менее 4 символов.");
        }
        if (!email.equals(newEmail) && users.containsKey(newEmail)) {
            throw new IllegalArgumentException("Пользователь с таким email уже существует.");
        }

        User user = users.get(email);
        user.setName(newName);
        user.setEmail(newEmail);
        user.setPassword(newPassword);

        if (!email.equals(newEmail)) {
            users.remove(email);
            users.put(newEmail, user);
        }
    }

    /**
     * Удаляет аккаунт пользователя из системы.
     *
     * @param email Email пользователя, аккаунт которого нужно удалить.
     * @throws IllegalArgumentException Если пользователь с указанным email не найден.
     */
    public void deleteUser(String email) {
        if (!users.containsKey(email)) {
            throw new IllegalArgumentException("Пользователь не найден.");
        }
        users.remove(email);
    }

    /**
     * Выводит в консоль список всех зарегистрированных пользователей.
     */
    public void viewUsers() {
        if (users.isEmpty()) {
            System.out.println("Список пользователей пуст.");
            return;
        }
        for (User user : users.values()) {
            System.out.println(user);
        }
    }

    /**
     * Выводит в консоль список транзакций указанного пользователя.
     *
     * @param email Email пользователя, транзакции которого нужно просмотреть.
     * @throws IllegalArgumentException Если пользователь с указанным email не найден.
     */
    public void viewUserTransactions(String email) {
        User user = users.get(email);
        if (user == null) {
            throw new IllegalArgumentException("Пользователь не найден.");
        }
        if (user.getTransactions().isEmpty()) {
            System.out.println("Список транзакций пуст.");
            return;
        }
        for (Transaction transaction : user.getTransactions()) {
            System.out.println(transaction);
        }
    }

    /**
     * Выводит в консоль список транзакций пользователя с их ID.
     *
     * @param user Пользователь, транзакции которого нужно просмотреть.
     * @throws IllegalArgumentException Если пользователь равен null.
     */
    public void viewUserTransactionsWithId(User user) {
        if (user == null) {
            throw new IllegalArgumentException("Пользователь не может быть null.");
        }

        List<Transaction> transactions = user.getTransactions();
        if (transactions.isEmpty()) {
            System.out.println("Список транзакций пуст.");
            return;
        }

        for (Transaction transaction : transactions) {
            System.out.println("ID: " + transaction.getId() + ", " + transaction);
        }
    }

    /**
     * Блокирует указанного пользователя в системе.
     *
     * @param email Email пользователя, которого нужно заблокировать.
     * @throws IllegalArgumentException Если пользователь с указанным email не найден.
     */
    public void blockUser(String email) {
        User user = users.get(email);
        if (user == null) {
            throw new IllegalArgumentException("Пользователь не найден.");
        }
        user.setBlocked(true);
    }

    /**
     * Разблокирует указанного пользователя в системе.
     *
     * @param email Email пользователя, которого нужно разблокировать.
     * @throws IllegalArgumentException Если пользователь с указанным email не найден.
     */
    public void unblockUser(String email) {
        User user = users.get(email);
        if (user == null) {
            throw new IllegalArgumentException("Пользователь не найден.");
        }
        user.setBlocked(false);
    }

    /**
     * Фильтрует транзакции указанного пользователя по дате.
     *
     * @param user      Пользователь, транзакции которого нужно отфильтровать.
     * @param startDate Начальная дата периода фильтрации (включительно).
     * @param endDate   Конечная дата периода фильтрации (включительно).
     * @return {@link List < Transaction >} Список транзакций, удовлетворяющих условиям фильтрации.
     */
    public List<Transaction> filterTransactionsByDate(User user, LocalDate startDate, LocalDate endDate) {
        List<Transaction> filteredTransactions = new ArrayList<>();
        for (Transaction transaction : user.getTransactions()) {
            if (transaction.getDate().isAfter(startDate.minusDays(1)) && transaction.getDate().isBefore(endDate.plusDays(1))) {
                filteredTransactions.add(transaction);
            }
        }
        return filteredTransactions;
    }

    /**
     * Фильтрует транзакции указанного пользователя по категории.
     *
     * @param user     Пользователь, транзакции которого нужно отфильтровать.
     * @param category Категория для фильтрации транзакций.
     * @return {@link List < Transaction >} Список транзакций, удовлетворяющих условиям фильтрации.
     */
    public List<Transaction> filterTransactionsByCategory(User user, String category) {
        List<Transaction> filteredTransactions = new ArrayList<>();
        for (Transaction transaction : user.getTransactions()) {
            if (transaction.getCategory().equalsIgnoreCase(category)) {
                filteredTransactions.add(transaction);
            }
        }
        return filteredTransactions;
    }

    /**
     * Фильтрует транзакции указанного пользователя по типу (доход/расход).
     *
     * @param user     Пользователь, транзакции которого нужно отфильтровать.
     * @param isIncome Тип транзакции для фильтрации (true - доход, false - расход).
     * @return {@link List < Transaction >} Список транзакций, удовлетворяющих условиям фильтрации.
     */
    public List<Transaction> filterTransactionsByType(User user, boolean isIncome) {
        List<Transaction> filteredTransactions = new ArrayList<>();
        for (Transaction transaction : user.getTransactions()) {
            if (transaction.isIncome() == isIncome) {
                filteredTransactions.add(transaction);
            }
        }
        return filteredTransactions;
    }

    /**
     * Внутренний метод для проверки бюджета пользователя и отправки уведомления (реализация не предоставлена).
     *
     * @param user Пользователь, для которого производится проверка бюджета.
     */
    private void checkBudgetAndNotify(User user) {
        // Временная реализация
        System.out.println("Проверка бюджета и отправка уведомления для пользователя: " + user.getName());
    }
}










