package vn.com.fortis.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.com.fortis.domain.entity.user.User;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    Optional<User> findByUsernameAndIsDeletedFalse(String username);

    boolean existsUserByUsernameAndIsDeletedFalse(String username);

    boolean existsUserByEmailAndIsDeletedFalse(String email);

    Optional<User> findByEmailAndIsDeletedFalse(String email);
}
