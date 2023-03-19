package hw1;
import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController // indicates that the data returned by each method will be written straight into the response body instead of rendering a template.
class CarController {

    private final CarRepository repository; // An CarRepository is injected by constructor into the controller

    CarController(CarRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/car") // HTTP GET call
    List<Car> all() {
        return repository.findAll();
    }

    @PostMapping("/car") // HTTP POST call
    Car newCar(@RequestBody Car newCar) {
        return repository.save(newCar);
    }

    @GetMapping("/car/{id}") // HTTP GET call
    Car one(@PathVariable Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new CarNotFoundException(id));
    }

    @PutMapping("/car/{id}") // HTTP PUT call
    Car replaceCar(@RequestBody Car newEmployee, @PathVariable Long id) {
        return repository.findById(id)
                .map(employee -> {
                    employee.setBrand(newEmployee.getBrand());
                    employee.setMotorization(newEmployee.getMotorization());
                    employee.setGearBox(newEmployee.getGearBox());
                    employee.setCilindricalCapacity(newEmployee.getCilindricalCapacity());
                    employee.setHorsePower(newEmployee.getHorsePower());
                    return repository.save(employee);
                })
                .orElseGet(() -> {
                    newEmployee.setId(id);
                    return repository.save(newEmployee);
                });
    }

    @DeleteMapping("/car/{id}") // HTTP DELETE call
    void deleteCar(@PathVariable Long id) {
        repository.deleteById(id);
    }
}