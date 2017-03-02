object NumberType extends Enumeration {
  type NumberType = Value
  val Deficient, Perfect, Abundant = Value
}

object PerfectNumbers {
  import NumberType._

  def sumFactorsOf(n: Int) : Int =
    (1 to n/2)
      .filter( n % _ == 0 )
      .sum

  def classify(n: Int) : NumberType =
    sumFactorsOf(n) match {
      case x if x == n => Perfect
      case x if x > n => Abundant
      case x if x < n => Deficient
    }

}
