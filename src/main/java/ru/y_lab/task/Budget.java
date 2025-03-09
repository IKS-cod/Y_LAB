package main.java.ru.y_lab.task;

/**
 * Представляет бюджет пользователя.
 * Хранит информацию о ежемесячном лимите расходов.
 */
public class Budget {

    private double monthlyLimit;

    /**
     * Создает новый объект Budget.
     *
     * @param monthlyLimit Ежемесячный лимит расходов для бюджета.
     */
    public Budget(double monthlyLimit) {
        this.monthlyLimit = monthlyLimit;
    }

    /**
     * Возвращает ежемесячный лимит расходов.
     *
     * @return Ежемесячный лимит расходов.
     */
    public double getMonthlyLimit() {
        return monthlyLimit;
    }

    /**
     * Устанавливает ежемесячный лимит расходов.
     *
     * @param monthlyLimit Новый ежемесячный лимит расходов.
     */
    public void setMonthlyLimit(double monthlyLimit) {
        this.monthlyLimit = monthlyLimit;
    }

    /**
     * Возвращает строковое представление объекта Budget. Включает ежемесячный лимит.
     *
     * @return Строковое представление объекта.
     */
    @Override
    public String toString() {
        return "Budget{" +
                "monthlyLimit=" + monthlyLimit +
                '}';
    }
}






