package org.academy;

import java.util.Arrays;

public class CustomCollection {
    public <T> T[] filter(T[] input, Filter<T> filter) {
        try {
            T[] result = Arrays.copyOf(input, input.length);

            for (var i = 0; i < input.length; i++) {
                result[i] = filter.apply(input[i]);
            }

            return result;
        } catch (Exception e) {
            System.out.println("Filter apply failed with message: " + e.getMessage());
            return input;
        }
    }
}
