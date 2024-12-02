package Licences.repository;

import Licences.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT u FROM U01_USER u WHERE u.U01_LOGIN = :login")
    Optional<User> findByLogin(@Param("login") String login);

}
