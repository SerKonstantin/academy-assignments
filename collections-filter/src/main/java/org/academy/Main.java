package org.academy;

public class Main {
    public static void main(String[] args) {
        var numbers = new Object[]{1, 3, 5};
        var collection = new CustomCollection();
        var filter = new CustomFilter();

        Object transformedNumbers = collection.filter(numbers, filter);
    }
}