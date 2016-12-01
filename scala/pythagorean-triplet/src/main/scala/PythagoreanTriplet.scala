package

object PythagoreanTriplet {
  def isPythagorean(triplet: (Int, Int, Int)) = {
    val (a, b, c) = triplet
    val Seq(d, e, f) = Seq(a, b, c).sorted
    d*d + e*e == f*f
  }

  def pythagoreanTriplets(start:Int, limit:Int) =
    for{
      a <- (start to limit).toSeq
      b <- a to limit
      c <- b to limit
      triplet = (a, b, c)
      if(isPythagorean(triplet))
    } yield triplet
}
