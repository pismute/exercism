object Prime {
  def isPrime(n: Int) =
    !(2 to math.sqrt(n).toInt).exists(n % _ == 0)

  def nth(n: Int) =
    Stream.from(2)
      .filter(isPrime)
      .drop(n-1)
      .head
}
