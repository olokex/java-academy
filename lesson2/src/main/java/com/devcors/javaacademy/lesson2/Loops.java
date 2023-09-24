package com.devcors.javaacademy.lesson2;

public class Loops {

    /**
     * Implement sum of numbers up to "x". For example x = 3 results in 1+2+3 = 6.
     */
    public int sum(int x) {
        if (x == 0) return 0;
        return x + sum(x - 1);
    }

    /**
     * Implement sum of even numbers up to "x".
     */
    public int sumEven(int x) {
        int sum = 0;
        for (int i = 0; i < x; i += 2) {
            sum += i;
        }
        return sum;
    }

    /**
     * Find out after how many iterations of "x" reaches one in case that:
     * <ul>
     *     <li>if x is even, divide by 2</li>
     *     <li>if x is odd, then x = 3*x + 1</li>
     * </ul>
     * For example for x=3, next iteration is 10 (3*3 + 1), then 5 (10 / 2).
     * For x=3 iterations leads to the sequence 3, 10, 5, 16, 8, 4, 2, 1 -> method should return "7", since "x" reached value 1 after 6 iterations.
     */
    public long hailstoneProblem(int x) {
        return 0l;
    }

    /**
     * Your task is to create triangle of size 'x'. which look like this for x = 3:
     *
     * <p>x
     * <p>xx
     * <p>xxx
     */
    public void triangle1(int x) {

    }

    /**
     * Your task is to create triangle of size 'x', which look like this for x = 3:
     *
     * <p>xxx
     * <p>xx
     * <p>x
     */
    public void triangle2(int x) {

    }

    /**
     * Your task is to create triangle of size 'x', which look like this for x = 3:
     *
     * <p>  x
     * <p> xx
     * <p>xxx
     */
    public void triangle3(int x) {

    }
}
