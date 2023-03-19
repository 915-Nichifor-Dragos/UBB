package hw3.DTO.Pilot;

import hw3.DTO.Car.CarDTO_All;

import lombok.*;

import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PilotDTO_One extends PilotDTO {
    Set<CarDTO_All> cars;
}
