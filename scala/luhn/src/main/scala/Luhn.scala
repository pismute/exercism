import scala.annotation.tailrec

object Luhn {
  def unfoldLeft[A, B](seed: A)(f: (A) => Option[(A, B)]): Seq[B] = {
    @tailrec
    def loop(seed: A)(acc: Seq[B]): Seq[B] =
      f(seed) match {
        case None => acc
        case Some((a, b)) => loop(a)(b +: acc)
      }

    loop(seed)(Nil)
  }

  def doubleLeft(digits: Seq[Long]) =
    for {
      (digit, i) <- digits.zipWithIndex
      doub = if(i%2 == 0) digit*2 else digit
    } yield if(doub>9) (doub-9) else doub

  def double(digits: Seq[Long], left: Boolean) =
    if(left) doubleLeft(digits) else digits.head +: doubleLeft(digits.tail)

  def apply(value: Long) = new Luhn(value)
}

class Luhn(val value: Long) {
  import Luhn._

  lazy val checkDigit = value % 10

  lazy val digits = unfoldLeft(value)(v=> if(v > 0) Some((v/10, v%10)) else None)
  lazy val digitsSize = digits.size

  lazy val addends: Seq[Long] = double(digits, digits.size % 2 == 0)

  lazy val checksum = addends.sum % 10

  lazy val isValid = checksum == 0

  lazy val create = {
    val uncheckDigit = (double(digits, digits.size%2 == 1).sum) % 10
    val checkDigit = (10 - uncheckDigit) % 10

    (digits :+ checkDigit).reduce((acc, d) => acc*10 + d)
  }
}
