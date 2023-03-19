package hw1;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CarRepository extends JpaRepository<Car, Long> {} // interface which extends Spring Data JPA’s