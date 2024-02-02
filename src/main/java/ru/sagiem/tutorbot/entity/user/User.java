package ru.sagiem.tutorbot.entity.user;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "users")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @Column(name = "id")
    Long chatId;

    @Column(name = "token", unique = true)
    UUID token;

    @Enumerated(EnumType.STRING)
    Role role;

    @Enumerated(EnumType.STRING)
    Action action;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_details_id")
    UserDetails userDetails;

    @ManyToMany
    @JoinTable(
            joinColumns = @JoinColumn(name = "teacher_id"),
            inverseJoinColumns = @JoinColumn(name = "student_id"),
            name = "relationships"
    )
    List<User> users;

    @PrePersist
    private void  generateUniqueToken(){
        if (token == null)
            token = UUID.randomUUID();

    }

}
