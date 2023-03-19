package hw3.Service;

import hw3.Entity.Pilot;
import hw3.Entity.Race;
import hw3.Entity.RacePilot.RacesPilots;
import hw3.Entity.RacePilot.RacesPilotsKey;
import hw3.Exception.PilotNotFoundException;
import hw3.Exception.RacesPilotsNotFoundException;
import hw3.Repository.PilotRepository;
import hw3.Repository.RaceRepository;
import hw3.Repository.RacesPilotsRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RacesPilotsService {

    private final PilotRepository pilotRepository;
    private final RaceRepository raceRepository;
    private final RacesPilotsRepository racesPilotsRepository;


    public RacesPilotsService(PilotRepository pilotRepository, RaceRepository raceRepository, RacesPilotsRepository racesPilotsRepository) {
        this.pilotRepository = pilotRepository;
        this.raceRepository = raceRepository;
        this.racesPilotsRepository = racesPilotsRepository;
    }

    public List<RacesPilots> getAllRacesPilots() {
        return racesPilotsRepository.findAll();
    }

    public RacesPilots addRacesPilots(RacesPilots newRacesPilots, Long idRace, Long idPilot) {
        RacesPilotsKey key = new RacesPilotsKey();
        Pilot pilot = pilotRepository.findById(idPilot).orElseThrow(() -> new PilotNotFoundException(idPilot));
        Race race = raceRepository.findById(idRace).orElseThrow(() -> new PilotNotFoundException(idRace));
        key.setRaceId(race.getId());
        key.setPilotId(pilot.getId());
        newRacesPilots.setId(key);
        newRacesPilots.setRace(race);
        newRacesPilots.setPilot(pilot);
        return racesPilotsRepository.save(newRacesPilots);
    }

}
