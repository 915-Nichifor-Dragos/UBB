package hw3.Service;

import hw3.Entity.Car;
import hw3.Entity.Pilot;
import hw3.Entity.Race;
import hw3.Entity.RacePilot.RacesPilots;
import hw3.Exception.PilotNotFoundException;
import hw3.Repository.CarRepository;
import hw3.Repository.PilotRepository;
import hw3.Repository.RacesPilotsRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class PilotService {

    private final CarRepository carRepository;
    private final PilotRepository pilotRepository;

    private final RacesPilotsRepository racesPilotsRepository;

    public PilotService(CarRepository carRepository, PilotRepository pilotRepository, RacesPilotsRepository racesPilotsRepository)
    {
        this.carRepository = carRepository;
        this.pilotRepository = pilotRepository;
        this.racesPilotsRepository = racesPilotsRepository;
    }

    public List<Pilot> getAllPilots() {
        return pilotRepository.findAll();
    }

    public Pilot getOnePilot(Long id) {
        return pilotRepository.findById(id)
                .orElseThrow(() -> new PilotNotFoundException(id));
    }

    public List<Car> getAllCarsFromPilot(Long id){
        return this.carRepository.findByPilotId(id);
    }

    public List<Race> getAllRacesFromPilot(Long pilotId) {
        List<Race> races = new ArrayList<>();
        for (RacesPilots racesPilots : this.racesPilotsRepository.findAll()) {
            if (Objects.equals(racesPilots.getPilot().getId(), pilotId))
                races.add(racesPilots.getRace());
        }
        return races;
    }

    public Pilot addPilot(Pilot newPilot) {
        return pilotRepository.save(newPilot);
    }

    public Pilot updatePilot(Pilot newPilot, Long id) {
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

}
