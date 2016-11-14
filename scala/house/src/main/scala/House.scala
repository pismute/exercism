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
      .stripMargin.split('\n')
        .foldRight(List(""))(_ +: _)

  def dropWith(xs: String, before: String) = xs.drop(xs.indexOf(before))

  def takeRhymes(n: Int) = {
    val rhymes = Rhymes.takeRight(n + 1)

    ("This is" ++ dropWith(rhymes.head, " the")) +: rhymes.tail
  }

  lazy val rhyme: String =
    (for(i <- 1 until Rhymes.size) yield takeRhymes(i))
      .map(_.mkString("\n"))
      .mkString("\n") + "\n"
}
