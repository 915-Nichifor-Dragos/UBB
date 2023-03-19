package hw3.Repository;

import hw3.DTO.Race.RaceDTO_PilotStatistic;
import hw3.Entity.Pilot;
import hw3.Entity.Race;
import hw3.Entity.RacePilot.RacesPilots;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.*;

import java.util.List;

public class CustomRaceRepositoryImpl implements CustomRaceRepository {
    @PersistenceContext
    private EntityManager entityManager;
    @Override
    public List<RaceDTO_PilotStatistic> findFirstStatistic() {
        CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<RaceDTO_PilotStatistic> query = cb.createQuery(RaceDTO_PilotStatistic.class);
        Root<Race> raceRoot = query.from(Race.class);
        Join<Race, RacesPilots> raceRacesPilotsJoin = raceRoot.join("racesPilots", JoinType.INNER);
        Join<RacesPilots, Pilot> racesPilotsPilotJoin = raceRacesPilotsJoin.join("pilot", JoinType.INNER);

        query.select(cb.construct(
                        RaceDTO_PilotStatistic.class,
                        raceRoot.get("name"),
                        cb.count(racesPilotsPilotJoin.get("firstName"))
                ))
                .groupBy(raceRoot.get("name"))
                .orderBy(cb.desc(cb.count(racesPilotsPilotJoin.get("firstName"))));

        return this.entityManager.createQuery(query).getResultList();
    }
}
