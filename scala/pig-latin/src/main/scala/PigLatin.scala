package

object PigLatin {
  val Consonants = "^(ch|thr|th|sch|qu|squ|[^aeiou])(.*)".r

  def ay(plain: String): String =
    plain match {
      case Consonants(startWith, xs) =>
        xs + startWith + "ay"
      case _ => plain + "ay"
    }

  def translate(plain: String): String =
    plain.split("\\s")
     .map(ay)
     .mkString(" ")
}
