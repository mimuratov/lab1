import java.util.Scanner;

// Класс консольного интерфейса для работы с матрицами
public class MatrixConsole {
    private static Scanner scanner = new Scanner(System.in);

    // Читает матрицу с заданным именем из консоли
    public static Matrix readMatrix(String name) {
        System.out.print("Введите количество строк матрицы " + name + ": ");
        int rows = scanner.nextInt();
        System.out.print("Введите количество столбцов матрицы " + name + ": ");
        int cols = scanner.nextInt();
        Matrix m = new Matrix(rows, cols);
        System.out.println("Введите элементы матрицы " + name + " (действительная и мнимая части через пробел):");
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                // Индексы для пользователя начинаются с 1
                System.out.print(name + "[" + (i + 1) + "][" + (j + 1) + "]: ");
                double re = scanner.nextDouble();
                double im = scanner.nextDouble();
                m.set(i, j, new Complex(re, im));
            }
        }
        System.out.println("Введённая матрица " + name + ":");
        System.out.println(m);
        return m;
    }

    // Главный метод: показывает меню и выполняет выбранную операцию
    public static void run() {
        // Используем точку как разделитель дробной части
        scanner.useLocale(java.util.Locale.US);
        System.out.println("Выберите операцию:");
        System.out.println("1 - Сложение");
        System.out.println("2 - Вычитание");
        System.out.println("3 - Умножение");
        System.out.println("4 - Деление");
        System.out.println("5 - Транспонирование");
        System.out.println("6 - Определитель");
        System.out.print("Ваш выбор: ");
        int choice = scanner.nextInt();

        try {
            switch (choice) {
                // Сложение двух матриц
                case 1: {
                    Matrix a = readMatrix("A");
                    Matrix b = readMatrix("B");
                    System.out.println("Результат:");
                    System.out.println(a.add(b));
                    break;
                }
                // Вычитание двух матриц
                case 2: {
                    Matrix a = readMatrix("A");
                    Matrix b = readMatrix("B");
                    System.out.println("Результат:");
                    System.out.println(a.sub(b));
                    break;
                }
                // Умножение двух матриц
                case 3: {
                    Matrix a = readMatrix("A");
                    Matrix b = readMatrix("B");
                    System.out.println("Результат:");
                    System.out.println(a.mul(b));
                    break;
                }
                // Деление матриц: A / B = A * B^(-1)
                case 4: {
                    Matrix a = readMatrix("A");
                    Matrix b = readMatrix("B");
                    System.out.println("Результат:");
                    System.out.println(a.divide(b));
                    break;
                }
                // Транспонирование одной матрицы
                case 5: {
                    Matrix a = readMatrix("A");
                    System.out.println("Результат:");
                    System.out.println(a.transpose());
                    break;
                }
                // Определитель одной квадратной матрицы
                case 6: {
                    Matrix a = readMatrix("A");
                    System.out.println("Определитель: " + a.determinant());
                    break;
                }
                default:
                    System.out.println("Неверный выбор");
            }
        } catch (Exception e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }
}