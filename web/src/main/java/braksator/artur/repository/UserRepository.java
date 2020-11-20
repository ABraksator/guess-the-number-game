package braksator.artur.repository;

import braksator.artur.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findByUserName(String userName);

    User findFirstByEmail(String email);
}