package hw3.Repository;

import hw3.DTO.Race.RaceDTO_PilotStatistic;

import java.util.List;

public interface CustomRaceRepository {

    List<RaceDTO_PilotStatistic> findFirstStatistic();

}
