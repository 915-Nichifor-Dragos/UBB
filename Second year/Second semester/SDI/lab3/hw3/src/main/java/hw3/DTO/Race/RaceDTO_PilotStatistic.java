package hw3.DTO.Race;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RaceDTO_PilotStatistic {
    private String name;
    private Long numberOfPilots;
}
