package org.academy.spring_mvc_object_mapper.initializer;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.academy.spring_mvc_object_mapper.model.Customer;
import org.academy.spring_mvc_object_mapper.model.Order;
import org.academy.spring_mvc_object_mapper.model.Product;
import org.academy.spring_mvc_object_mapper.repository.CustomerRepository;
import org.academy.spring_mvc_object_mapper.repository.OrderRepository;
import org.academy.spring_mvc_object_mapper.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
@Getter
public class DataInitializer implements ApplicationRunner {

    @Autowired
    private final ProductRepository productRepository;

    @Autowired
    private final CustomerRepository customerRepository;

    @Autowired
    private final OrderRepository orderRepository;

    private Data data;

    @Override
    public void run(ApplicationArguments args) {
        Customer customer = addCustomer();
        Order singleOrder = addSingleOrder(customer);
        List<Product> products = addProducts(singleOrder, 5);

        data = new Data(customer, singleOrder, products);
    }

    private Customer addCustomer() {
        var customer = new Customer();
        customer.setName("username");
        customer.setEmail("test@email.com");
        customer.setPhoneNumber("+123456789");

        return customerRepository.save(customer);
    }

    private Order addSingleOrder(Customer customer) {
        var order = new Order();
        order.setAddress("address");
        order.setStatus("In progress");
        order.setCustomer(customer);

        return orderRepository.save(order);
    }

    private List<Product> addProducts(Order order, Integer amount) {
        List<Product> products = new ArrayList<>();

        for (int i = 1; i < amount + 1; i++) {
            var product = new Product();
            product.setName("product" + i);
            product.setDescription("description" + i);
            product.setPrice(100 * i);
            product.setAmount(4 * i);
            product.setOrder(order);

            var testProduct = productRepository.save(product);
            products.add(testProduct);
        }

        return products;
    }

    @AllArgsConstructor
    @Getter
    public static class Data {
        private Customer customer;
        private Order order;
        private List<Product> products;
    }
}
