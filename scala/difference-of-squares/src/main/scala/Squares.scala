object Squares {
  val One = BigInt(1)

  def squareOfSums =
    (One to (_:BigInt)) andThen
      (_.sum) andThen
      (_.pow(2))

  def sumOfSquares =
    (One to (_:BigInt)) andThen
      (_.map(_.pow(2))) andThen
      (_.sum)

  def difference(i: BigInt) =
    squareOfSums(i) - sumOfSquares(i)

  def apply() = Squares
}
