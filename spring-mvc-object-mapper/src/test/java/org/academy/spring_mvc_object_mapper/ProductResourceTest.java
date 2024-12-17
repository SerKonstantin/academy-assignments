package org.academy.spring_mvc_object_mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.academy.spring_mvc_object_mapper.dto.ProductDto;
import org.academy.spring_mvc_object_mapper.initializer.DataInitializer;
import org.academy.spring_mvc_object_mapper.model.Order;
import org.academy.spring_mvc_object_mapper.model.Product;
import org.academy.spring_mvc_object_mapper.repository.ProductRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ProductResourceTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private DataInitializer initializer;

    @Autowired
    private ObjectMapper om;

    @Autowired
    private ProductRepository productRepository;

    // Expected values, are set during @BeforeAll setup()
    private Order order;
    private Product product;
    private final String baseUrl = "/api/products";

    @BeforeAll
    public void setup() {
        DataInitializer.Data data = initializer.getData();
        order = data.getOrder();
        product = data.getProducts().get(0);
    }

    @Test
    public void getAllProducts() throws Exception {
        mockMvc.perform(get(baseUrl)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray()) // Assuming 5 products are initialized
                .andExpect(jsonPath("$[0].id").doesNotExist())
                .andExpect(jsonPath("$[0].name").value(product.getName()))
                .andExpect(jsonPath("$[0].price").value(product.getPrice()))
                .andExpect(jsonPath("$[0].amount").value(product.getAmount()))
                .andExpect(jsonPath("$[0].orderId").value(order.getId().toString()));
    }

    @Test
    public void createProduct() throws Exception {
        ProductDto newProductDto = new ProductDto(
                "New Product",
                "New Description",
                200,
                10,
                order.getId()
        );

        mockMvc.perform(post(baseUrl)
                        .content(om.writeValueAsString(newProductDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

        var newProduct = productRepository.findByName("New Product")
                .orElseThrow(() -> new RuntimeException("Продукт не найден после создания"));
        assert newProduct.getDescription().equals(newProductDto.getDescription());
        assert newProduct.getPrice().equals(newProductDto.getPrice());
    }

}
