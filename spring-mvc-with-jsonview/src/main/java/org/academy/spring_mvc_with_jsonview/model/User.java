package org.academy.spring_mvc_with_jsonview.model;

import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.academy.spring_mvc_with_jsonview.config.JsonViewRoles;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "user_")
@EntityListeners(AuditingEntityListener.class)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false, updatable = false)
    @JsonView(JsonViewRoles.UserFull.class)
    private UUID id;

    @Size(min = 2, message = "Имя должно содержать не менее двух символов")
    @Column(name = "username", nullable = false)
    @JsonView(JsonViewRoles.UserConcise.class)
    private String username;

    @Email(message = "Некорректный формат электронной почты")
    @Column(name = "email", nullable = false, unique = true)
    @JsonView(JsonViewRoles.UserConcise.class)
    private String email;

    @CreatedDate
    @Column(name = "created_at", updatable = false)
    @JsonView(JsonViewRoles.UserFull.class)
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "user", cascade = jakarta.persistence.CascadeType.ALL, orphanRemoval = true)
    @JsonView(JsonViewRoles.UserFull.class)
    private List<Order> orders = new ArrayList<>();

}