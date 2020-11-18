package braksator.artur.repository;

import braksator.artur.entity.Gameplay;
import braksator.artur.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface GameplayRepository extends JpaRepository<Gameplay, Integer> {

}
