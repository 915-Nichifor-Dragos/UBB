package hw2.DTO;

import hw2.Entity.Car;

import java.util.Date;
import java.util.List;

public class PilotDTO {

    private Long id;
    private String firstName;
    private String lastName;
    private String nationality;
    private Date date;
    private Integer drivingExperience;
    List<PilotCarDTO> cars;

    public PilotDTO() {};

    public PilotDTO(Long id, String firstName, String lastName, String nationality, Date date, Integer drivingExperience, List<PilotCarDTO> cars)
    {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.nationality = nationality;
        this.date = date;
        this.drivingExperience = drivingExperience;
        this.cars = cars;
    }

    public Long getId() {
        return this.id;
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
        this.id = ID;
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

    public List<PilotCarDTO> getCars() { return this.cars;}

    public void setCars(List<PilotCarDTO> cars) {
        this.cars = cars;
    }

}
