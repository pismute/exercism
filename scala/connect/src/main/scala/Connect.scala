import scala.annotation.tailrec

object Connect {
  type Board = List[String]

  case class Point(x: Int, y: Int) {
    lazy val neighbors: Stream[Point] =
      for {
        dx <- (-1 to 1).toStream
        dy <- (-1 to 1)
        if(dx != 0 || dy != 0)
      } yield Point(x + dx, y - dy)
  }

  def apply(board: Board) = new Connect(board)
}

import Connect._

sealed trait Color {
  def stone: Char
  def isStart(board: Board): Point => Boolean
  def isEnd(board: Board): Point => Boolean
  def startPointsIn(board: Board): Seq[Point]
}

object Color {
  case object White extends Color {
    val stone = 'O'

    def isStart(board: Board): Point => Boolean =
      (p: Point) => p.y == 0

    def isEnd(board: Board): Point => Boolean  = {
      val y = board.size - 1

      for {
        (s, x) <- board.last.zipWithIndex.toSet
        if(s == stone)
      } yield Point(x, y)
    }

    def startPointsIn(board: Board): Seq[Point] =
      for {
        (s, x) <- board.head.zipWithIndex.toStream
        if(s == stone)
      } yield Point(x, 0)
  }

  case object Black extends Color {
    val stone = 'X'

    def isStart(board: Board): Point => Boolean =
      (p: Point) => p.x == 0

    def isEnd(board: Board): Point => Boolean = {
      val x = board.head.size - 1

      for {
        (s, y) <- board.map(_.last).zipWithIndex.toSet
        if(s == stone)
      } yield Point(x, y)
    }

    def startPointsIn(board: Board): Seq[Point] =
      for {
        (s, y) <- board.map(_.head).zipWithIndex.toStream
        if(s == stone)
      } yield Point(0, y)
  }
}

class Connect(board: Board) {
  import Color._

  val XSize = board.head.size
  val YSize = board.size

  def isValid(p: Point): Boolean =
    0 <= p.x && p.x < XSize && 0 <= p.y && p.y < YSize

  def isStoneOf(stone: Char)(p: Point): Boolean =
    stone == board(p.y)(p.x)

  def find(color: Color): Option[Color] = {
    val isEnd = color.isEnd(board)

    @tailrec
    def findConnect(points: Seq[Point], path: Set[Point]): Option[Point] =
      if(points.isEmpty) None
      else {
        val Seq(p, ps @ _*) = points

        if(isEnd(p)) Option(p)
        else {
          val connects =
            p.neighbors
              .filter(isValid)
              .filterNot(path)
              .filter(isStoneOf(color.stone))

          findConnect(connects ++ ps, path + p)
        }
      }

    findConnect(color.startPointsIn(board), Set.empty[Point])
      .map(_ => color)
  }

  lazy val result = find(White).orElse(find(Black))
}
