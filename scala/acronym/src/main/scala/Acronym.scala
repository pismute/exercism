object Acronym {
  def apply(phrase: String) = new Acronym(phrase)
}

class Acronym(phrase: String) {
  import Acronym._

  def capitalize(s: String) = s.head.toUpper + s.tail

  def abbreviate: String =
    phrase
      .takeWhile(_ != ':')
      .split("\\W+")
        .map(capitalize)
        .mkString
          .filter(_.isUpper)
}
