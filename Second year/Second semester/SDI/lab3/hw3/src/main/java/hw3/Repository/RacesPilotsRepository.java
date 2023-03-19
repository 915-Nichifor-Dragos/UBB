package hw3.Repository;

import hw3.Entity.RacePilot.RacesPilots;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RacesPilotsRepository extends JpaRepository<RacesPilots, Long> {} // interface which extends Spring Data JPA’s
