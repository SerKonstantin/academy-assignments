package org.academy.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor         // Just for tests for manual id set
@Getter
@Setter
public class Book {

    private Long id;
    private String title;
    private String author;
    private int publicationYear;

}
