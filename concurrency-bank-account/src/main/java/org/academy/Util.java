package org.academy;

import java.math.BigDecimal;

public class Util {
    public static BigDecimal toFormat(String stringValue) {
        var value = new BigDecimal(stringValue);
        if (value.scale() == 2) {
            return value;
        } else {
            throw new RuntimeException("ERROR: некорректный формат валюты");
        }
    }
}
