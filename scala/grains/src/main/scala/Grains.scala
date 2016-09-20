object Grains{
  val Two = BigInt(2)

  def square(p: Int) = Two.pow(p-1)

  lazy val total = (1 until 65).map(square).sum
}
