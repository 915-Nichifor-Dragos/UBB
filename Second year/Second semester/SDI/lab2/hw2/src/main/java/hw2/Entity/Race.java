package hw2.Entity;

import jakarta.persistence.*;

import java.util.Date;
import java.util.Objects;

@Entity  // JPA annotation to make this object ready for storage in a JPA-based data store
@Table(name = "Races")
public class Race {

    private @Id
    @GeneratedValue Long id; // marked with more JPA annotations to indicate it’s the primary key and automatically populated by the JPA provide
    private String name;
    private Integer maximumHorsePower;
    private Integer numberOfLaps;
    private Integer maximumAllowedEngineCapacity; // stored in cm^3
    private Date date;

    public Race() {
    }

    public Race(String name, Integer maximumHorsePower, Integer numberOfLaps, Integer maximumAllowedEngineCapacity, Date date) {
        this.name = name;
        this.maximumHorsePower = maximumHorsePower;
        this.numberOfLaps = numberOfLaps;
        this.maximumAllowedEngineCapacity = maximumAllowedEngineCapacity;
        this.date = date;
    }

    public Long getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public Integer getMaximumHorsePower() {
        return this.maximumHorsePower;
    }

    public Integer getNumberOfLaps() {
        return this.numberOfLaps;
    }

    public Integer getMaximumAllowedEngineCapacity() {
        return this.maximumAllowedEngineCapacity;
    }

    public Date getDate() {
        return this.date;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMaximumHorsePower(Integer maximumHorsePower) {
        this.maximumHorsePower = maximumHorsePower;
    }

    public void setNumberOfLaps(Integer numberOfLaps) {
        this.numberOfLaps = numberOfLaps;
    }

    public void setMaximumAllowedEngineCapacity(Integer maximumAllowedEngineCapacity) {
        this.maximumAllowedEngineCapacity = maximumAllowedEngineCapacity;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o)
            return true;
        if (!(o instanceof Pilot))
            return false;
        Race race = (Race) o;
        return Objects.equals(this.id, race.id) && Objects.equals(this.name, race.name)
                && Objects.equals(this.maximumHorsePower, race.maximumHorsePower) && Objects.equals(this.numberOfLaps, race.numberOfLaps)
                && Objects.equals(this.maximumAllowedEngineCapacity, race.maximumAllowedEngineCapacity) && Objects.equals(this.date, race.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.name, this.maximumHorsePower, this.numberOfLaps, this.maximumAllowedEngineCapacity, this.date);
    }

    @Override
    public String toString() {
        return "Race{" + "id=" + this.id + ", name='" + this.name + '\'' + ", maximumHorsePower='" + this.maximumHorsePower + '\'' +
                ", numberOfLaps='" + this.numberOfLaps + '\'' + ", maximumAllowedEngineCapacity='" + this.maximumAllowedEngineCapacity + '\'' +
                ", date='" + this.date + '\'' + '}';
    }

}
