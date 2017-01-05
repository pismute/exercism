object Acronym {
  def apply(phrase: String) = new Acronym(phrase)
}

class Acronym(phrase: String) {
  import Acronym._

  def abbreviate: String =
    phrase
      .takeWhile(_ != ':')
      .split("\\W+")
        .map(_.capitalize)
        .mkString
          .filter(_.isUpper)
}
