package main.java.ru.y_lab.task;

/**
 * Представляет финансовую цель пользователя.
 * Хранит информацию о целевой сумме и накопленной сумме.
 */
public class Goal {

    private double targetAmount;
    private double savedAmount;

    /**
     * Создает новый объект Goal.
     *
     * @param targetAmount Целевая сумма для достижения цели.
     */
    public Goal(double targetAmount) {
        this.targetAmount = targetAmount;
        this.savedAmount = 0.0;
    }

    /**
     * Возвращает целевую сумму цели.
     *
     * @return Целевая сумма цели.
     */
    public double getTargetAmount() {
        return targetAmount;
    }

    /**
     * Устанавливает целевую сумму цели.
     *
     * @param targetAmount Новая целевая сумма цели.
     */
    public void setTargetAmount(double targetAmount) {
        this.targetAmount = targetAmount;
    }

    /**
     * Возвращает накопленную сумму для достижения цели.
     *
     * @return Накопленная сумма.
     */
    public double getSavedAmount() {
        return savedAmount;
    }

    /**
     * Устанавливает накопленную сумму для достижения цели.
     *
     * @param savedAmount Новая накопленная сумма.
     */
    public void setSavedAmount(double savedAmount) {
        this.savedAmount = savedAmount;
    }


    /**
     * Возвращает строковое представление объекта Goal.  Включает целевую сумму и накопленную сумму.
     *
     * @return Строковое представление объекта.
     */
    @Override
    public String toString() {
        return "Goal{" +
                "targetAmount=" + targetAmount +
                ", savedAmount=" + savedAmount +
                '}';
    }
}








