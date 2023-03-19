package hw2.Service;


import hw2.DTO.CarDTO;
import hw2.DTO.PilotCarDTO;
import hw2.DTO.PilotDTO;
import hw2.Entity.Car;
import hw2.Entity.Pilot;
import hw2.Entity.Race;
import hw2.Exception.CarNotFoundException;
import hw2.Exception.PilotNotFoundException;
import hw2.Exception.RaceNotFoundException;
import hw2.Repository.CarRepository;
import hw2.Repository.PilotRepository;
import hw2.Repository.RaceRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class Services {
    private final CarRepository carRepository;
    private final PilotRepository pilotRepository;
    private final RaceRepository raceRepository;

    public Services(CarRepository carRepository, PilotRepository pilotRepository, RaceRepository raceRepository) {
        this.carRepository = carRepository;
        this.pilotRepository = pilotRepository;
        this.raceRepository = raceRepository;
    }


    // =============================================== CAR ===============================================

    public List<CarDTO> allCars() {
        List<Car> cars = carRepository.findAll();

        List<CarDTO> carsDTO = new ArrayList<>();
        for (Car car : cars)
        {
            CarDTO newCar = new CarDTO();
            newCar.setId(car.getId());
            newCar.setBrand(car.getBrand());
            newCar.setCylindricalCapacity(car.getCylindricalCapacity());
            newCar.setHorsePower(car.getHorsePower());
            newCar.setMotorization(car.getMotorization());
            newCar.setGearBox(car.getGearBox());
            newCar.setPilotId(car.getPilot().getId());
            carsDTO.add(newCar);
        }

        return carsDTO;
    }


    public List<CarDTO> getAllCarsWithCapacityGreater(Integer id) {
        List<Car> cars = this.carRepository.findByCylindricalCapacityGreaterThan(id);

        List<CarDTO> carsDTO = new ArrayList<>();
        for (Car car : cars)
        {
            CarDTO newCar = new CarDTO();
            newCar.setId(car.getId());
            newCar.setBrand(car.getBrand());
            newCar.setCylindricalCapacity(car.getCylindricalCapacity());
            newCar.setHorsePower(car.getHorsePower());
            newCar.setMotorization(car.getMotorization());
            newCar.setGearBox(car.getGearBox());
            newCar.setPilotId(car.getPilot().getId());
            carsDTO.add(newCar);
        }

        return carsDTO;

    }


    public Car newCar(Car newCar, Long id) {
        return this.pilotRepository.findById(id)
                .map(pilot -> {
                    newCar.setPilot(pilot);
                    return this.carRepository.save(newCar);
                }).orElseThrow(() -> new PilotNotFoundException(id));
    }

    public Car oneCar(Long id) {
        return carRepository.findById(id)
                .orElseThrow(() -> new CarNotFoundException(id));
    }

    public Car replaceCar(Car newCar, Long id) {
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


    // =============================================== PILOT ===============================================


    public List<Pilot> allPilots() {
        return pilotRepository.findAll();
    }

    public Pilot newPilot(Pilot newPilot) {
        return pilotRepository.save(newPilot);
    }

    public PilotDTO onePilot(Long id) {
        Pilot pilot =  pilotRepository.findById(id)
                .orElseThrow(() -> new PilotNotFoundException(id));
        PilotDTO data = new PilotDTO();
        data.setId(pilot.getId());
        data.setDrivingExperience(pilot.getDrivingExperience());
        data.setLastName(pilot.getLastName());
        data.setFirstName(pilot.getFirstName());
        data.setNationality(pilot.getNationality());
        data.setDate(pilot.getDate());

        List<PilotCarDTO> carsDTO = new ArrayList<>();
        for (Car car : getAllCarsFromPilot(pilot.getId()))
        {
            PilotCarDTO newCarDTO = new PilotCarDTO();
            newCarDTO.setId(car.getId());
            newCarDTO.setBrand(car.getBrand());
            newCarDTO.setCylindricalCapacity(car.getCylindricalCapacity());
            newCarDTO.setMotorization(car.getMotorization());
            newCarDTO.setGearBox(car.getGearBox());
            newCarDTO.setHorsePower(car.getHorsePower());
            carsDTO.add(newCarDTO);
        }

        data.setCars(carsDTO);
        return data;
    }

    public List<Car> getAllCarsFromPilot(Long id){
        return this.carRepository.findByPilotID(id);
    }

    public Pilot replacePilot(Pilot newPilot, Long id) {
        return pilotRepository.findById(id)
                .map(pilot -> {
                    pilot.setFirstName(newPilot.getFirstName());
                    pilot.setLastName(newPilot.getLastName());
                    pilot.setNationality(newPilot.getNationality());
                    pilot.setDate(newPilot.getDate());
                    pilot.setDrivingExperience(newPilot.getDrivingExperience());
                    return pilotRepository.save(pilot);
                })
                .orElseGet(() -> {
                    newPilot.setId(id);
                    return pilotRepository.save(newPilot);
                });
    }

    public void deletePilot(Long id) {
        pilotRepository.deleteById(id);
    }


    // =============================================== CAR ===============================================


    public List<Race> allRaces() {
        return raceRepository.findAll();
    }

    public Race newRace(Race newRace) {
        return raceRepository.save(newRace);
    }

    public Race oneRace(Long id) {
        return raceRepository.findById(id)
                .orElseThrow(() -> new RaceNotFoundException(id));
    }

    public Race replaceRace(Race newRace, Long id) {
        return raceRepository.findById(id)
                .map(race -> {
                    race.setName(newRace.getName());
                    race.setDate(newRace.getDate());
                    race.setNumberOfLaps(newRace.getNumberOfLaps());
                    race.setMaximumHorsePower(newRace.getMaximumHorsePower());
                    race.setMaximumAllowedEngineCapacity(newRace.getMaximumAllowedEngineCapacity());
                    return raceRepository.save(race);
                })
                .orElseGet(() -> {
                    newRace.setId(id);
                    return raceRepository.save(newRace);
                });
    }

    public void deleteRace(Long id) {
        raceRepository.deleteById(id);
    }
}
