object CryptoSquare {
  type PlainText = String

  implicit class PlainTextOps(text: PlainText) {
    def normalize = text.filter(_.isLetterOrDigit).toLowerCase
    def squareSize = math.sqrt(text.size).ceil.toInt
    def segments = {
      val normalized = text.normalize
      val squareSize = normalized.squareSize

      if(squareSize > 0) normalized.grouped(squareSize).toList else Nil
    }

    def normalizedCiphertext(separator: String) = {
      val segments = text.segments
      val size = segments.size

      def rowToColOfSquare(iFrom: Int, iUntil:Int, jFrom: Int, jUntil: Int) =
        (for {
          i <- iFrom until iUntil
          j <- jFrom until jUntil
        } yield segments(j)(i))
          .grouped(jUntil)
          .map(_.mkString)
          .toList

      if(size < 2) ""
      else {
        val headSize = segments.head.size
        val lastSize = segments.last.size

        val init = rowToColOfSquare(0, lastSize, 0, size)
        val tail = rowToColOfSquare(lastSize, headSize, 0, size-1)

        (init ++ tail).mkString(separator)
      }
    }
  }

  def normalizePlaintext(text: PlainText) = text.normalize

  def squareSize(text: PlainText) = text.squareSize

  def plaintextSegments(text: PlainText) = text.segments

  def ciphertext(text: PlainText) = text.normalizedCiphertext("")

  def normalizedCiphertext(text: PlainText) = text.normalizedCiphertext(" ")

  def apply() = CryptoSquare
}
