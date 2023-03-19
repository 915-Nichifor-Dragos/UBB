package hw3.DTO.Car;

import hw3.Entity.Pilot;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CarDTO_One extends CarDTO {

    Pilot pilot;

}
