import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class Gigasecond {
    private static Duration GIGA_SECONDS = Duration.ofSeconds(1000000000);
    private final LocalDateTime birthDateTime;

    public Gigasecond(LocalDate birthDate) {
        this(birthDate.atStartOfDay());
    }

    public Gigasecond(LocalDateTime birthDateTime) {
        this.birthDateTime = birthDateTime;
    }

    public LocalDateTime getDate() {
        return birthDateTime.plus(GIGA_SECONDS);
    }
}
