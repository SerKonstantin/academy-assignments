package org.academy.spring_mvc_pageable.initializer;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.academy.spring_mvc_pageable.model.Author;
import org.academy.spring_mvc_pageable.model.Book;
import org.academy.spring_mvc_pageable.repository.AuthorRepository;
import org.academy.spring_mvc_pageable.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

// Test data generator, move here to use with Postman

@Component
@RequiredArgsConstructor
@Getter
public class DataInitializer implements ApplicationRunner {

    @Autowired
    private final AuthorRepository authorRepository;

    @Autowired
    private final BookRepository bookRepository;

    private Author testAuthor;
    private final List<Book> testBooks = new ArrayList<>();

    @Override
    public void run(ApplicationArguments args) {
        Author author = addAuthor();
        addBooks(author, 50);
    }

    private Author addAuthor() {
        var author = new Author();
        author.setFirstName("first");
        author.setLastName("last");
        testAuthor = authorRepository.save(author);
        return testAuthor;
    }

    private void addBooks(Author author, int amount) {
        for (int i = 0; i < amount; i++) {
            var book = new Book();
            book.setAuthor(author);
            book.setName("book" + (1 + i));
            book.setPublicationYear("1984");
            var testBook = bookRepository.save(book);
            testBooks.add(testBook);
        }
    }

}
