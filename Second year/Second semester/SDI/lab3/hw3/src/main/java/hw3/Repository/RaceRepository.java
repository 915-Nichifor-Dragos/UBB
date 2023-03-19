package hw3.Repository;

import hw3.Entity.Race;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RaceRepository extends CustomRaceRepository, JpaRepository<Race, Long> {} // interface which extends Spring Data JPA’s