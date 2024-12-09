package org.academy;

import java.util.HashMap;

public class Util {
    public static HashMap<Object, Integer> countOfElements(Object[] elements) {
        var result = new HashMap<Object, Integer>();

        for(var element : elements) {
            if (result.containsKey(element)) {
                var currentCount = result.get(element);
                result.put(element, currentCount + 1);
            } else {
                result.put(element, 1);
            }
        }

        return result;
    }
}
