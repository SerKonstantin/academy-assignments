package org.academy;

import java.math.BigDecimal;
import java.sql.Array;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Util {
    public static BigDecimal toFormat(String stringValue) {
        var value = new BigDecimal(stringValue);
        if (value.scale() == 2) {
            return value;
        } else {
            throw new RuntimeException("ERROR: некорректный формат валюты");
        }
    }

    public static Lock[] orderLocksToArray(Lock lock1, Lock lock2) {
        var hash1 = lock1.hashCode();
        var hash2 = lock2.hashCode();

        if ( hash1 == hash2 || hash1 == 0 || hash2 == 0) {
            throw new RuntimeException("Некорректная работа Utils.orderLocksToArray, проверьте хэш-коды локов");
        }

        return hash1 < hash2 ? new Lock[]{lock1, lock2} : new Lock[]{lock2, lock1};
    }
}
