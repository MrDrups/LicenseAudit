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

    @Query("SELECT u FROM U01_USER u WHERE u.U01_LOGIN = :login")
    Optional<User> findByLogin(@Param("login") String login);

    @Query("SELECT u FROM U01_USER u WHERE concat(u.U01_ID, ' ', u.U01_EMAIL, ' ', u.U01_LOGIN, ' ',u.U01_NAME, ' ', u.U01_PASS, ' ', u.role.R01_NAME) LIKE %?1%")
    List<User> search(String keyword);
}
