package hw3.Controller;

import hw3.DTO.Car.CarDTO;
import hw3.DTO.Car.CarDTO_All;
import hw3.DTO.Car.CarDTO_One;
import hw3.Entity.Car;
import hw3.Service.CarService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
// indicates that the data returned by each method will be written straight into the response body instead of rendering a template.
@RequestMapping("/api")
public class CarController {

    @Autowired
    private ModelMapper modelMapper;
    private final CarService carService;

    public CarController(CarService carService) {this.carService = carService;}

    @GetMapping("/car") // get all the cars, or filter by cylindrical capacity if there is a parameter specified
    List<CarDTO> allCars(@RequestParam(required = false) Integer capacity) {
        if (capacity == null) {
            return this.carService.getAllCars().stream().map(this::convertToCarDTO_All).collect(Collectors.toList());
        }
        return this.carService.getAllCarsWithCapacityGreaterThan(capacity).stream().map(this::convertToCarDTO_All).collect(Collectors.toList());
    }

    @GetMapping("/car/{id}") // get a car by its id
    CarDTO oneCar(@PathVariable Long id) {
        return this.convertToCarDTO_One(this.carService.getOneCar(id));
    }

    @PostMapping("/pilot/{id}/car") // add a new car to an existing pilot
    Car newCar(@RequestBody Car newCar, @PathVariable Long id) {
        return carService.addCar(newCar, id);
    }

    @PutMapping("/car/{id}") // update a car given the id
    Car replaceCar(@RequestBody Car newCar, @PathVariable Long id) {
        return carService.updateCar(newCar, id);
    }

    @DeleteMapping("/car/{id}") // delete a car given its id
    void deleteCar(@PathVariable Long id) {
        carService.deleteCar(id);
    }

    private CarDTO convertToCarDTO_All(Car car) {
        CarDTO_All carDTO = this.modelMapper.map(car, CarDTO_All.class);
        carDTO.setPilotID(car.getPilot().getId());
        return carDTO;
    }

    private CarDTO convertToCarDTO_One(Car car) {
        return this.modelMapper.map(car, CarDTO_One.class);
    }

}
