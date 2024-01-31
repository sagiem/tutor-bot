package ru.sagiem.tutorbot.entity.task;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "task")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Task {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;

    @Column(name = "title")
    String title;

    @Column(name = "text_content")
    String textContent;

    @Column(name = "actual_message_id")
    Integer messageId;
}
