object RomanDigit {
  type RomanDigit = String
  val I = "I"
  val V = "V"
  val X = "X"
  val L = "L"
  val C = "C"
  val D = "D"
  val M = "M"
  val empty = ""

  case class Roman(position: Int, x: RomanDigit, v: RomanDigit, i: RomanDigit)

  val digits =
    Stream(
      Roman(1000, empty, empty, M),
      Roman(100, M, D, C),
      Roman(10, C, L, X),
      Roman(1, X, V, I))

  implicit class IntHelper(nr: Int) {
    def toRomanNumber = {
      require(nr >= 0)
      require(nr <= 3000)

      digits.foldRight(Stream.empty[RomanDigit]) { (roman, acc) =>
        ((nr / roman.position) % 10) match {
          case 0 => acc
          case n if n >= 1 && n <= 3 => (roman.i * n) #:: acc
          case 4 => roman.i #:: roman.v #:: acc
          case 5 => roman.v #:: acc
          case n if n >= 6 && n <= 8 => roman.v #:: (roman.i * (n-5)) #:: acc
          case 9 => roman.i #:: roman.x #:: acc
        }
      }
    }
  }
}

object RomanNumeral {
  def apply(nr:Int) = new RomanNumeral(nr)
}

class RomanNumeral(nr: Int) {
  import RomanDigit._

  lazy val value = nr.toRomanNumber.mkString
}
