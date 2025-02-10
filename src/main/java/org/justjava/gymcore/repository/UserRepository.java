package org.justjava.gymcore.repository;

import org.justjava.gymcore.model.User;
import org.justjava.gymcore.model.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query("""
    select u from User u
    where u.id = :userId
    and u.role = :userRole
    """)
    User findByUserIdAndUserRole(@Param("userId") Long userId, @Param("userRole") UserRole userRole);
}
