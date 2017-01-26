object Dna {
  val Transcription =
    Map('G' -> 'C', 'C' -> 'G', 'T' -> 'A', 'A' -> 'U')

  def toRna(xs: String) = traverse(Transcription.get, xs)

  def traverse(f: Char => Option[Char], xs: String): Option[String] = {
    def _loop(xs: Seq[Char]): Option[Seq[Char]] =
      if(xs.isEmpty) Option(Nil)
      else
        f(xs.head) match {
          case None => None
          case Some(x) => _loop(xs.tail).map(x +: _)
        }

    _loop(xs.seq).map(_.mkString)
  }

}
