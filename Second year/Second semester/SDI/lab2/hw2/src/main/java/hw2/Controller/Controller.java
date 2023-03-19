package hw2.Controller;

import hw2.DTO.CarDTO;
import hw2.DTO.PilotDTO;
import hw2.Entity.Car;
import hw2.Entity.Pilot;
import hw2.Entity.Race;
import hw2.Service.Services;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController // indicates that the data returned by each method will be written straight into the response body instead of rendering a template.
@RequestMapping("/api")
public class Controller {

    private final Services services;

    Controller(Services services) {
        this.services = services;
    }

    // =============================================== CAR ===============================================

    @GetMapping("/car") // get all the cars, or filter by cylindrical capacity if there is a parameter specified
    List<CarDTO> allCars(@RequestParam(required = false) Integer capacity) {
        if (capacity == null) {
            return this.services.allCars();
        }
        return this.services.getAllCarsWithCapacityGreater(capacity);
    }

    @PostMapping("/pilot/{id}/car") // add a new car to an existing pilot
    Car newCar(@RequestBody Car newCar, @PathVariable Long id) {
        return services.newCar(newCar, id);
    }

    @GetMapping("/car/{id}") // get a car by its id
    Car oneCar(@PathVariable Long id) {
        return services.oneCar(id);
    }

    @PutMapping("/car/{id}") // update a car given the id
    Car replaceCar(@RequestBody Car newCar, @PathVariable Long id) {
        return services.replaceCar(newCar, id);
    }

    @DeleteMapping("/car/{id}") // delete a car by its id
    void deleteCar(@PathVariable Long id) {
        services.deleteCar(id);
    }


    // =============================================== PILOT ===============================================


    @GetMapping("/pilot") // get all the pilots
    List<Pilot> allPilots() {
        return services.allPilots();
    }

    @PostMapping("/pilot") // add a new pilot
    Pilot newPilot(@RequestBody Pilot newPilot) {
        return services.newPilot(newPilot);
    }

    @GetMapping("/pilot/{id}") // get a pilot by its id
    PilotDTO onePilot(@PathVariable Long id) {
        return services.onePilot(id);
    }

    @GetMapping("/pilot/{id}/car") // get all the cars from a pilot given the id
    List<Car>  allPilotCars(@PathVariable Long id) {
        return services.getAllCarsFromPilot(id);
    }

    @PutMapping("/pilot/{id}") // HTTP PUT call for pilot
    Pilot replacePilot(@RequestBody Pilot newPilot, @PathVariable Long id) {
        return services.replacePilot(newPilot, id);
    }

    @DeleteMapping("/pilot/{id}") // HTTP DELETE call for pilot
    void deletePilot(@PathVariable Long id) {
        services.deletePilot(id);
    }


    // =============================================== CAR ===============================================


    @GetMapping("/race") // get all the races
    List<Race> allRaces() {
        return services.allRaces();
    }

    @PostMapping("/race") // add a new race
    Race newRace(@RequestBody Race newRace) {
        return services.newRace(newRace);
    }

    @GetMapping("/race/{id}") // HTTP GET call for race
    Race oneRace(@PathVariable Long id) {
        return services.oneRace(id);
    }

    @PutMapping("/race/{id}") // HTTP PUT call for race
    Race replaceRace(@RequestBody Race newRace, @PathVariable Long id) {
        return services.replaceRace(newRace, id);
    }

    @DeleteMapping("/race/{id}") // HTTP DELETE call for race
    void deleteRace(@PathVariable Long id) {
        services.deleteRace(id);
    }
}