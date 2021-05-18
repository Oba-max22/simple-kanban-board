package com.decagon.obamax.kanban.repositories;

import com.decagon.obamax.kanban.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    User findByEmailAndPassword(String email, String password);

    User findByUserId(Long id);
}
