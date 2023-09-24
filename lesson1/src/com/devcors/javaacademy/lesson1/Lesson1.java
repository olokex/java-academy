package com.devcors.javaacademy.lesson1;

public class Lesson1 {

    /**
     * <strong>Homework 1</strong>
     * Create class wallet with one numeric attribute for actual balance {@link Wallet#actualBalance}.
     * {@link Wallet} class will contain those methods:
     * <ul>
     *     <li>{@link Wallet#Wallet()} (int)} - default constructor</li>
     *     <li>{@link Wallet#saveMoney(int)} - this method has one numeric param and will add param value into {@link Wallet#actualBalance}</li>
     *     <li>{@link Wallet#takeMoney(int)} - this method has one numeric param and subtracts param value from {@link Wallet#actualBalance}</li>
     *     <li>{@link Wallet#actualBalance()} - this method returns {@link Wallet#actualBalance}</li>
     *     <li>{@link Wallet#toString()} ()} - this method will return String message: "Actual balance is: {@link Wallet#actualBalance}"</li>
     * </ul>
     * <p>
     * When you will have {@link Wallet} class ready create new instance and test all wallet methods in {@link Lesson1#main(String[])}
     * Use System.out.println for print results into console.
     * @param args
     */
    public static void main(String[] args) {
        Wallet wallet = new Wallet(500);
        System.out.printf("Actual balance is: %d%n", wallet.actualBalance());
        wallet.saveMoney(300);
        System.out.printf("Actual balance is: %d%n", wallet.actualBalance());
        wallet.takeMoney(800);
        System.out.printf("Actual balance is: %d%n", wallet.actualBalance());

        System.out.println(wallet);
    }
}