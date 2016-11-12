object Clock {
  def normalizeMinute(minute: Int) = {
    val (h, m) = (minute/60, minute%60)

    if(minute >= 0) (h, m)
    else (h - 1, m+60)
  }

  def normalizeHour(hour: Int) =
    if(hour >= 0) hour%24 else hour%24 + 24

  def apply(hour: Int, minute: Int): Clock = {
    val (h, m) = normalizeMinute(minute)

    new Clock(normalizeHour(hour + h), m)
  }

  def apply(minute:Int): Clock = apply(0, minute)
}

class Clock(val hour: Int, val minute: Int) {
  import Clock._

  def +(that: Clock): Clock = Clock(this.hour + that.hour, this.minute + that.minute)

  def -(that: Clock): Clock = Clock(this.hour - that.hour, this.minute - that.minute)

  override def toString(): String = f"$hour%02d:$minute%02d"

  override def equals(that: Any): Boolean =
    that.isInstanceOf[Clock] && {
      val t = that.asInstanceOf[Clock]
      this.hour == t.hour && this.minute == t.minute
    }
}
