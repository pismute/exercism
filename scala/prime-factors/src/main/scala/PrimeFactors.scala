object PrimeFactors {
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

  def apply() = PrimeFactors
}
