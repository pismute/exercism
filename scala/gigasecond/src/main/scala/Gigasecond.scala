import java.util.{GregorianCalendar, Calendar}

object Gigasecond {
  val Giga = 1000000000

  def apply(c: GregorianCalendar) = new Gigasecond(c)
}

class Gigasecond(val c: GregorianCalendar) {
  import Gigasecond._

  lazy val date = {
    val cc = c.clone.asInstanceOf[GregorianCalendar]
    cc.add(Calendar.SECOND, Giga)
    cc
  }
}
