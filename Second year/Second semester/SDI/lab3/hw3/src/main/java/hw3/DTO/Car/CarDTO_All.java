package hw3.DTO.Car;

import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CarDTO_All extends CarDTO {

    private Long pilotID;

}
