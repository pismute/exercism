import scala.util.Random

class Robot {
  private var _name: String = ""

  reset()

  def name = _name

  def reset() : Unit = {
    _name =
      (Random.alphanumeric.filter(_.isUpper).take(2) ++
        Random.alphanumeric.filter(_.isDigit).take(3)).mkString
  }
}
