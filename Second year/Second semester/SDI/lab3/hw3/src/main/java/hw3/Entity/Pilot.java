package hw3.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import hw3.Entity.RacePilot.RacesPilots;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.Objects;
import java.util.Set;

@Entity // JPA annotation to make this object ready for storage in a JPA-based data store
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Pilots")
public class Pilot {

    private @Id
    @GeneratedValue Long id; // marked with more JPA annotations to indicate it’s the primary key and automatically populated by the JPA provide
    private String firstName;
    private String lastName;
    private String nationality;
    private Date date;
    private Integer drivingExperience;

    @OneToMany(mappedBy = "pilot", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<Car> cars;

    @OneToMany(mappedBy = "pilot")
    @JsonIgnore
    Set<RacesPilots> racesPilots;

    @Override
    public boolean equals(Object o) {

        if (this == o)
            return true;
        if (!(o instanceof Pilot))
            return false;
        Pilot pilot = (Pilot) o;
        return Objects.equals(this.id, pilot.id) && Objects.equals(this.firstName, pilot.firstName)
                && Objects.equals(this.lastName, pilot.lastName) && Objects.equals(this.nationality, pilot.nationality)
                && Objects.equals(this.date, pilot.date) && Objects.equals(this.drivingExperience, pilot.drivingExperience);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.firstName, this.lastName, this.nationality, this.date, this.drivingExperience);
    }

    @Override
    public String toString() {
        return "Pilot{" + "id=" + this.id + ", firstName='" + this.firstName + '\'' + ", lastName='" + this.lastName + '\'' +
                ", nationality='" + this.nationality + '\'' + ", date='" + this.date + '\'' +
                ", drivingExperience='" + this.drivingExperience + '\'' + '}';
    }

}
