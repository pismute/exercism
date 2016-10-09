import scala.annotation.tailrec

object RomanNumeral {
  type ArabicNumber = Int
  type RomanNumber = String

  val arabicToRomans = Seq(
    1000 -> "M",
    900 -> "CM",
    500 -> "D",
    400 -> "CD",
    100 -> "C",
    90 -> "XC",
    50 -> "L",
    40 -> "XL",
    10 -> "X",
    9 -> "IX",
    5 -> "V",
    4 -> "IV",
    1 -> "I"
  )

  def unfoldRight[A,B](seed: B)(f: B => Option[(A,B)]): Seq[A] =
    f(seed) match {
    case None => Seq()
    case Some((a,b)) => a +: unfoldRight(b)(f)
  }

  implicit class ArabicNumberHelper(arabicNumber: ArabicNumber) {
    def toRomanNumber: RomanNumber = {
      require( arabicNumber >= 0 )
      require( arabicNumber <= 3000 )

      unfoldRight(arabicNumber){ i =>
        arabicToRomans.find(_._1 <= i).map {
          case (arabic, roman) => (roman, i - arabic)
        }
      }.mkString
    }
  }

  def apply(nr:Int) = new RomanNumeral(nr)
}

import RomanNumeral._

class RomanNumeral(nr: ArabicNumber) {

  lazy val value = nr.toRomanNumber
}
