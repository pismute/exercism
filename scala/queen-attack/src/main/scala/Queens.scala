case class Position(x: Int, y: Int) {
  def isOn(x: Int, y: Int) = this.x == x && this.y == y
  def diff = x - y
  def sum = x + y
}

object Queens {
  val BOARD_SIZE = 8

  def getStone(w: Option[Position], b: Option[Position], x: Int, y: Int): Char =
    w.filter(_.isOn(x, y)).map(_=>'W')
      .orElse(b.filter(_.isOn(x, y)).map(_=>'B'))
      .getOrElse('_')

  def boardString(w: Option[Position], b: Option[Position]): String =
    (for {
      x <- 0 until BOARD_SIZE
      y <- 0 until BOARD_SIZE
    } yield getStone(w, b, x, y))
      .grouped(BOARD_SIZE)
      .map(_.mkString(" ") + "\n")
        .mkString

  def canAttack(w: Position, b: Position): Boolean =
    w.x == b.x || w.y == b.y || w.diff == b.diff || w.sum == b.sum

  def apply() = Queens
}
