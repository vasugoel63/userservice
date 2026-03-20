package org.example.repositories;

import java.util.Optional;
import org.example.entities.RefreshToken;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RefreshTokenRepository extends CrudRepository<RefreshToken, Integer> {

    // else we have to write SQL query
    // Repository using JPA
    // only method name
    Optional<RefreshToken> findByToken(String token);

    // findByusernameandpassword(String username, String password)

}

// SQL query get converted