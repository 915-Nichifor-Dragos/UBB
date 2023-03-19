package hw3.Controller;

import hw3.DTO.Pilot.PilotDTO;
import hw3.DTO.Pilot.PilotDTO_All;
import hw3.DTO.Race.RaceDTO;
import hw3.DTO.Race.RaceDTO_All;
import hw3.DTO.Race.RaceDTO_PilotStatistic;
import hw3.Entity.Pilot;
import hw3.Entity.Race;
import hw3.Service.RaceService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
// indicates that the data returned by each method will be written straight into the response body instead of rendering a template.
@RequestMapping("/api")
public class RaceController {

    @Autowired
    private ModelMapper modelMapper;

    private final RaceService raceService;

    RaceController(RaceService raceService) {this.raceService = raceService;}

    @GetMapping("/race") // get all the races
    List<RaceDTO> allRaces() {
        return raceService.getAllRaces().stream().map(this::convertToRaceDTO_All).collect(Collectors.toList());
    }

    @GetMapping("/race/{id}") // get a race by its id
    RaceDTO oneRace(@PathVariable Long id) {
        return this.convertToRaceDTO_All(raceService.getOneRace(id));
    }

    @GetMapping("/race/{id}/pilot") // get all the pilots which will attend to the race
    List<PilotDTO> oneRacePilots(@PathVariable Long id) {return raceService.getAllPilotsFromRace(id).stream().map(this::convertToPilotDTO_All).collect(Collectors.toList());}

    @GetMapping("/race/pilots-statistic")
    List<RaceDTO_PilotStatistic> getRaceWithPilotStatistic() {
        return this.raceService.getRacesWithNumberOfPilotsDesc();
    }

    @PostMapping("/race") // add a new race
    Race newRace(@RequestBody Race newRace) {
        return raceService.addRace(newRace);
    }

    @PutMapping("/race/{id}") // update a race given its id
    Race replaceRace(@RequestBody Race newRace, @PathVariable Long id) {
        return raceService.updateRace(newRace, id);
    }

    @DeleteMapping("/race/{id}") // delete a race given its id
    void deleteRace(@PathVariable Long id) {
        raceService.deleteRace(id);
    }

    private RaceDTO_All convertToRaceDTO_All(Race race) {
        return this.modelMapper.map(race, RaceDTO_All.class);
    }

    private PilotDTO_All convertToPilotDTO_All(Pilot pilot) {
        return this.modelMapper.map(pilot, PilotDTO_All.class);
    }

}
