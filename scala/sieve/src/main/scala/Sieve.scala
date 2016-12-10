object Sieve {
  def sieve(stream: Stream[Int]): Stream[Int] =
    stream.head #:: sieve(stream.tail.filter(_ % stream.head != 0))

  def primesUpTo(limit: Int) = sieve(Stream.from(2)).takeWhile(_ <= limit)
}
