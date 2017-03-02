object NumberType extends Enumeration {
  type NumberType = Value
  val Deficient, Perfect, Abundant = Value
}

object PerfectNumbers {
  import NumberType._

  def sumFactorsOf(n: Int) : Int =
    (1 to math.sqrt(n).toInt)
      .filter( n % _ == 0 )
      .map( x => x + n/x)
      .sum / 2

  def classify(n: Int) : NumberType =
    sumFactorsOf(n) match {
      case x if x == n => Perfect
      case x if x > n => Abundant
      case x if x < n => Deficient
    }

}
