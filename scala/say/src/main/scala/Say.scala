import scala.annotation.tailrec

object Say {
  val LowerBound = 0L
  val UpperBound = 999999999999L

  val Numbers = Map(
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
    90L -> "ninety")

  val ScaleWords = Seq(Option("billion"), Option("million"), Option("thousand"), None)

  def say00(nr: Long): Option[String] =
    if(nr == 0) None
    else {
      Numbers.get(nr)
        .orElse {
          val n = nr%10
          val n0 = nr - n
          Option((Numbers.get(n0) ++ Numbers.get(n)).mkString("-"))
        }
    }

  def say000(n: Long): Option[String] = {
    require(n < 1000)

    say00(n / 100).map(_ + " hundred") ++ say00(n % 100) match {
      case xs if xs.isEmpty => None
      case xs => Option(xs.mkString(" "))
    }
  }

  def validNumber(n: Long): Option[Long] =
    if( LowerBound <= n && n <= UpperBound ) Option(n)
    else None

  def combine(chunkAndScales: Seq[(Long, Option[String])]) =
    (for{
      (chunk, scale) <- chunkAndScales
      combined <- say000(chunk).map(c => scale.map(c + " " + _).getOrElse(c))
    } yield combined)
      .mkString(" ")

  def inEnglish(n: Long): Option[String] =
    if(n == 0) Option("zero")
    else
      for {
        nr <- validNumber(n)
        chunks = splitByBase(1000, nr)
        scales = ScaleWords.drop(ScaleWords.size - chunks.size)
      } yield combine(chunks zip scales)

  def splitByBase(base: Long, n: Long): Seq[Long] =
    unfoldLeft(n)(z=>
      if(z <= 0) None
      else Option((z/base, z%base)))

  def unfoldLeft[Z, A](seed: Z)(f: Z => Option[(Z, A)]): Seq[A] = {
    @tailrec
    def loop(z: Z, acc: Seq[A]): Seq[A] =
      f(z) match {
        case None => acc
        case Some((zz, a)) => loop(zz, a +: acc)
      }

    loop(seed, Nil)
  }
}
