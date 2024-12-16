package org.academy.spring_mvc_pageable;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.academy.spring_mvc_pageable.initializer.DataInitializer;
import org.academy.spring_mvc_pageable.model.Author;
import org.academy.spring_mvc_pageable.model.Book;
import org.academy.spring_mvc_pageable.repository.AuthorRepository;
import org.academy.spring_mvc_pageable.repository.BookRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BookResourceTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper om;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private DataInitializer initializer;

    private final String baseUrl = "/api/books";
    private Author author;
    private List<Book> books = new ArrayList<>();

    @BeforeAll
    public void setup() {
        // Ugly solution, just for speed, data is shared with runtime for Postman poking
        author = initializer.getTestAuthor();
        books = initializer.getTestBooks();
    }

    @Test
    public void getAllDefaultPage() throws Exception {
        mockMvc.perform(get("/api/books"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.content.length()").value(20)) // default size is 20
                .andExpect(jsonPath("$.content[0].name").value("book1"))
                .andExpect(jsonPath("$.content[0].author.firstName").value("first"))
                .andExpect(jsonPath("$.page.number").value(0))
                .andExpect(jsonPath("$.page.totalElements").value(50)) // predefined books amount
                .andExpect(jsonPath("$.page.totalPages").value(3));
    }

    @Test
    public void getAllPageable() throws Exception {
        mockMvc.perform(get(baseUrl)
                        .param("page", "3")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.content.length()").value(10))
                .andExpect(jsonPath("$.content[0].name").value("book31"))
                .andExpect(jsonPath("$.content[0].author.firstName").value("first"))
                .andExpect(jsonPath("$.page.number").value(3))
                .andExpect(jsonPath("$.page.totalElements").value(50))
                .andExpect(jsonPath("$.page.totalPages").value(5))
                ;
    }

    @Test
    public void getAllPageableOutOfBounds() throws Exception {
        mockMvc.perform(get(baseUrl)
                        .param("page", "16")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.content[0].name").doesNotExist());
    }

    @Test
    public void getOne() throws Exception {
        Book book = books.get(0);

        mockMvc.perform(get(baseUrl + "/" + book.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(book.getId().toString()))
                .andExpect(jsonPath("$.name").value("book1"))
                .andExpect(jsonPath("$.publicationYear").value("1984"))
                .andExpect(jsonPath("$.author.firstName").value("first"))
                .andExpect(jsonPath("$.author.lastName").value("last"));
    }

    @Test
    public void getOneWithInvalidId() throws Exception {
        mockMvc.perform(get(baseUrl  + "/" + UUID.randomUUID()))
                .andExpect(status().isNotFound());
    }

    @Transactional
    @Test
    public void create() throws Exception {
        var anotherAuthor = new Author();
        anotherAuthor.setFirstName("John");
        anotherAuthor.setLastName("Smith");
        authorRepository.save(anotherAuthor);

        var book = new Book();
        book.setName("New Book");
        book.setPublicationYear("1812");
        book.setAuthor(anotherAuthor);

        mockMvc.perform(post(baseUrl)
                        .content(om.writeValueAsString(book))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

        bookRepository.deleteAll(bookRepository.findByAuthorId(anotherAuthor.getId()));
        authorRepository.delete(anotherAuthor);
    }

    @Test
    public void createWithInvalidYearFormat() throws Exception {
        var book = new Book();
        book.setName("New Book");
        book.setPublicationYear("12");
        book.setAuthor(author);

        mockMvc.perform(post(baseUrl)
                        .content(om.writeValueAsString(book))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void patch() throws Exception {
        var book = books.get(49);
        String patchNode = "{\"name\": \"Renamed Book\"}";

        mockMvc.perform(MockMvcRequestBuilders.patch(baseUrl + "/" + book.getId())
                        .content(patchNode)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        var modifiedBook = bookRepository.findById(book.getId())
                .orElseThrow(() -> new RuntimeException("Cannot find book in db after PATCH"));
        assert modifiedBook.getName().equals("Renamed Book");
        assert modifiedBook.getPublicationYear().equals(book.getPublicationYear());
    }

    @Test
    public void delete() throws Exception {
        var anotherAuthor = new Author();
        anotherAuthor.setFirstName("John");
        anotherAuthor.setLastName("Smith");
        authorRepository.save(anotherAuthor);

        var book = new Book();
        book.setName("New Book");
        book.setPublicationYear("1812");
        book.setAuthor(anotherAuthor);
        bookRepository.save(book);

        UUID id = bookRepository.findByAuthorId(anotherAuthor.getId()).get(0).getId();
        assert bookRepository.existsById(id);

        mockMvc.perform(MockMvcRequestBuilders.delete(baseUrl + "/" + id))
                .andExpect(status().isOk());

        assert !bookRepository.existsById(id);
    }
}
