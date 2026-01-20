package com.socio.socio.repository;

import com.socio.socio.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    @Query("""
            SELECT u FROM User u
            WHERE DAY(u.birthDate) = DAY(:today)
            AND MONTH(u.birthDate) = MONTH(:today)
        """)
    List<User> findUsersWithBirthdayToday(@Param("today") LocalDate today);
}
