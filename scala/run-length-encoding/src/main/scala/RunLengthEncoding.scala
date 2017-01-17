object RunLengthEncoding {
  def encode(str: String): String = {
    def _groupBy(f: (Char, Char) => Boolean, xs: String): Seq[String] =
      if(xs.isEmpty) Nil
      else {
        val (ys, zs) = xs.span(f(_, xs.head))
        ys +: _groupBy(f, zs)
      }

    def _grouped = _groupBy(_ == _, _: String)

    def _encode(xs: String) =
      xs.length match {
        case 1 => xs
        case n => n.toString + xs.head
      }

    _grouped(str)
      .map(_encode)
      .mkString
  }

  def decode(str: String): String = {
    def _parseInt(xs: String) =
      if(xs.isEmpty) 1
      else xs.toInt

    if(str.isEmpty) ""
    else {
      val (xs, ys) = str.span(_.isDigit)
      ys.head.toString * _parseInt(xs) + decode(ys.tail)
    }
  }
}
