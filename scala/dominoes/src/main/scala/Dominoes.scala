import scala.annotation.tailrec

object Dominoes {
  def connect(stone: Int, domino: (Int, Int)): Option[(Int, Int)] =
    if(domino._1 == stone) Option(domino.swap)
    else if(domino._2 == stone) Option(domino)
    else None

  def theSameFirst(dominoes: List[(Int, Int)]): List[(Int, Int)] = {
    val (xs, ys) = dominoes.partition { case (l, r) => l == r }
    xs ++ ys
  }

  def chain(dominoes: List[(Int, Int)]): Option[List[(Int, Int)]] = {
    @tailrec
    def loop(dominoes: List[(Int, Int)], acc: List[(Int, Int)]): Option[List[(Int, Int)]] =
      if(dominoes.isEmpty) Option(acc)
      else {
        val stone = acc.head._1

        dominoes.span(connect(stone, _) == None) match {
          case (_, Nil) => None
          case (xs, y :: ys) =>
            loop(xs ++ ys, connect(stone, y).get +: acc)
        }
      }

    if(dominoes.isEmpty) Option(dominoes)
    else {
      val init :: xs = theSameFirst(dominoes)

      loop(xs, List(init))
        .filter(_.head._1 == init._2)
    }
  }
}
