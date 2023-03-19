package hw2.DTO;

import hw2.Entity.Pilot;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

public class PilotCarDTO {

    private Long id; // marked with more JPA annotations to indicate it’s the primary key and automatically populated by the JPA provide
    private String brand;
    private String motorization; // (diesel, benzine...)
    private String gearBox; // (automatic or manual)
    private Integer cylindricalCapacity; // (stored in cm^3)
    private Integer horsePower;


    public PilotCarDTO() {}

    public PilotCarDTO(String brand, String motorization, String gearBox, Integer cylindricalCapacity, Integer horsePower) {
        this.brand = brand;
        this.motorization = motorization;
        this.gearBox = gearBox;
        this.cylindricalCapacity = cylindricalCapacity;
        this.horsePower = horsePower;
    }

    public Long getId() {
        return this.id;
    }

    public String getBrand() {
        return this.brand;
    }

    public String getMotorization() {
        return this.motorization;
    }

    public String getGearBox() {
        return this.gearBox;
    }

    public Integer getCylindricalCapacity() {
        return this.cylindricalCapacity;
    }

    public Integer getHorsePower() {
        return this.horsePower;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void setMotorization(String motorization) {
        this.motorization = motorization;
    }

    public void setGearBox(String gearBox) {
        this.gearBox = gearBox;
    }

    public void setCylindricalCapacity(Integer cylindricalCapacity) {
        this.cylindricalCapacity = cylindricalCapacity;
    }

    public void setHorsePower(int horsePower) {
        this.horsePower = horsePower;
    }

}
