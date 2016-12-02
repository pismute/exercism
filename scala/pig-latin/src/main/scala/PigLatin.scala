package

object PigLatin {
  val Vowels = "^([aeiou])(.*)".r
  val Consonants = "^(ch|thr|th|sch|qu|squ)(.*)".r

  def ay(plain: String): String =
    plain match {
      case Vowels(startWith, _) => plain + "ay"
      case Consonants(startWith, xs) => xs + startWith + "ay"
      case _ => plain.tail + plain.head + "ay"
    }

  def translate(plain: String): String =
    plain.split("\\s")
     .map(ay)
     .mkString(" ")
}
