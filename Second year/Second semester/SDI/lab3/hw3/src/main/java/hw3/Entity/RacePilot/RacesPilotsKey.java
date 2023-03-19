package hw3.Entity.RacePilot;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Getter
@Setter
public class RacesPilotsKey implements Serializable {

    @Column(name = "pilot_id")
    Long pilotId;

    @Column(name = "race_id")
    Long raceId;

    @Override
    public int hashCode() {
        return Objects.hash(this.pilotId, this.raceId);
    }

    @Override
    public String toString() {
        return "PilotRaceKey{" + "pilotId='" + this.pilotId + ", raceId='" + this.raceId + '\'' + '}';
    }

}
