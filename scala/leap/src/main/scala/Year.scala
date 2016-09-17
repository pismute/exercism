class Year(year: Int) {
  def isDivisibleBy(n: Int) = year % n == 0

  def isLeap =
    isDivisibleBy(400) || (!isDivisibleBy(100) && isDivisibleBy(4))
}

object Year {
  def apply(year: Int) = new Year(year)
}

