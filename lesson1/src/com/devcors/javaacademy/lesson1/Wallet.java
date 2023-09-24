package com.devcors.javaacademy.lesson1;

public class Wallet {
    private int actualBalance;

    public Wallet(int amount) {
        actualBalance = amount;
    }

    public void saveMoney(int amount) {
        actualBalance += amount;
    }

    public void takeMoney(int amount) {
        actualBalance -= amount;
    }

    public int actualBalance() {
        return actualBalance;
    }

    public String toString() {
        return "Actual balance is: %d".formatted(actualBalance());
    }
}
