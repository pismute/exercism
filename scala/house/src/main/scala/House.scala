package

object House {
  val Rhymes = """This is the horse and the hound and the horn
    |that belonged to the farmer sowing his corn
    |that kept the rooster that crowed in the morn
    |that woke the priest all shaven and shorn
    |that married the man all tattered and torn
    |that kissed the maiden all forlorn
    |that milked the cow with the crumpled horn
    |that tossed the dog
    |that worried the cat
    |that killed the rat
    |that ate the malt
    |that lay in the house that Jack built."""
      .stripMargin.split('\n').toList ++ List("")

  val RhymesSize = Rhymes.size

  def dropBefore_The(xs: String) = xs.drop(xs.indexOf(" the"))

  def takeRhymes(n: Int) = {
    val rhymes = Rhymes.drop(RhymesSize-1 - n)

    ("This is" ++ dropBefore_The(rhymes.head)) +: rhymes.tail
  }

  lazy val rhyme: String =
    (for(i <- 1 until RhymesSize) yield takeRhymes(i))
      .map(_.mkString("\n"))
      .mkString("\n") + "\n"
}
