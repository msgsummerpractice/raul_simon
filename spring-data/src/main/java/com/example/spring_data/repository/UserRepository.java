package com.example.spring_data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
import com.example.spring_data.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);

    User findByEmail(String email);

    Optional<User> findById(Long id);

    List<User> findTop10ByUsernameContainingIgnoreCaseOrderByUsernameAsc(String username);

    @Query("SELECT COUNT(u) FROM User u")
    Long countUsers();
}
