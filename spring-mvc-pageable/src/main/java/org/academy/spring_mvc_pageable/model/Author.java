package org.academy.spring_mvc_pageable.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "author")
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private UUID id;

    @Size(min = 2, message = "Имя автора должно быть длиннее двух букв")
    @Pattern(regexp = "^[\\p{L}'-]+$", message = "Пожалуйста не используйте символы в имени автора")
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Size(min = 2, message = "Фамилия автора должно быть длиннее двух букв")
    @Pattern(regexp = "^[\\p{L}'-]+$", message = "Пожалуйста не используйте символы в фамилии автора")
    @Column(name = "last_name", nullable = false)
    private String lastName;

}