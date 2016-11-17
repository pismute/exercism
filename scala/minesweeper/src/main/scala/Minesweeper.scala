object Minesweeper {
  case class Point(x: Int, y: Int) {
    def move(point: Point) = copy(x = x + point.x, y = y + point.y)
  }

  object Board {
    val Neighbours: Seq[Point] =
      for {
        x <- -1 to 1
        y <- -1 to 1 if !(x == 0 && y == 0)
      } yield Point(x, y)

    def neighbours(point: Point): Seq[Point] =
      Neighbours.map(_.move(point))

    def apply(input: Seq[String]) = new Board(input)
  }

  class Board(input: Seq[String]) {
    import Board._

    require(!input.isEmpty)

    val board = input.map(_.toIndexedSeq).toIndexedSeq
    val width = board.head.size;
    val height = board.size;

    def isOnBoard(point: Point): Boolean =
       0 <= point.x && point.x < width && 0 <= point.y && point.y < height

    def isMine(point: Point): Boolean =
      board(point.y)(point.x) == '*'

    def validNeighbours(point: Point): Seq[Point] =
      neighbours(point).filter(isOnBoard)

    def countMines(point: Point): Int =
      validNeighbours(point).filter(isMine).size

    def annotatePoint(point: Point): Char =
       if(isMine(point)) '*'
       else countMines(point) match {
         case 0 => ' '
         case n => ('0' + n).toChar
       }

    def annotate: Seq[String] =
      (for {
        x <- 0 until width
        y <- 0 until height
      } yield annotatePoint(Point(x, y)))
        .grouped(width)
        .map(_.mkString)
        .toSeq
  }

  def annotate(input: Seq[String]): Seq[String] =
    if(input.isEmpty) input else Board(input).annotate
}
