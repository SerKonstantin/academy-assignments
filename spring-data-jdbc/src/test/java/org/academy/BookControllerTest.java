package org.academy;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.academy.model.Book;
import org.academy.repository.BookRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private ObjectMapper om;

    @Transactional
    @Test
    void getBooks() throws Exception {
        var book = new Book(1L, "title", "author", 1984);
        bookRepository.save(book);

        mockMvc.perform(get("/books"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].title").value("title"));

        bookRepository.deleteById(1L);
    }

    @Transactional
    @Test
    void createBook() throws Exception {
        var book = new Book(2L, "title2", "author2", 1985);

        mockMvc.perform(post("/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(book)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("title2"));

        bookRepository.deleteById(2L);
    }

    @Transactional
    @Test
    void updateBook() throws Exception {
        var book = new Book(3L, "title3", "author3", 1986);
        bookRepository.save(book);

        var newData = """
                {
                    "title": "Game of Thrones 7",
                    "author": "R Martin",
                    "publicationYear": "3842"
                }""";

        mockMvc.perform(put("/books/3")
                        .content(newData)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        bookRepository.deleteById(3L);
    }

}
