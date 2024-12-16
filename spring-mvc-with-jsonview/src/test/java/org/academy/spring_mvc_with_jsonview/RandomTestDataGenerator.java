package org.academy.spring_mvc_with_jsonview;

import org.academy.spring_mvc_with_jsonview.model.Good;
import org.academy.spring_mvc_with_jsonview.model.Order;
import org.academy.spring_mvc_with_jsonview.model.User;
import org.academy.spring_mvc_with_jsonview.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RandomTestDataGenerator {

    @Autowired
    private UserRepository userRepository;

    public User generateUser(List<Good> goods) {
        if (goods.size() < 3) {
            throw new RuntimeException("TEST: goods list for user generation should have at least 3 goods");
        }

        var order1 = new Order();
        order1.setGoods(List.of(goods.get(0), goods.get(1)));
        order1.setStatus("Delivered");

        var order2 = new Order();
        order2.setGoods(List.of(goods.get(1), goods.get(2)));
        order2.setStatus("In progress");

        var user = new User();
        user.setUsername("test_user");
        user.setEmail((Math.random() * 1000000000) + "user@test.com"); // For speed, pseudo-unique

        order1.setUser(user);
        order2.setUser(user);
        user.setOrders(List.of(order1, order2));
        userRepository.save(user);      // Orders saved by CascadeType.ALL

        return user;
    }
}
