package

object PigLatin {
  val Vowels = "^([aeiou])(.*)".r
  val Consonants = "^(ch|thr|th|sch|qu|squ|[^aeiou])(.*)".r

  def ay(plain: String): String =
    plain match {
      case Vowels(startWith, xs) => plain  + "ay"
      case Consonants(startWith, xs) =>
        xs + startWith + "ay"
    }

  def translate(plain: String): String =
    plain.split("\\s")
     .map(ay)
     .mkString(" ")
}
