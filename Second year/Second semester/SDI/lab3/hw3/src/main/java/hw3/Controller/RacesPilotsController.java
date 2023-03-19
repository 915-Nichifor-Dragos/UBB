package hw3.Controller;

import hw3.Entity.RacePilot.RacesPilots;
import hw3.Service.RacesPilotsService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
// indicates that the data returned by each method will be written straight into the response body instead of rendering a template.
@RequestMapping("/api")
public class RacesPilotsController {

    private final RacesPilotsService racesPilotsService;

    private RacesPilotsController (RacesPilotsService racesPilotsService) {this.racesPilotsService = racesPilotsService;}

    @GetMapping("/racespilots") // get all race pilot pairs
    List<RacesPilots> allRacesPilots() {
        return this.racesPilotsService.getAllRacesPilots();
    }

    @PostMapping("/races/{idRace}/pilots/{idPilot}") // add a new race pilot pair
    RacesPilots newRacesPilots(@RequestBody RacesPilots newRacesPilots, @PathVariable Long idRace, @PathVariable Long idPilot) {
        return racesPilotsService.addRacesPilots(newRacesPilots, idRace, idPilot);
    }

}
