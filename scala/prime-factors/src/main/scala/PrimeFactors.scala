object PrimeFactors {
  def primeFactors(nr:Long) = {
    def _primeFactors(nr: Long, divisor: Long = 2, acc: List[Long] = Nil): List[Long] = {
      if(nr < divisor) acc
      else (nr%divisor) match {
        case 0 => _primeFactors(nr/divisor, divisor, divisor :: acc)
        case _ => _primeFactors(nr, divisor+1, acc)
      }
    }

    _primeFactors(nr).reverse
  }

  def apply() = PrimeFactors
}
