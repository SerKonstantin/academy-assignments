package org.academy;

public class Main {
    public static void main(String[] args) {
        var testData = new String[]{"cat", "dog", "duck", "dog", "dog", "cat"};

        var countData = Util.countOfElements(testData);

        System.out.println(countData);
    }
}