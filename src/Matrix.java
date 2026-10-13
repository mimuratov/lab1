// Класс матрицы комплексных чисел
public class Matrix {
    // Двумерный массив элементов матрицы
    private Complex[][] data;
    // Количество строк
    private int rows;
    // Количество столбцов
    private int cols;

    // Конструктор: создаёт пустую матрицу заданного размера
    public Matrix(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.data = new Complex[rows][cols];
    }

    // Возвращает элемент матрицы по индексам (i, j)
    public Complex get(int i, int j) {
        return data[i][j];
    }

    // Записывает значение в ячейку (i, j)
    public void set(int i, int j, Complex value) {
        data[i][j] = value;
    }

    // Сложение матриц: поэлементно A[i][j] + B[i][j]
    public Matrix add(Matrix other) {
        if (this.rows != other.rows || this.cols != other.cols) {
            throw new IllegalArgumentException("Размеры матриц не совпадают");
        }
        Matrix result = new Matrix(rows, cols);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                result.set(i, j, this.data[i][j].add(other.data[i][j]));
            }
        }
        return result;
    }

    // Вычитание матриц: поэлементно A[i][j] - B[i][j]
    public Matrix sub(Matrix other) {
        if (this.rows != other.rows || this.cols != other.cols) {
            throw new IllegalArgumentException("Размеры матриц не совпадают");
        }
        Matrix result = new Matrix(rows, cols);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                result.set(i, j, this.data[i][j].sub(other.data[i][j]));
            }
        }
        return result;
    }

    // Умножение матриц: C[i][j] = сумма по k от A[i][k] * B[k][j]
    public Matrix mul(Matrix other) {
        if (this.cols != other.rows) {
            throw new IllegalArgumentException("Несовместимые размеры для умножения");
        }
        Matrix result = new Matrix(this.rows, other.cols);
        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < other.cols; j++) {
                // Накопитель суммы произведений
                Complex sum = new Complex(0, 0);
                for (int k = 0; k < this.cols; k++) {
                    sum = sum.add(this.data[i][k].mul(other.data[k][j]));
                }
                result.set(i, j, sum);
            }
        }
        return result;
    }

    // Деление матриц: A / B = A * B^(-1)
    public Matrix divide(Matrix other) {
        return this.mul(other.inverse());
    }

    // Обратная матрица методом Гаусса–Жордана
    public Matrix inverse() {
        if (rows != cols) {
            throw new IllegalArgumentException("Деление возможно только для квадратных матриц одинакового размера");
        }
        int n = rows;
        // Расширенная матрица [A | E] шириной 2n
        Matrix extended = new Matrix(n, 2 * n);
        // Заполняем левую половину исходной матрицей, правую - единичной
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                extended.set(i, j, this.get(i, j));
            }
            for (int j = 0; j < n; j++) {
                extended.set(i, n + j, new Complex(i == j ? 1 : 0, 0));
            }
        }
        // Приводим левую половину к единичной матрице
        for (int col = 0; col < n; col++) {
            // Ищем ведущую строку с ненулевым элементом в столбце col
            int lead = -1;
            for (int r = col; r < n; r++) {
                if (extended.get(r, col).abs() > 1e-12) {
                    lead = r;
                    break;
                }
            }
            if (lead == -1) {
                throw new ArithmeticException("Матрица вырождена, обратной не существует");
            }
            // Меняем местами строки col и lead, если нужно
            if (lead != col) {
                for (int j = 0; j < 2 * n; j++) {
                    Complex tmp = extended.get(col, j);
                    extended.set(col, j, extended.get(lead, j));
                    extended.set(lead, j, tmp);
                }
            }
            // Делим строку col на ведущий элемент, чтобы на диагонали получилась единица
            Complex leadVal = extended.get(col, col);
            for (int j = 0; j < 2 * n; j++) {
                extended.set(col, j, extended.get(col, j).div(leadVal));
            }
            // Обнуляем столбец col во всех остальных строках
            for (int r = 0; r < n; r++) {
                if (r == col) continue;
                Complex factor = extended.get(r, col);
                for (int j = 0; j < 2 * n; j++) {
                    extended.set(r, j, extended.get(r, j).sub(factor.mul(extended.get(col, j))));
                }
            }
        }
        // Берём правую половину расширенной матрицы - это и есть обратная
        Matrix result = new Matrix(n, n);
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                result.set(i, j, extended.get(i, n + j));
            }
        }
        return result;
    }

    // Транспонирование: элемент (i, j) переезжает в (j, i)
    public Matrix transpose() {
        Matrix result = new Matrix(cols, rows);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                result.set(j, i, this.data[i][j]);
            }
        }
        return result;
    }

    // Определитель квадратной матрицы
    public Complex determinant() {
        if (rows != cols) {
            throw new IllegalArgumentException("Определитель только для квадратных матриц");
        }
        return det(data, rows);
    }

    // Рекурсивное вычисление определителя разложением по первой строке
    private Complex det(Complex[][] m, int n) {
        // Базовый случай: матрица 1x1
        if (n == 1) {
            return m[0][0];
        }
        // Базовый случай: матрица 2x2 — a*d - b*c
        if (n == 2) {
            return m[0][0].mul(m[1][1]).sub(m[0][1].mul(m[1][0]));
        }
        // Накопитель результата
        Complex result = new Complex(0, 0);
        // Идём по элементам первой строки
        for (int col = 0; col < n; col++) {
            // Строим минор: вычёркиваем первую строку и столбец col
            Complex[][] minor = new Complex[n - 1][n - 1];
            for (int i = 1; i < n; i++) {
                int minorCol = 0;
                for (int j = 0; j < n; j++) {
                    if (j == col) continue;
                    minor[i - 1][minorCol] = m[i][j];
                    minorCol++;
                }
            }
            // Текущий член разложения: элемент * определитель минора
            Complex term = m[0][col].mul(det(minor, n - 1));
            // Знак чередуется: +, -, +, - и т.д.
            if (col % 2 == 0) {
                result = result.add(term);
            } else {
                result = result.sub(term);
            }
        }
        return result;
    }

    // Строковое представление матрицы: элементы по строкам
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                result.append(data[i][j]).append("  ");
            }
            result.append("\n");
        }
        return result.toString();
    }
}