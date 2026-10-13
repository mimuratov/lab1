// Класс комплексного числа вида a + bi
public class Complex {
    // Действительная часть a
    private double re;
    // Мнимая часть b
    private double im;

    // Конструктор: создаёт комплексное число a + bi
    public Complex(double re, double im) {
        this.re = re;
        this.im = im;
    }

    // Сложение: (a1 + b1*i) + (a2 + b2*i) = (a1 + a2) + (b1 + b2)i
    public Complex add(Complex other) {
        double newRe = this.re + other.re;
        double newIm = this.im + other.im;
        return new Complex(newRe, newIm);
    }

    // Вычитание: (a1 + b1*i) - (a2 + b2*i) = (a1 - a2) + (b1 - b2)i
    public Complex sub(Complex other) {
        double newRe = this.re - other.re;
        double newIm = this.im - other.im;
        return new Complex(newRe, newIm);
    }

    // Умножение: (a1 + b1*i) * (a2 + b2*i) = (a1*a2 - b1*b2) + (a1*b2 + b1*a2)i
    public Complex mul(Complex other) {
        double newRe = this.re * other.re - this.im * other.im;
        double newIm = this.re * other.im + this.im * other.re;
        return new Complex(newRe, newIm);
    }

    // Деление: (a1 + b1*i) / (a2 + b2*i) = ((a1*a2 + b1*b2) + (b1*a2 - a1*b2)i) / (a2^2 + b2^2)
    public Complex div(Complex other) {
        // Знаменатель формулы деления: a2^2 + b2^2
        double denom = other.re * other.re + other.im * other.im;
        if (denom == 0) {
            throw new ArithmeticException("Деление на ноль");
        }
        double newRe = (this.re * other.re + this.im * other.im) / denom;
        double newIm = (this.im * other.re - this.re * other.im) / denom;
        return new Complex(newRe, newIm);
    }

    // Модуль комплексного числа: sqrt(a^2 + b^2)
    public double abs() {
        return Math.sqrt(re * re + im * im);
    }

    // Строковое представление вида "3,00+2,00i" или "3,00-2,00i"
    @Override
    public String toString() {
        if (im >= 0) {
            return String.format("%.2f+%.2fi", re, im);
        } else {
            return String.format("%.2f%.2fi", re, im);
        }
    }
}