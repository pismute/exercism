import org.joda.time.DateTime;

import java.util.function.Predicate;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Meetup {
    private final DateTime firstOfMonth;
    private final DateTime firstOfNextMonth;

    public Meetup(int month, int year) {
        this.firstOfMonth = new DateTime(year, month, 1, 0, 0);
        this.firstOfNextMonth = firstOfMonth.plusMonths(1);
    }

    private final Predicate<DateTime> isTeenth = (d)-> 13 <= d.getDayOfMonth() && d.getDayOfMonth() < 20;

    public final Stream<DateTime> dates(int dayOfWeek) {
        return IntStream.rangeClosed(0, 5)
                .mapToObj(week-> firstOfMonth.plusWeeks(week))
                .map(dateTime -> dateTime.withDayOfWeek(dayOfWeek))
                .filter(dateTime -> dateTime.compareTo(firstOfMonth) >= 0)
                .filter(dateTime-> dateTime.compareTo(firstOfNextMonth) < 0);
    }

    public DateTime day(int dayOfWeek, MeetupSchedule meetupSchedule) {
        switch (meetupSchedule) {
            case TEENTH:
                return dates(dayOfWeek).filter(isTeenth).findFirst().get();
            case FIRST:
                return dates(dayOfWeek).findFirst().get();
            case SECOND:
                return dates(dayOfWeek).skip(1).findFirst().get();
            case THIRD:
                return dates(dayOfWeek).skip(2).findFirst().get();
            case FOURTH:
                return dates(dayOfWeek).skip(3).findFirst().get();
            case LAST:
                return dates(dayOfWeek).reduce((left, right)-> right).get();
            default:
                return null;
        }
    }
}
