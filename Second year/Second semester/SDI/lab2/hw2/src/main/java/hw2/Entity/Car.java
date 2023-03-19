package hw2.Entity;

import jakarta.persistence.*;

import java.util.Objects;

@Entity // JPA annotation to make this object ready for storage in a JPA-based data store
@Table(name = "Cars")
public class Car {

    private @Id @GeneratedValue Long id; // marked with more JPA annotations to indicate it’s the primary key and automatically populated by the JPA provide
    private String brand;
    private String motorization; // (diesel, benzine...)
    private String gearBox; // (automatic or manual)
    private Integer cylindricalCapacity; // (stored in cm^3)
    private Integer horsePower;

    @ManyToOne
    @JoinColumn(name = "pilot_id", nullable = false)
    private Pilot pilot;

    public Car() {}

    public Car(String brand, String motorization, String gearBox, Integer cylindricalCapacity, Integer horsePower) {
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

    public Pilot getPilot() { return this.pilot; }

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

    public void setPilot(Pilot pilot) { this.pilot = pilot; }

    @Override
    public boolean equals(Object o) {

        if (this == o)
            return true;
        if (!(o instanceof Car))
            return false;
        Car car = (Car) o;
        return Objects.equals(this.id, car.id) && Objects.equals(this.brand, car.brand)
                && Objects.equals(this.motorization, car.motorization) && Objects.equals(this.gearBox, car.gearBox)
                && Objects.equals(this.cylindricalCapacity, car.cylindricalCapacity) && Objects.equals(this.horsePower, car.horsePower);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.brand, this.motorization, this.gearBox, this.cylindricalCapacity, this.horsePower);
    }

    @Override
    public String toString() {
        return "Car{" + "id=" + this.id + ", brand='" + this.brand + '\'' + ", motorization='" + this.motorization + '\'' +
                ", gearBox='" + this.gearBox + '\'' + ", cylindricalCapacity='" + this.cylindricalCapacity + '\'' +
                ", horsePower='" + this.horsePower + '\'' + '}';
    }

}
