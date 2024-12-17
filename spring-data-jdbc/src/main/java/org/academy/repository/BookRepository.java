package org.academy.repository;

import org.academy.model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class BookRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static final BookRowMapper ROW_MAPPER = new BookRowMapper();

    // RowMapper implementation
    private static class BookRowMapper implements org.springframework.jdbc.core.RowMapper<Book> {
        @Override
        public Book mapRow(java.sql.ResultSet rs, int rowNum) throws java.sql.SQLException {
            Book book = new Book();
            book.setId(rs.getLong("id"));
            book.setTitle(rs.getString("title"));
            book.setAuthor(rs.getString("author"));
            book.setPublicationYear(rs.getInt("publication_year"));
            return book;
        }
    }

    public List<Book> findAll() {
        String sql = "SELECT * FROM book";
        return jdbcTemplate.query(sql, ROW_MAPPER);
    }

    public Book save(Book book) {
        String sql = "INSERT INTO book (title, author, publication_year) VALUES (?, ?, ?)";

        var keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, book.getTitle());
            ps.setString(2, book.getAuthor());
            ps.setInt(3, book.getPublicationYear());
            return ps;
        }, keyHolder);

        Long generatedId = keyHolder.getKey().longValue();
        book.setId(generatedId);
        return book;
    }

    public Book update(Long id, Book book) {
        String sql = "UPDATE book SET title = ?, author = ?, publication_year = ? WHERE id = ?";
        jdbcTemplate.update(sql, book.getTitle(), book.getAuthor(), book.getPublicationYear(), id);
        book.setId(id);
        return book;
    }

    public void deleteById(Long id) {
        String sql = "DELETE FROM book WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

}
