import java.util.Scanner;

// Класс консольного интерфейса для работы с матрицами
public class MatrixConsole {
    private static Scanner scanner = new Scanner(System.in);

    // Последний полученный результат (хранится в течение сеанса)
    private static Matrix lastResult = null;

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

    // Читает матрицу, но если есть lastResult, предлагает использовать его
    private static Matrix readMatrixOrLast(String name) {
        if (lastResult != null) {
            System.out.print("Использовать последний результат как матрицу " + name + "? (y/n): ");
            String answer = scanner.next();
            if (answer.equalsIgnoreCase("y")) {
                System.out.println("Матрица " + name + " = последний результат:");
                System.out.println(lastResult);
                return lastResult;
            }
        }
        return readMatrix(name);
    }

    // Главный метод: показывает меню и выполняет выбранную операцию
    public static void run() {
        // Используем точку как разделитель дробной части
        scanner.useLocale(java.util.Locale.US);

        while (true) {
            System.out.println();
            if (lastResult != null) {
                System.out.println("Последний результат:");
                System.out.println(lastResult);
            }
            System.out.println("Выберите операцию:");
            System.out.println("1 - Сложение");
            System.out.println("2 - Вычитание");
            System.out.println("3 - Умножение");
            System.out.println("4 - Деление");
            System.out.println("5 - Транспонирование");
            System.out.println("6 - Определитель");
            System.out.println("0 - Выход");
            System.out.print("Ваш выбор: ");
            int choice = scanner.nextInt();

            if (choice == 0) {
                System.out.println("Выход из программы.");
                break;
            }

            try {
                switch (choice) {
                    // Сложение двух матриц
                    case 1: {
                        Matrix a = readMatrixOrLast("A");
                        Matrix b = readMatrixOrLast("B");
                        lastResult = a.add(b);
                        System.out.println("Результат:");
                        System.out.println(lastResult);
                        break;
                    }
                    // Вычитание двух матриц
                    case 2: {
                        Matrix a = readMatrixOrLast("A");
                        Matrix b = readMatrixOrLast("B");
                        lastResult = a.sub(b);
                        System.out.println("Результат:");
                        System.out.println(lastResult);
                        break;
                    }
                    // Умножение двух матриц
                    case 3: {
                        Matrix a = readMatrixOrLast("A");
                        Matrix b = readMatrixOrLast("B");
                        lastResult = a.mul(b);
                        System.out.println("Результат:");
                        System.out.println(lastResult);
                        break;
                    }
                    // Деление матриц: A / B = A * B^(-1)
                    case 4: {
                        Matrix a = readMatrixOrLast("A");
                        Matrix b = readMatrixOrLast("B");
                        lastResult = a.divide(b);
                        System.out.println("Результат:");
                        System.out.println(lastResult);
                        break;
                    }
                    // Транспонирование одной матрицы
                    case 5: {
                        Matrix a = readMatrixOrLast("A");
                        lastResult = a.transpose();
                        System.out.println("Результат:");
                        System.out.println(lastResult);
                        break;
                    }
                    // Определитель одной квадратной матрицы
                    case 6: {
                        Matrix a = readMatrixOrLast("A");
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
}