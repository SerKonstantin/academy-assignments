package org.academy.spring_mvc_with_jsonview;

import org.academy.spring_mvc_with_jsonview.model.Good;

import java.util.ArrayList;
import java.util.List;

public class StaticTestDataGenerator {

    private static final List<Good> goods = new ArrayList<>();

    public static List<Good> generateSharedTestGoods() {
        if (!goods.isEmpty()) {
            return goods;
        }

        var good1 = new Good();
        good1.setName("apple");
        good1.setPrice(200);
        goods.add(good1);

        var good2 = new Good();
        good2.setName("banana");
        good2.setPrice(300);
        goods.add(good2);

        var good3 = new Good();
        good3.setName("candy");
        good3.setPrice(400);
        goods.add(good3);

        return goods;
    }
}
