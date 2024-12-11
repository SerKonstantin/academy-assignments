package org.academy;

import java.math.BigDecimal;
import java.util.concurrent.locks.ReentrantLock;

public class BankAccount {
    private BigDecimal balance;
    private final ReentrantLock lock = new ReentrantLock();

    public BankAccount(BigDecimal initialBalance) {
        this.balance = initialBalance;
    }

    // Под капотом присвоение значения в BigDecimal выполняется в несколько шагов
    // Поэтому применяем synchronized даже для пополнения
    public synchronized BigDecimal deposit(BigDecimal amount) {
        balance = balance.add(amount);
        return balance;
    }

    public synchronized BigDecimal withdraw(BigDecimal amount) {
        var newBalance = balance.subtract(amount);
        if (newBalance.compareTo(BigDecimal.ZERO) < 0) {
            System.out.println("Недостаточно средств");
            return balance;
        } else {
            balance = newBalance;
            return newBalance;
        }
    }

    public synchronized BigDecimal getBalance() {
        return balance;
    }

    public synchronized ReentrantLock getLock() {
        return lock;
    }
}
