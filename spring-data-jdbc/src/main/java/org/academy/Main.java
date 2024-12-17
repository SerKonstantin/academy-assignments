package org.academy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

@SpringBootApplication
public class Main implements CommandLineRunner {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }


    // Create table in db to work with
    @Override
    public void run(String... args) throws Exception {
        executeSchemaSQL();
    }

    private void executeSchemaSQL() {
        try {
            var resource = new ClassPathResource("schema.sql");
            var reader = new BufferedReader(new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8));
            StringBuilder sqlScript = new StringBuilder();

            String line;
            while ((line = reader.readLine()) != null) {
                sqlScript.append(line).append(" ");
            }

            jdbcTemplate.execute(sqlScript.toString());
        } catch (Exception e) {
            System.err.println("Не удалось создать таблицу при старте приложения. Ошибка: " + e.getMessage());
        }
    }
}