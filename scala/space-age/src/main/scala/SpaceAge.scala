object SpaceAge {
  val AnYear = 31557600.0

  def apply(seconds: Long) = new SpaceAge(seconds)

  implicit class DoubleHelpers(d: Double) {
    def roundSecond = (d * 100).round / 100.0
  }

}


class SpaceAge(val seconds: Long) {
  import SpaceAge._

  def onBy(d: Double) = seconds / d / AnYear

  lazy val onEarth = onBy(1.0).roundSecond
  lazy val onMercury = onBy(0.2408467).roundSecond
  lazy val onVenus = onBy(0.61519726).roundSecond
  lazy val onMars = onBy(1.8808158).roundSecond
  lazy val onJupiter = onBy(11.862615).roundSecond
  lazy val onSaturn = onBy(29.447498).roundSecond
  lazy val onUranus = onBy(84.016846).roundSecond
  lazy val onNeptune = onBy(164.79132).roundSecond
}
