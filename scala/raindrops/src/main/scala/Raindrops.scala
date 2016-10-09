object Raindrops {
  def unfoldRight[A,B](seed: B)(f: B => Option[(A,B)]): Seq[A] =
    f(seed) match {
      case None => Seq()
      case Some((a,b)) => a +: unfoldRight(b)(f)
    }

  val Divisors = Stream.from(2)

  def primeFactors(nr:Long) =
    unfoldRight(nr){ seed =>
      if(seed == 1L) None
      else Divisors
        .find(seed%_ == 0)
        .map(divisor => (divisor, seed/divisor))
  }

  val pliaong = Map(3 -> "Pling", 5 -> "Plang", 7 -> "Plong")

  def convert(nr:Long) =
    primeFactors(nr)
      .distinct
      .collect(pliaong) match {
      case xs if xs.isEmpty => nr.toString
      case xs => xs.mkString
    }

  def apply() = Raindrops
}
