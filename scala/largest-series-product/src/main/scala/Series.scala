package

object Series {
  def validInput(n: Int, given: String): Option[String] = if(n <= given.length()) Some(given) else None

  def largestProduct(n: Int, given: String): Option[Int] =
    if(n==0) Some(1)
    else
      for {
        g <- validInput(n, given)
        largest =
          g.map(_-'0')
            .sliding(n)
            .map(_.product)
            .max
      } yield largest
}
