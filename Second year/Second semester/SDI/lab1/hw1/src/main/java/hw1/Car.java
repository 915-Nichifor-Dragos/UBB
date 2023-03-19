package hw1;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.util.Objects;

@Entity // JPA annotation to make this object ready for storage in a JPA-based data store
public class Car {

    private @Id @GeneratedValue Long id; // marked with more JPA annotations to indicate it’s the primary key and automatically populated by the JPA provide
    private String brand;
    private String motorization; // (diesel, benzine...)
    private String gearBox; // (automatic or manual)
    private Integer cilindricalCapacity; // (stored in cm^3)
    private Integer horsePower;


    public Car() {}

    public Car(String brand, String motorization, String gearBox, Integer cilindricalCapacity, Integer horsePower) {
        this.brand = brand;
        this.motorization = motorization;
        this.gearBox = gearBox;
        this.cilindricalCapacity = cilindricalCapacity;
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

    public Integer getCilindricalCapacity() {
        return this.cilindricalCapacity;
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

    public void setCilindricalCapacity(Integer cilindricalCapacity) {
        this.cilindricalCapacity = cilindricalCapacity;
    }

    public void setHorsePower(int horsePower) {
        this.horsePower = horsePower;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o)
            return true;
        if (!(o instanceof Car))
            return false;
        Car car = (Car) o;
        return Objects.equals(this.id, car.id) && Objects.equals(this.brand, car.brand)
                && Objects.equals(this.motorization, car.motorization) && Objects.equals(this.gearBox, car.gearBox)
                && Objects.equals(this.cilindricalCapacity, car.cilindricalCapacity) && Objects.equals(this.horsePower, car.horsePower);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.brand, this.motorization, this.gearBox, this.cilindricalCapacity, this.horsePower);
    }

    @Override
    public String toString() {
        return "Car{" + "id=" + this.id + ", brand='" + this.brand + '\'' + ", motorization='" + this.motorization + '\'' +
                ", gearBox='" + this.gearBox + '\'' + ", cilindricalCapacity='" + this.cilindricalCapacity + '\'' +
                ", horsePower='" + this.horsePower + '\'' + '}';
    }

}
