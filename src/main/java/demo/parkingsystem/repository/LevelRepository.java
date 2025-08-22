package demo.parkingsystem.repository;

import demo.parkingsystem.model.Level;
import demo.parkingsystem.model.embedded.LevelId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LevelRepository extends JpaRepository<Level, LevelId> {
}
