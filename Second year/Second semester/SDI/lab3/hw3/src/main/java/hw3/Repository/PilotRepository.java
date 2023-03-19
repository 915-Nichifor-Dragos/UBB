package hw3.Repository;


import hw3.Entity.Pilot;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PilotRepository  extends JpaRepository<Pilot, Long> {} // interface which extends Spring Data JPA’s
