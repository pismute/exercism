object Ocr {
  import OcrLetters._

  type Digit = (String, String, String, String)

  val Digits =
    Seq(Zero, One, Two, Three, Four, Five, Six, Seven, Eight, Nine)
      .toMap
        .withDefaultValue("?")

  def toDigit(xs: Seq[String]): Digit =
    (xs.head, xs.tail.head, xs.tail.tail.head, xs.tail.tail.tail.head)

  def toDigits(ocrLine: Seq[String]): Seq[Digit] = {
    val groupedOcr = ocrLine.map(_.grouped(3).toIndexedSeq).toIndexedSeq

    (for {
      x <- 0 until groupedOcr(0).length
      line <- groupedOcr
    } yield line(x))
      .grouped(4)
        .map(_.toSeq)
        .map(toDigit)
        .toSeq
  }

  def convertLine(ocrLine: Array[String]): String = {
    toDigits(ocrLine).map(Digits).mkString
  }

  def apply(ocr: String) = new Ocr(ocr)

  object OcrLetters {
    val Zero =
      ((" _ ",
        "| |",
        "|_|",
        "   "), "0")

    val One =
      (("   ",
        "  |",
        "  |",
        "   "), "1")

    val Two =
      ((" _ ",
        " _|",
        "|_ ",
        "   "), "2")

    val Three =
      ((" _ ",
        " _|",
        " _|",
        "   "), "3")

    val Four =
      (("   ",
        "|_|",
        "  |",
        "   "), "4")

    val Five =
      ((" _ ",
        "|_ ",
        " _|",
        "   "), "5")

    val Six =
      ((" _ ",
        "|_ ",
        "|_|",
        "   "), "6")

    val Seven =
      ((" _ ",
        "  |",
        "  |",
        "   "), "7")

    val Eight =
      ((" _ ",
        "|_|",
        "|_|",
        "   "), "8")

    val Nine =
      ((" _ ",
        "|_|",
        " _|",
        "   "), "9")
  }

}

class Ocr(val ocr: String) {
  import Ocr._

  def convert =
    ocr.split("\n")
      .grouped(4)
        .map(convertLine)
        .mkString(",")
}
