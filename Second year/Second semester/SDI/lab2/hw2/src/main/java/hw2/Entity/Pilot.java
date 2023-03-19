package hw2.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.Date;
import java.util.Objects;
import java.util.Set;

@Entity // JPA annotation to make this object ready for storage in a JPA-based data store
@Table(name = "Pilots")
public class Pilot {

    private @Id
    @GeneratedValue Long ID; // marked with more JPA annotations to indicate it’s the primary key and automatically populated by the JPA provide
    private String firstName;
    private String lastName;
    private String nationality;
    private Date date;
    private Integer drivingExperience; // stored in years

    @OneToMany(mappedBy = "pilot", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<Car> cars;

    public Pilot() {}

    public Pilot(String firstName, String lastName, String nationality, Date date, Integer drivingExperience) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.nationality = nationality;
        this.date = date;
        this.drivingExperience = drivingExperience;
    }

    public Long getId() {
        return this.ID;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public String getNationality() {
        return this.nationality;
    }

    public Date getDate() {
        return this.date;
    }

    public Integer getDrivingExperience() {
        return this.drivingExperience;
    }

    public void setId(Long ID) {
        this.ID = ID;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setDrivingExperience(int drivingExperience) {
        this.drivingExperience = drivingExperience;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o)
            return true;
        if (!(o instanceof Pilot))
            return false;
        Pilot pilot = (Pilot) o;
        return Objects.equals(this.ID, pilot.ID) && Objects.equals(this.firstName, pilot.firstName)
                && Objects.equals(this.lastName, pilot.lastName) && Objects.equals(this.nationality, pilot.nationality)
                && Objects.equals(this.date, pilot.date) && Objects.equals(this.drivingExperience, pilot.drivingExperience);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.ID, this.firstName, this.lastName, this.nationality, this.date, this.drivingExperience);
    }

    @Override
    public String toString() {
        return "Pilot{" + "id=" + this.ID + ", firstName='" + this.firstName + '\'' + ", lastName='" + this.lastName + '\'' +
                ", nationality='" + this.nationality + '\'' + ", date='" + this.date + '\'' +
                ", drivingExperience='" + this.drivingExperience + '\'' + '}';
    }

}
