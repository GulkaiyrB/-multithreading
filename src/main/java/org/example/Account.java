package org.example;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Account {
    private int balance;
    private final int accountNumber;
    private final Lock lock = new ReentrantLock();

    public Account(int balance, int accountNumber) {
        this.balance = balance;
        this.accountNumber = accountNumber;
    }

    public void deposit(int amount) {
        lock.lock();
        try {
            balance += amount;
            System.out.println("Счет " + accountNumber + " пополнен на сумму:  "+ amount  + " сом");
        } finally {
            lock.unlock();
        }
    }

    public boolean withdraw(int amount) {
        lock.lock();
        try {
            if (balance >= amount) {
                balance -= amount;
                System.out.println("Со счета " + accountNumber + " снята сумма:  "+ amount  + " сом");
                return true;
            }
            return false;
        } finally {
            lock.unlock();
        }
    }


    public int getBalance() {
        return balance;
    }

    public int getAccountNumber() {
        return accountNumber;
    }
}



