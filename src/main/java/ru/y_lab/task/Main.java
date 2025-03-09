package main.java.ru.y_lab.task;


import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

/**
 * Основной класс приложения Financial Tracker.
 * <p>
 * Этот класс содержит главную функцию main и управляет потоком приложения,
 * включая отображение меню авторизации, главного меню и обработку пользовательского ввода.
 */
public class Main {

    /**
     * Экземпляр FinancialTracker для управления финансами пользователей.
     */
    private static final FinancialTracker tracker = new FinancialTracker();
    /**
     * Экземпляр Scanner для чтения ввода пользователя из консоли.
     */
    private static final Scanner scanner = new Scanner(System.in);
    /**
     * Текущий вошедший в систему пользователь.
     */
    private static User currentUser = null;

    /**
     * Главный метод приложения.
     * <p>
     * Этот метод запускает основной цикл приложения, который отображает меню авторизации,
     * если пользователь не вошел в систему, и главное меню, если пользователь аутентифицирован.
     *
     * @param args Аргументы командной строки (не используются).
     */
    public static void main(String[] args) {
        while (true) {
            if (currentUser == null) {
                showLoginMenu();
            } else {
                showMainMenu();
            }
        }
    }

    /**
     * Отображает меню авторизации.
     * <p>
     * Предлагает пользователю зарегистрироваться, войти в систему или выйти из приложения.
     * Обрабатывает выбор пользователя и вызывает соответствующие методы.
     */
    private static void showLoginMenu() {
        System.out.println("\n--- Меню Авторизации ---");
        System.out.println("1. Регистрация");
        System.out.println("2. Вход");
        System.out.println("0. Выход");
        System.out.print("Выберите действие: ");

        String choice = scanner.nextLine();

        switch (choice) {
            case "1":
                registerUser();
                break;
            case "2":
                loginUser();
                break;
            case "0":
                System.out.println("Выход из приложения.");
                System.exit(0);
            default:
                System.out.println("Неверный выбор. Попробуйте снова.");
        }
    }

    /**
     * Отображает главное меню.
     * <p>
     * Предлагает пользователю различные опции, такие как управление финансами, бюджетом, целями,
     * просмотр статистики и аналитики, управление профилем и администрирование (если пользователь является администратором).
     * Обрабатывает выбор пользователя и вызывает соответствующие методы.
     */
    private static void showMainMenu() {
        System.out.println("\n--- Главное Меню ---");
        System.out.println("1. Управление финансами");
        System.out.println("2. Управление бюджетом");
        System.out.println("3. Управление целями");
        System.out.println("4. Статистика и аналитика");
        System.out.println("5. Управление профилем");
        if (currentUser != null && currentUser.isAdmin()) {
            System.out.println("6. Администрирование");
        }
        System.out.println("0. Выход из системы");
        System.out.print("Выберите действие: ");

        String choice = scanner.nextLine();

        switch (choice) {
            case "1":
                manageTransactions();
                break;
            case "2":
                manageBudget();
                break;
            case "3":
                manageGoals();
                break;
            case "4":
                showStatistics();
                break;
            case "5":
                manageProfile();
                break;
            case "6":
                if (currentUser != null && currentUser.isAdmin()) {
                    adminMenu();
                } else {
                    System.out.println("Нет доступа.");
                }
                break;
            case "0":
                logoutUser();
                break;
            default:
                System.out.println("Неверный выбор. Попробуйте снова.");
        }
    }

    /**
     * Регистрирует нового пользователя.
     * <p>
     * Запрашивает у пользователя имя, email и пароль, затем вызывает метод registerUser
     * класса FinancialTracker для регистрации пользователя в системе.
     */
    private static void registerUser() {
        System.out.println("\n--- Регистрация Пользователя ---");
        System.out.print("Имя: ");
        String name = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Пароль: ");
        String password = scanner.nextLine();

        try {
            tracker.registerUser(name, email, password);
            System.out.println("Регистрация прошла успешно.");
        } catch (IllegalArgumentException e) {
            System.out.println("Ошибка регистрации: " + e.getMessage());
        }
    }

