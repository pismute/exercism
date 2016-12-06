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
    90L -> "ninety",
    1000L -> "thousand",
    1000000L -> "million",
    1000000000L -> "billion")

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

  def sayRight(nr: Long): Seq[String] =
    if(nr == 0) Seq.empty
    else {
      val len = math.log10(nr).toLong
      val scale = if(len >= 3) math.pow(1000, (len/3)).toLong else 0

      val says: Option[String] =
        for{
          c <- say000(if(scale == 0) nr else nr / scale)
          s = Numbers.get(scale)
        } yield s.map(ss => s"$c $ss").getOrElse(c)

      val acc = sayRight(if(scale == 0) 0 else nr % scale)

      says.map(_ +: acc).getOrElse(acc)
    }

  def validNumber(n: Long): Option[Long] =
    if( LowerBound <= n && n <= UpperBound ) Option(n)
    else None

  def inEnglish(n: Long): Option[String] =
    if(n == 0) Option("zero")
    else
      for {
        nr <- validNumber(n)
      } yield sayRight(nr).mkString(" ")
}
