package hw2.Repository;


import hw2.Entity.Pilot;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PilotRepository  extends JpaRepository<Pilot, Long> {} // interface which extends Spring Data JPA’s
