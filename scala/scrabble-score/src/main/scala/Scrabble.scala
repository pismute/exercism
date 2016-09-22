object Scrabble {
  val LetterValues =
    Map("AEIOULNRST" -> 1,
      "DG" -> 2,
      "BCMP" -> 3,
      "FHVWY" -> 4,
      "K" -> 5,
      "JX" -> 8,
      "QZ" -> 10)

  val ScoreLetter =
    for{
      (word:String, value:Int) <- LetterValues
      letter <- word
      c <- List(letter, letter.toLower)
    } yield (c, value)
}

class Scrabble {
  import Scrabble._

  val scoreLetter = ScoreLetter

  def scoreWord(word:String) = word.map(scoreLetter).sum
}
