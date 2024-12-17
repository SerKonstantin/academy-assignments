package org.academy.spring_mvc_object_mapper.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "customers")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false, updatable = false)
    private UUID id;

    @NotBlank(message = "Введите имя пользователя")
    @Column(name = "name", nullable = false)
    private String name;

    @Email(message = "Некорретный формат электронной почты")
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Pattern(message = "Ведите номер телефона в формате +7 981 123 45 67", regexp = "^\\+(\\d+([ -]\\d+)*)$")
    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;

}