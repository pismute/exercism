import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class BeerSong {

    public static String bottle(int i) {
        return i == 1? "bottle": "bottles";
    }

    public static String one(int i) {
        return i > 1? "one": "it";
    }

    public static String number(int i) {
        return i > 0? String.valueOf(i) : "no more";
    }

    public static String Number(int i) {
        return i > 0? String.valueOf(i) : "No more";
    }

    public static String verse(int i) {
        BearSongBuilder builder = new BearSongBuilder(i);

        builder.Number().bottle().ofBeerOnTheWall().comma()
                    .number().bottle().ofBeer().period().ln();

        if(i > 0) {
            builder.Take().one().downAndPassItAround().comma()
                    .number(-1).bottle(-1).ofBeerOnTheWall().period().ln()
                    .ln();
        } else {
            builder.advertising();
        }

        return builder.build();
    }

    public static String sing(int start, int until) {
        return IntStream.iterate(start, i-> i - 1)
                .limit(start - until + 1)
                .mapToObj(BeerSong::verse)
                .collect(Collectors.joining());
    }

    public static String singSong() {
        return sing(99, 0);
    }

    public static class BearSongBuilder {
        private final StringBuilder builder;
        private final int phase;

        public BearSongBuilder(int phase) {
            this.phase = phase;
            builder = new StringBuilder();
        }

        public BearSongBuilder bottle() {
            return bottle(0);
        }

        public BearSongBuilder bottle(int delta) {
            int i = phase + delta;
            builder.append(i == 1? "bottle": "bottles");
            return this;
        }

        public BearSongBuilder one() {
            builder.append(phase > 1? "one": "it");
            return this;
        }

        public BearSongBuilder number() {
            return number(0);
        }

        public BearSongBuilder number(int delta) {
            int i = phase + delta;
            builder.append(i > 0? i : "no more").append(" ");
            return this;
        }

        public BearSongBuilder Number() {
            return Number(0);
        }

        public BearSongBuilder Number(int delta) {
            int i = phase + delta;
            builder.append(i > 0? i : "No more").append(" ");
            return this;
        }

        public BearSongBuilder ofBeerOnTheWall() {
            builder.append(" of beer on the wall");
            return this;
        }

        public BearSongBuilder comma() {
            builder.append(", ");
            return this;
        }

        public BearSongBuilder ofBeer() {
            builder.append(" of beer");
            return this;
        }
        public BearSongBuilder Take() {
            builder.append("Take ");
            return this;
        }
        public BearSongBuilder downAndPassItAround() {
            builder.append(" down and pass it around");
            return this;
        }

        public BearSongBuilder period() {
            builder.append(".");
            return this;
        }

        public BearSongBuilder ln() {
            builder.append("\n");
            return this;
        }

        public BearSongBuilder advertising() {
            builder.append("Go to the store and buy some more, 99 bottles of beer on the wall.\n\n");
            return this;
        }

        public String build() {
            return builder.toString();
        }
    }
}