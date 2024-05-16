package com.alura.chalenge.application.users;

import com.alura.chalenge.application.shared.enums.Role;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User,Long> {
    Optional<User> findUserByEmailOrUsername(String email, String username);
    Optional<User> findByEmailAndRoleEquals(String email, Role role);
    Optional<User> findByUsername(String username);

    @Query("SELECT u FROM User AS u WHERE u.email = :username OR u.username = :username")
    Optional<User> searchUserByEmailOrUsername(String username);
}
