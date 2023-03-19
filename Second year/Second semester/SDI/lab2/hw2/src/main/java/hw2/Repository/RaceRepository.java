package hw2.Repository;

import hw2.Entity.Race;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RaceRepository extends JpaRepository<Race, Long> {} // interface which extends Spring Data JPA’s