package com.alura.chalenge.application.users;

import com.alura.chalenge.application.shared.enums.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User,Long> {
    Optional<User> findByEmailOrUsername(String email, String username);
    Optional<User> findByEmailAndRoleEquals(String email, Role role);
    Optional<User> findByUsername(String username);
}
