public class SpaceAge {
    public static final double EARTH_SECONDS = 31557600;
    public static final double MERCURY_SECONDS = EARTH_SECONDS *  0.2408467;
    public static final double VENUS_SECONDS = EARTH_SECONDS *  0.61519726;
    public static final double MARS_SECONDS = EARTH_SECONDS *  1.8808158;
    public static final double JUPITER_SECONDS = EARTH_SECONDS *  11.862615;
    public static final double SATURN_SECONDS = EARTH_SECONDS *  29.447498;
    public static final double URANUS_SECONDS = EARTH_SECONDS *  84.016846;
    public static final double NEPTUNE_SECONDS = EARTH_SECONDS *  164.79132;

    private final long seconds;
    private final double earth;
    private final double mercury;
    private final double venus;
    private final double mars;
    private final double jupiter;
    private final double saturn;
    private final double uranus;
    private final double neptune;

    public SpaceAge(long seconds) {
        this.seconds = seconds;
        this.earth = seconds/ EARTH_SECONDS;
        this.mercury = seconds/ MERCURY_SECONDS;
        this.venus = seconds/ VENUS_SECONDS;
        this.mars = seconds/ MARS_SECONDS;
        this.jupiter = seconds/ JUPITER_SECONDS;
        this.saturn = seconds/ SATURN_SECONDS;
        this.uranus = seconds/ URANUS_SECONDS;
        this.neptune = seconds/ NEPTUNE_SECONDS;
    }

    public long getSeconds() {
        return seconds;
    }

    public double onEarth() {
        return earth;
    }

    public double onMercury() {
        return mercury;
    }

    public double onVenus() {
        return venus;
    }

    public double onMars() {
        return mars;
    }

    public double onJupiter() {
        return jupiter;
    }

    public double onSaturn() {
        return saturn;
    }

    public double onUranus() {
        return uranus;
    }

    public double onNeptune() {
        return neptune;
    }
}
