package interfaces;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public interface Programmabile {
	LocalDate getData();
	LocalTime getOraInizio();
	LocalDateTime getDataOraInizio();
}
