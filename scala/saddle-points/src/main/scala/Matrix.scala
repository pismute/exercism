object Matrix {
  def apply[A : Ordering](matrix: Seq[Seq[A]]) = new Matrix(matrix)

}

class Matrix[A : Ordering](val matrix: Seq[Seq[A]]) {
  import Matrix._

  def chooseWithIndices(xs: Seq[A], f: Iterable[A]=> A ) = {
    val m : Map[A, Seq[Int]] =
      xs.zipWithIndex
        .groupBy(_._1)
          .mapValues(_.map(_._2))

    val choosed = f(m.keys)
    (choosed, m(choosed))
  }

  lazy val maxsInRow: Seq[(A, Seq[Int])] =
    matrix
      .map(chooseWithIndices(_, _.max))

  lazy val minsInColumn: Seq[(A, Seq[Int])] =
    matrix.transpose
      .map(chooseWithIndices(_, _.min))

  def saddlePoints: Set[(Int, Int)] = {
    for {
      (ca, cs) <- minsInColumn.toSet
      (ra, rs) <- maxsInRow
      if( ra == ca)
      c <- cs
      r <- rs
    } yield (c, r)
  }
}
