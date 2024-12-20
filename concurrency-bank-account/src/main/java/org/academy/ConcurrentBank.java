package org.academy;

import java.math.BigDecimal;
import java.util.ArrayList;

import static org.academy.Util.toFormat;
import static org.academy.Util.orderLocksToArray;

public class ConcurrentBank {
    private final ArrayList<BankAccount> accounts;

    public ConcurrentBank() {
        accounts = new ArrayList<>();
    }

    public BankAccount createAccount(String initialBalance) {
        var account = new BankAccount(toFormat(initialBalance));
        accounts.add(account);
        return account;
    }

    public BankAccount createAccount() {
        return(createAccount("0.00"));
    }

    public void transfer(BankAccount acc1, BankAccount acc2, String amount) {
        if (acc1 == null || acc2 == null) {
            throw new RuntimeException("Invalid arguments");
        }

        var lockOrderArray = orderLocksToArray(acc1.getLock(), acc2.getLock());

        synchronized (lockOrderArray[0]) {
            synchronized (lockOrderArray[1]) {
                var formattedAmount = toFormat(amount);

                BigDecimal initAmount = acc1.getBalance();
                if (initAmount.subtract(formattedAmount).compareTo(BigDecimal.ZERO) < 0) {
                    System.out.println("Недостаточно средств");
                } else {
                    acc1.withdraw(formattedAmount);
                    acc2.deposit(formattedAmount);
                }
            }
        }
    }

    public synchronized BigDecimal getTotalBalance() {
        var totalBalance = new BigDecimal("0.00");
        for (var account : accounts) {
            totalBalance = totalBalance.add(account.getBalance());
        }
        return totalBalance;
    }

}
