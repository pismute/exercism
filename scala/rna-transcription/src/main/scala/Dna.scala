object Dna {
  val Transcription =
    Map('G' -> 'C', 'C' -> 'G', 'T' -> 'A', 'A' -> 'U')

  def apply() = new Dna
}

class Dna {
  import Dna._

  def toRna(s: String) = s.map(Transcription)
}