    /**
     * Аутентифицирует пользователя.
     * <p>
     * Запрашивает у пользователя email и пароль, затем вызывает метод login класса
     * FinancialTracker для аутентификации пользователя в системе.
     */
    private static void loginUser() {
        System.out.println("\n--- Вход в Систему ---");
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Пароль: ");
        String password = scanner.nextLine();

        try {
            currentUser = tracker.login(email, password);
            System.out.println("Вход выполнен. Добро пожаловать, " + currentUser.getName() + "!");
        } catch (IllegalArgumentException e) {
            System.out.println("Ошибка входа: " + e.getMessage());
        }
    }

    /**
     * Выход пользователя из системы.
     * <p>
     * Устанавливает currentUser в null.
     */
    private static void logoutUser() {
        System.out.println("Выход из системы. До свидания!");
        currentUser = null;
    }

    // --- Управление Целями ---

    /**
     * Управляет целями пользователя.
     * <p>
     * Предлагает пользователю установить цель, добавить накопления и посмотреть прогресс.
     */
    private static void manageGoals() {
        while (true) {
            System.out.println("\n--- Управление Целями ---");
            System.out.println("1. Установить цель");
            System.out.println("2. Добавить накопления");
            System.out.println("3. Посмотреть прогресс цели"); // Добавляем пункт меню
            System.out.println("0. Назад");
            System.out.print("Выберите действие: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    setGoal();
                    break;
                case "2":
                    addSavingsToGoal();
                    break;
                case "3":
                    viewGoalProgress(); // Добавляем обработку выбора пункта меню
                    break;
                case "0":
                    return;
                default:
                    System.out.println("Неверный выбор. Попробуйте снова.");
            }
        }
    }

