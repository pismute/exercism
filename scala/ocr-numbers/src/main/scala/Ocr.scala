object Ocr {
  type Digit = (String, String, String, String)

  val Digits: Map[Digit, String] =
    toDigits(Seq(
    " _     _  _     _  _  _  _  _ ",
    "| |  | _| _||_||_ |_   ||_||_|",
    "|_|  ||_  _|  | _||_|  ||_| _|",
    "                              "))
      .zipWithIndex
      .toMap
        .mapValues(_.toString)
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
}

class Ocr(val ocr: String) {
  import Ocr._

  def convert =
    ocr.split("\n")
      .grouped(4)
        .map(convertLine)
        .mkString(",")
}
