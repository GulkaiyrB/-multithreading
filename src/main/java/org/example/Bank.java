package org.example;

import java.util.Random;
public class Bank {
    private static final int NUM_ACCOUNTS = 5;
    private static final int INITIAL_BALANCE = 1500;

    public static void main(String[] args) {
        Account[] accounts = new Account[NUM_ACCOUNTS];
        for (int i = 0; i < NUM_ACCOUNTS; i++) {
            accounts[i] = new Account(INITIAL_BALANCE, i);
        }

        Random random = new Random();

        Thread[] threads = new Thread[2 * NUM_ACCOUNTS];
        int index = 0;
        for (int i = 0; i < NUM_ACCOUNTS; i++) {
            threads[index++] = new Thread(() -> {
                int fromAccount = random.nextInt(NUM_ACCOUNTS);
                int toAccount = random.nextInt(NUM_ACCOUNTS);
                int amount = random.nextInt(INITIAL_BALANCE);
                if (fromAccount != toAccount) {
                    if (accounts[fromAccount].withdraw(amount)) {
                        accounts[toAccount].deposit(amount);
                    }
                }
            });

            threads[index++] = new Thread(() -> {
                int account = random.nextInt(NUM_ACCOUNTS);
                int amount = random.nextInt(INITIAL_BALANCE);
                accounts[account].deposit(amount);
            });
        }

        for (Thread thread : threads) {
            thread.start();
        }

        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println();
        System.out.println("Информация о балансе счетов: ");
        for (Account account : accounts) {
            System.out.println("Счет " + account.getAccountNumber()
                    + " баланс составляет: " + account.getBalance());
        }
    }
}