    /**
     * Устанавливает финансовую цель для пользователя.
     * <p>
     * Запрашивает целевую сумму и вызывает метод setGoal класса FinancialTracker.
     */
    private static void setGoal() {
        System.out.println("\n--- Установка Цели ---");
        System.out.print("Введите целевую сумму: ");
        try {
            double targetAmount = Double.parseDouble(scanner.nextLine());
            tracker.setGoal(currentUser, targetAmount);
            System.out.println("Цель успешно установлена.");
        } catch (NumberFormatException e) {
            System.out.println("Неверный формат суммы.");
        } catch (IllegalArgumentException e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }

    /**
     * Добавляет накопления к финансовой цели пользователя.
     * <p>
     * Запрашивает сумму для добавления и вызывает метод addSavingsToGoal класса FinancialTracker.
     */
    private static void addSavingsToGoal() {
        System.out.println("\n--- Добавление Накоплений ---");
        System.out.print("Введите сумму для добавления: ");
        try {
            double amount = Double.parseDouble(scanner.nextLine());
            tracker.addSavingsToGoal(currentUser, amount);
            System.out.println("Накопления успешно добавлены.");
        } catch (NumberFormatException e) {
            System.out.println("Неверный формат суммы.");
        } catch (IllegalArgumentException e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }

    /**
     * Отображает прогресс достижения финансовой цели пользователя.
     * <p>
     * Вызывает метод getGoalProgress класса FinancialTracker и отображает результат.
     */
    private static void viewGoalProgress() {
        System.out.println("\n--- Прогресс Достижения Цели ---");
        try {
            double progress = tracker.getGoalProgress(currentUser);
            System.out.println("Ваш прогресс: " + String.format("%.2f", progress) + "%"); // Форматируем вывод
        } catch (IllegalArgumentException e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }

    // --- Управление Финансами ---

    /**
     * Управляет финансовыми транзакциями пользователя.
     * <p>
     * Предлагает пользователю добавить, редактировать, удалить и просмотреть транзакции.
     */
    private static void manageTransactions() {
        while (true) {
            System.out.println("\n--- Управление Финансами ---");
            System.out.println("1. Добавить транзакцию");
            System.out.println("2. Редактировать транзакцию");
            System.out.println("3. Удалить транзакцию");
            System.out.println("4. Просмотреть транзакции");
            System.out.println("0. Назад");
            System.out.print("Выберите действие: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    addTransaction();
                    break;
                case "2":
                    editTransaction();
                    break;
                case "3":
                    deleteTransaction();
                    break;
                case "4":
                    viewTransactions();
                    break;
                case "0":
                    return;
                default:
                    System.out.println("Неверный выбор. Попробуйте снова.");
            }
        }
    }

    /**
     * Добавляет новую транзакцию для пользователя.
     * <p>
     * Запрашивает сумму, категорию, описание, тип (доход/расход) и дату транзакции,
     * затем вызывает метод addTransaction класса FinancialTracker.
     */
    private static void addTransaction() {
        System.out.println("\n--- Добавить Транзакцию ---");
        System.out.print("Сумма: ");
        double amount = Double.parseDouble(scanner.nextLine());
        System.out.print("Категория: ");
        String category = scanner.nextLine();
        System.out.print("Описание: ");
        String description = scanner.nextLine();
        System.out.print("Доход (true) / Расход (false): ");
        boolean isIncome = Boolean.parseBoolean(scanner.nextLine());
        System.out.print("Дата (YYYY-MM-DD): ");
        LocalDate date = LocalDate.parse(scanner.nextLine());

        try {
            tracker.addTransaction(currentUser, amount, category, description, isIncome, date);
            System.out.println("Транзакция добавлена.");
        } catch (IllegalArgumentException e) {
            System.out.println("Ошибка добавления транзакции: " + e.getMessage());
        }
    }

    /**
     * Редактирует существующую транзакцию пользователя.
     * <p>
     * Запрашивает ID транзакции, новую сумму, новую категорию и новое описание,
     * затем вызывает метод editTransaction класса FinancialTracker.
     */
    private static void editTransaction() {
        System.out.println("\n--- Редактировать Транзакцию ---");
        System.out.print("Введите ID транзакции для редактирования: ");
        String transactionIdStr = scanner.nextLine();
        UUID transactionId = UUID.fromString(transactionIdStr);

        System.out.print("Новая сумма: ");
        double newAmount = Double.parseDouble(scanner.nextLine());
        System.out.print("Новая категория: ");
        String newCategory = scanner.nextLine();
        System.out.print("Новое описание: ");
        String newDescription = scanner.nextLine();

        try {
            tracker.editTransaction(currentUser, transactionId, newAmount, newCategory, newDescription);
            System.out.println("Транзакция отредактирована.");
        } catch (IllegalArgumentException e) {
            System.out.println("Ошибка редактирования транзакции: " + e.getMessage());
        }
    }

    /**
     * Удаляет существующую транзакцию пользователя.
     * <p>
     * Запрашивает ID транзакции и вызывает метод deleteTransaction класса FinancialTracker.
     */
    private static void deleteTransaction() {
        System.out.println("\n--- Удалить Транзакцию ---");
        System.out.print("Введите ID транзакции для удаления: ");
        String transactionIdStr = scanner.nextLine();
        UUID transactionId = UUID.fromString(transactionIdStr);

        try {
            tracker.deleteTransaction(currentUser, transactionId);
            System.out.println("Транзакция удалена.");
        } catch (IllegalArgumentException e) {
            System.out.println("Ошибка удаления транзакции: " + e.getMessage());
        }
    }

    /**
     * Просматривает транзакции пользователя с возможностью фильтрации.
     * <p>
     * Предлагает пользователю показать все транзакции, фильтровать по дате, категории или типу (доход/расход).
     */
    private static void viewTransactions() {
        while (true) {
            System.out.println("\n--- Просмотр Транзакций ---");
            System.out.println("1. Показать все транзакции");
            System.out.println("2. Фильтровать по дате");
            System.out.println("3. Фильтровать по категории");
            System.out.println("4. Фильтровать по типу (доход/расход)");
            System.out.println("0. Назад");
            System.out.print("Выберите действие: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    viewUserTransactionsWithId(currentUser);
                    break;
                case "2":
                    filterTransactionsByDate();
                    break;
                case "3":
                    filterTransactionsByCategory();
                    break;
                case "4":
                    filterTransactionsByType();
                    break;
                case "0":
                    return;
                default:
                    System.out.println("Неверный выбор. Попробуйте снова.");
            }
        }
    }

    /**
     * Выводит список транзакций пользователя с их ID.
     *
     * @param user Пользователь, чьи транзакции нужно отобразить.
     */
    private static void viewUserTransactionsWithId(User user) {
        if (user == null) {
            System.out.println("Пользователь не найден.");
            return;
        }

        List<Transaction> transactions = user.getTransactions();
        if (transactions.isEmpty()) {
            System.out.println("Список транзакций пуст.");
            return;
        }

        System.out.println("\n--- Транзакции ---");
        for (Transaction transaction : transactions) {
            System.out.println("ID: " + transaction.getId() + ", " + transaction);
        }
    }

    /**
     * Фильтрует транзакции по дате.
     * <p>
     * Запрашивает начальную и конечную даты и вызывает метод filterTransactionsByDate класса FinancialTracker.
     */
    private static void filterTransactionsByDate() {
        System.out.println("\n--- Фильтрация Транзакций по Дате ---");
        System.out.print("Начальная дата (YYYY-MM-DD): ");
        LocalDate startDate = LocalDate.parse(scanner.nextLine());
        System.out.print("Конечная дата (YYYY-MM-DD): ");
        LocalDate endDate = LocalDate.parse(scanner.nextLine());

        List<Transaction> filteredTransactions = tracker.filterTransactionsByDate(currentUser, startDate, endDate);
        displayTransactions(filteredTransactions);
    }

    /**
     * Фильтрует транзакции по категории.
     * <p>
     * Запрашивает категорию и вызывает метод filterTransactionsByCategory класса FinancialTracker.
     */
    private static void filterTransactionsByCategory() {
        System.out.println("\n--- Фильтрация Транзакций по Категории ---");
        System.out.print("Категория: ");
        String category = scanner.nextLine();

        List<Transaction> filteredTransactions = tracker.filterTransactionsByCategory(currentUser, category);
        displayTransactions(filteredTransactions);
    }

    /**
     * Фильтрует транзакции по типу (доход/расход).
     * <p>
     * Запрашивает тип (доход/расход) и вызывает метод filterTransactionsByType класса FinancialTracker.
     */
    private static void filterTransactionsByType() {
        System.out.println("\n--- Фильтрация Транзакций по Типу ---");
        System.out.print("Доход (true) / Расход (false): ");
        boolean isIncome = Boolean.parseBoolean(scanner.nextLine());

        List<Transaction> filteredTransactions = tracker.filterTransactionsByType(currentUser, isIncome);
        displayTransactions(filteredTransactions);
    }

    /**
     * Выводит список транзакций.
     *
     * @param transactions Список транзакций для отображения.
     */
    private static void displayTransactions(List<Transaction> transactions) {
        if (transactions.isEmpty()) {
            System.out.println("Транзакции не найдены.");
            return;
        }

        System.out.println("\n--- Транзакции ---");
        for (Transaction transaction : transactions) {
            System.out.println(transaction);
        }
    }

    // --- Управление Бюджетом ---

    /**
     * Управляет бюджетом пользователя.
     * <p>
     * Позволяет установить месячный лимит бюджета.
     */
    private static void manageBudget() {
        System.out.println("\n--- Управление Бюджетом ---");
        System.out.print("Установите месячный бюджет: ");
        double monthlyLimit = Double.parseDouble(scanner.nextLine());
        try {
            tracker.setBudget(currentUser, monthlyLimit);
            System.out.println("Бюджет установлен.");
        } catch (IllegalArgumentException e) {
            System.out.println("Ошибка установки бюджета: " + e.getMessage());
        }
    }

    // --- Управление Профилем ---

    private static void manageProfile() {
        System.out.println("\n--- Управление Профилем ---");
        System.out.println("Функциональность в разработке.");
    }

    // --- Статистика и Аналитика ---
    private static void showStatistics() {
        System.out.println("\n--- Статистика и Аналитика ---");
        System.out.println("Функциональность в разработке.");
    }

    // --- Администрирование ---
    private static void adminMenu() {
        System.out.println("\n--- Меню Администрирования ---");
        System.out.println("Функциональность в разработке.");
    }
}