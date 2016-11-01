object SumOfMultiples {
  def multiples(nr:Int, given:Int) =
    Stream.from(1).map(_ * nr).takeWhile(_ < given)

  def sumOfMultiples(nrs: Seq[Int], given: Int) =
    nrs.toSet.flatMap(multiples(_:Int, given)).sum
}
