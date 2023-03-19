package hw3.Service;

import hw3.Entity.Car;
import hw3.Exception.CarNotFoundException;
import hw3.Exception.PilotNotFoundException;
import hw3.Repository.CarRepository;
import hw3.Repository.PilotRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarService {

    private final CarRepository carRepository;
    private final PilotRepository pilotRepository;

    public CarService(CarRepository carRepository, PilotRepository pilotRepository)
    {
        this.carRepository = carRepository;
        this.pilotRepository = pilotRepository;
    }

    public List<Car> getAllCars() {
        return this.carRepository.findAll();
    }

    public Car getOneCar(Long id) {
        return carRepository.findById(id)
                .orElseThrow(() -> new CarNotFoundException(id));
    }

    public List<Car> getAllCarsWithCapacityGreaterThan(Integer capacity) {
        return this.carRepository.findByCylindricalCapacityGreaterThan(capacity);

    }

    public Car addCar(Car newCar, Long id) {
        return this.pilotRepository.findById(id)
                .map(pilot -> {
                    newCar.setPilot(pilot);
                    return this.carRepository.save(newCar);
                }).orElseThrow(() -> new PilotNotFoundException(id));
    }

    public Car updateCar(Car newCar, Long id) {
        return carRepository.findById(id)
                .map(car -> {
                    car.setBrand(newCar.getBrand());
                    car.setMotorization(newCar.getMotorization());
                    car.setGearBox(newCar.getGearBox());
                    car.setCylindricalCapacity(newCar.getCylindricalCapacity());
                    car.setHorsePower(newCar.getHorsePower());
                    return carRepository.save(car);
                })
                .orElseGet(() -> {
                    newCar.setId(id);
                    return carRepository.save(newCar);
                });
    }

    public void deleteCar(Long id) {
        carRepository.deleteById(id);
    }

}
