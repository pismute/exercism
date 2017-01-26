import scala.annotation.tailrec

object Dna {
  val Transcription =
    Map('G' -> 'C', 'C' -> 'G', 'T' -> 'A', 'A' -> 'U')

  def toRna(xs: String) = traverse(Transcription.get, xs)

  def traverse(f: Char => Option[Char], xs: String): Option[String] = {
    @tailrec
    def _loop(xs: Seq[Char], acc: Seq[Option[Char]]): Option[Seq[Char]] =
      if(xs.isEmpty) Option(acc.reverse.map(_.get))
      else
        f(xs.head) match {
          case None => None
          case x => _loop(xs.tail, x +: acc)
        }

    _loop(xs.seq, Nil).map(_.mkString)
  }

}
