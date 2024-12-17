package org.academy.service;

import org.academy.model.Book;
import org.academy.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    public Iterable<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Book addBook(Book book) {
        return bookRepository.save(book);
    }

    public Book updateBook(Long id, Book book) {
        return bookRepository.update(id, book);
    }

    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }
}
