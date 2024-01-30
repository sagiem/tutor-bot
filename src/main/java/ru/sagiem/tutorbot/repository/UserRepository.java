package ru.sagiem.tutorbot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.sagiem.tutorbot.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
