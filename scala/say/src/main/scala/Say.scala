import scala.collection.immutable.SortedMap

object Say {
  val LowerBound = 0L
  val UpperBound = 999999999999L

  val Numbers = Seq(
    1L -> "one",
    2L -> "two",
    3L -> "three",
    4L -> "four",
    5L -> "five",
    6L -> "six",
    7L -> "seven",
    8L -> "eight",
    9L -> "nine",
    10L -> "ten",
    11L -> "eleven",
    12L -> "twelve",
    13L -> "thirteen",
    14L -> "fourteen",
    15L -> "fifteen",
    16L -> "sixteen",
    17L -> "seventeen",
    18L -> "eighteen",
    19L -> "nineteen",
    20L -> "twenty",
    30L -> "thirty",
    40L -> "forty",
    50L -> "fifty",
    60L -> "sixty",
    70L -> "seventy",
    80L -> "eighty",
    90L -> "ninety",
    100L -> "hundred",
    1000L -> "thousand",
    1000000L -> "million",
    1000000000L -> "billion").reverse

  def sayRight(nr: Long, nrs: Seq[(Long, String)], acc: Seq[String]): Seq[String] =
    if(nr == 0) acc
    else nrs match {
      case Seq() => acc
      case Seq((x, _), xs @ _*) if nr < x =>
        sayRight(nr, xs, acc)
      case Seq((x, expr), xs @ _*) if nr < 100 =>
        sayRight(if(nr < 2*x) 0 else nr / x, xs,
          (expr +: sayRight(nr % x, xs, Seq.empty)).mkString("-") +: acc)
      case Seq((x, expr), xs @ _*) =>
        sayRight(nr / x, xs,
          expr +: sayRight(nr % x, xs, acc))
    }

  def validNumber(n: Long): Option[Long] =
    if( LowerBound <= n && n <= UpperBound ) Option(n)
    else None

  def inEnglish(n: Long): Option[String] =
    if(n == 0) Option("zero")
    else
      for {
        nr <- validNumber(n)
      } yield sayRight(nr, Numbers, Seq.empty).mkString(" ")
}
