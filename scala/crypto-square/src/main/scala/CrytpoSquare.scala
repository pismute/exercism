import scala.util.Try

object CryptoSquare {
  type PlainText = String
  type CipherText = String

  def transposeAll(xss: Seq[String], size: Int): Seq[Seq[Char]] =
    (for{
      i <- (0 until size)
      xs <- xss
    } yield Try(xs.charAt(i)).toOption)
      .grouped(xss.size)
        .map(_.filterNot(_ == None))
        .map(_.map(_.get))
        .toSeq

  implicit class PlainTextOps(text: PlainText) {
    def normalize: PlainText = text.filter(_.isLetterOrDigit).toLowerCase

    def squareSize: Int = math.sqrt(text.size).ceil.toInt

    def segments: List[PlainText] = {
      val normalized = text.normalize
      val squareSize = normalized.squareSize

      if(squareSize > 0) normalized.grouped(squareSize).toList else Nil
    }

    def normalizedCiphertext(separator: String): CipherText = {
      val segments = text.segments

      if(segments.isEmpty) ""
      else transposeAll(segments, segments.head.size)
        .map(_.mkString)
        .mkString(separator)
    }
  }

  def normalizePlaintext(text: PlainText) = text.normalize

  def squareSize(text: PlainText) = text.squareSize

  def plaintextSegments(text: PlainText) = text.segments

  def ciphertext(text: PlainText) = text.normalizedCiphertext("")

  def normalizedCiphertext(text: PlainText) = text.normalizedCiphertext(" ")

  def apply() = CryptoSquare
}
