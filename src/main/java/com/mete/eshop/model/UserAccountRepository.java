package com.mete.eshop.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import java.util.Optional;

public interface UserAccountRepository extends JpaRepository<UserAccount, Long> {
    // This method name follows Spring Data JPA naming convention
    Optional<UserAccount> findByEmail(String email);
}