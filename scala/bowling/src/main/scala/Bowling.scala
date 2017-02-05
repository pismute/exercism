case class Bowling(rollings: Seq[Int] = Nil) {
  def roll(pins: Int): Bowling = Bowling(pins +: rollings)

  def traverse(f: Int => Either[Error, Int], xs: Seq[Int]): Either[Error, Seq[Int]] = {
    def _loop(xs: Seq[Int]): Either[Error, Seq[Int]] =
      if(xs.isEmpty) Right(Nil)
      else
        for {
          x <- f(xs.head)
          y <- _loop(xs.tail)
        } yield x +: y

    _loop(xs)
  }

  def validPins(pins: Int): Either[Error, Int] =
    if( pins >= 0 && pins <= 10 ) Right(pins)
    else Left(Error("invalid pins"))

  def frames(i: Int, xs: Seq[Int], acc: Int) : Either[Error, Int] =
    if( i == 0 ) tenth(xs).map(_ + acc)
    else
      xs match {
        case Seq(10, _*) =>
           frames(i-1, xs.drop(1), xs.take(3).sum + acc)
        case Seq(_1, _2, _*) if _1 + _2 > 10 =>
          Left(Error("two rolls over 10"))
        case Seq(_1, _2, _*) if _1 + _2 == 10 =>
          frames(i-1, xs.drop(2), xs.take(3).sum + acc)
        case Seq(_1, _2, _*) =>
          frames(i-1, xs.drop(2), xs.take(2).sum + acc)
        case _ =>
          Left(Error("imcomplete game"))
      }

  def tenth(xs: Seq[Int]) : Either[Error, Int] =
    xs match {
      case Seq(10, 10, _3) => Right(xs.sum)
      case Seq(10, _2, _3) if _2 + _3 > 10 =>
        Left(Error("two rolls over 10 in the last frame"))
      case Seq(10, _2, _3) => Right(xs.sum)
      case Seq(_1, _2, _*) if _1 + _2 > 10 =>
        Left(Error("two rolls over 10 in the last frame"))
      case Seq(_1, _2, _3) if _1 + _2 == 10 => Right(xs.sum)
      case Seq(_1, _2) if _1 + _2 == 10 =>
        Left(Error("bonus roll required if it is a spare in the last frame"))
      case Seq(_1, _2) => Right(xs.sum)
      case _ =>
        Left(Error("imcomplete game"))
    }

  def score(): Either[Error, Int] =
    for {
      xs <- traverse(validPins, rollings.reverse)
      n <- frames(9, xs, 0)
    } yield n
}

case class Error(errorText: String)
