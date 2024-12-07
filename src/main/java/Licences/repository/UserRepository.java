package Licences.repository;

import Licences.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query("SELECT u FROM U01_USER u WHERE u.LOGIN = :login")
    Optional<User> findByLogin(@Param("login") String login);

    @Query("SELECT u FROM U01_USER u WHERE concat(u.ID, ' ', u.EMAIL, ' ', u.LOGIN, ' ',u.NAME, ' ', u.PASS, ' ', u.role.NAME) LIKE %?1%")
    List<User> search(String keyword);
}