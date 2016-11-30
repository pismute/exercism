package

object PigLatin {
  val Vowels = Seq("a", "e", "i", "o", "u")
  val Consonants = Seq("ch", "thr", "th", "sch", "qu", "squ")

  def ayVowel(plain: String): Option[String] =
    Vowels.find(plain.startsWith)
      .map(_=> plain + "ay")

  def ayConsonant(plain: String): Option[String] =
    Consonants.find(plain.startsWith)
      .map(cs=> plain.drop(cs.length) + cs + "ay")

  def ay(plain: String): String =
    ayVowel(plain)
      .orElse(ayConsonant(plain))
      .getOrElse(plain.tail + plain.head + "ay")

  def translate(plain: String): String =
    plain.split("\\s")
     .map(ay)
     .mkString(" ")
}
