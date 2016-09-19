import java.util.GregorianCalendar
import java.time.{LocalDate, DayOfWeek}
import java.time.temporal.TemporalAdjusters

object Meetup {
  import DayOfWeek._
  val Mon = MONDAY
  val Tue = TUESDAY
  val Wed = WEDNESDAY
  val Thu = THURSDAY
  val Fri = FRIDAY
  val Sat = SATURDAY
  val Sun = SUNDAY

  implicit class LocalDateHelpers(localDate: LocalDate) {
    def toCalendar =
      new GregorianCalendar(localDate.getYear, localDate.getMonthValue-1, localDate.getDayOfMonth)
    def isTeenth = 13 <= localDate.getDayOfMonth && localDate.getDayOfMonth < 20
  }

  def apply(m:Int, y:Int) = new Meetup(m, y)
}

class Meetup(m: Int, y: Int) {
  import Meetup._

  def dates(day: DayOfWeek) = {
    val first = LocalDate.of(y, m, 1)
    val firstOfNext = first.plusMonths(1)

    (1 until 6)
      .map(TemporalAdjusters.dayOfWeekInMonth(_:Int, day))
      .map(first.`with`)
      .filter(_.compareTo(firstOfNext) < 0)
      .toIndexedSeq
  }

  def teenth(day: DayOfWeek) = dates(day).find(_.isTeenth).get.toCalendar

  def first(day: DayOfWeek) = dates(day)(0).toCalendar

  def second(day: DayOfWeek) = dates(day)(1).toCalendar

  def third(day: DayOfWeek) = dates(day)(2).toCalendar

  def fourth(day: DayOfWeek) = dates(day)(3).toCalendar

  def last(day: DayOfWeek) = dates(day).last.toCalendar
}
