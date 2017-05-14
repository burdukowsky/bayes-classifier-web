package tk.burdukowsky.BayesClassifierWeb;

import java.util.Arrays;

/**
 * Наивный Байесовский классификатор
 * Создано 13.05.2017 11:17.
 *
 * @author Станислав Бурдуковский
 */
class Classifier {
    /**
     * Возможные классы
     */
    private int[] classes;

    /**
     * Количество свойств объекта
     */
    private int propertiesCount;

    /**
     * Обучающая выборка
     */
    private int[][] trainingSample;

    /**
     * Ответы
     */
    private int[] answers;

    /**
     * Массивы значимостей
     */
    private double[][] probabilitySignificanceClass;

    /**
     * Конструктор
     *
     * @param trainingSample Обучающая выборка
     * @param answers        Ответы
     */
    Classifier(int[][] trainingSample, int[] answers) {
        this.classes = getUniqueValues(answers);
        this.propertiesCount = trainingSample[0].length;
        this.trainingSample = trainingSample;
        this.answers = answers;
        this.probabilitySignificanceClass = new double[this.classes.length][this.propertiesCount];
        train();
    }

    /**
     * Возвращает массив уникальных значений входящего массива
     *
     * @param arrayWithDuplicates массив с дубликаттами
     * @return массив уникальных значений
     */
    private int[] getUniqueValues(int[] arrayWithDuplicates) {
        return Arrays.stream(arrayWithDuplicates).distinct().toArray();
    }

    /**
     * Обучение классификатора
     */
    private void train() {
        // задача обучения - найти массивы значимостей для каждого класса
        // в коде это probabilitySignificanceClass
        // начинаем решать
        // тут мы посчитали, сколько объектов каждого класса в обучающей выборке
        int[] classItemsCount = new int[classes.length];
        for (int i = 0; i < classes.length; i++) {
            for (int j = 0; j < trainingSample.length; j++) {
                if (classes[i] == answers[j]) {
                    classItemsCount[i]++;
                }
            }
        }

        // сейчас мы можем посчитать вероятность встретить нужный класс
        double[] probabilityClasses = new double[classes.length];
        for (int i = 0; i < classes.length; i++) {
            probabilityClasses[i] = (double) classItemsCount[i] / trainingSample.length;
        }

        // [вероятность встретить этот признак среди всех объектов]
        double[] probabilityProperties = new double[propertiesCount];
        // количества имеющихся у объектов свойств
        int[] countPropertiesMatches = new int[propertiesCount];
        for (int i = 0; i < trainingSample.length; i++) {
            for (int j = 0; j < propertiesCount; j++) {
                if (trainingSample[i][j] == 1) {
                    countPropertiesMatches[j]++;
                }
            }
        }
        // теперь сама вероятность встретить этот признак среди всех объектов
        for (int i = 0; i < propertiesCount; i++) {
            probabilityProperties[i] = (double) countPropertiesMatches[i] / trainingSample.length;
        }

        // [вероятность встретить этот признак среди всех объектов нужного класса]
        double[][] probabilityPropertiesClass = new double[classes.length][propertiesCount];
        // количества имеющихся у объектов каждого класса свойств
        int[][] countPropertiesClassMatches = new int[classes.length][propertiesCount];
        for (int i = 0; i < classes.length; i++) {
            for (int j = 0; j < trainingSample.length; j++) {
                if (answers[j] == classes[i]) { // если набор принадлежит текущему классу
                    for (int k = 0; k < propertiesCount; k++) {
                        if (trainingSample[j][k] == 1) {
                            countPropertiesClassMatches[i][k]++;
                        }
                    }
                }
            }
        }

        // теперь вероятность...
        for (int i = 0; i < classes.length; i++) {
            for (int j = 0; j < propertiesCount; j++) {
                probabilityPropertiesClass[i][j] = (double) countPropertiesClassMatches[i][j] / classItemsCount[i];
            }
        }

        // наконец, найдем массивы значимостей для каждого класса
        // чтобы найти значимость конкретного признака для конкретного класса, нужно:
        // [вероятность встретить этот признак среди всех объектов нужного класса]
        // умножить на [вероятность встретить нужный класс] и поделить на
        // [вероятность встретить этот признак среди всех объектов]
        // это и есть теорема Байеса
        for (int i = 0; i < classes.length; i++) {
            for (int j = 0; j < propertiesCount; j++) {
                probabilitySignificanceClass[i][j] =
                        probabilityPropertiesClass[i][j] * probabilityClasses[i] / probabilityProperties[j];
            }
        }
    }

    /**
     * Определяет класс входного объекта
     *
     * @param test входной объект
     * @return класс
     */
    int getClazz(int[] test) {
        // на основе обучающей выборки нужно получить массивы значимостей для каждого класса
        // затем найти скалярное произведение входящего массива и массива значимостей для каждого класса
        // чьё скалярное произведение больше, к тому классу объект и принадлежит
        //
        // значит, нужно найти массив размерностью равной количеству классов
        // в котором будут значения скалярных умножений
        // потом среди них найти максимальное
        // индекс максимального элемента - индекс класса, которому принадлежит тестовая выборка
        double[] results = new double[classes.length];
        for (int i = 0; i < classes.length; i++) {
            for (int j = 0; j < propertiesCount; j++) {
                results[i] += probabilitySignificanceClass[i][j] * test[j];
            }
        }

        // теперь найдем максимальное
        double max = Double.MIN_VALUE;
        int indexMaxResult = 0;
        for (int i = 0; i < classes.length; i++) {
            if (results[i] > max) {
                max = results[i];
                indexMaxResult = i;
            }
        }

        return classes[indexMaxResult];
    }
}
