package hw3.Repository;

import hw3.Entity.Car;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CarRepository extends JpaRepository<Car, Long> { // interface which extends Spring Data JPA’s
    List<Car> findByCylindricalCapacityGreaterThan(Integer cylindricalCapacity);

    List<Car> findByPilotId(Long id);

}