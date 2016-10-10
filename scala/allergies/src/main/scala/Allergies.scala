object Allergen extends Enumeration {
  type Allergen = Value
  val Eggs, Peanuts, Shellfish, Strawberries, Tomatoes, Chocolate, Pollen, Cats = Value
}

object Allergies {
  import Allergen._

  val allergensWithScore =
    Allergen.values.toList
      .zipWithIndex
      .map{ case (allergen, i) => (allergen, math.pow(2, i).toInt) }
      .reverse

  val allergensMap = allergensWithScore.toMap

  def isAllergicTo(allergen: Allergen, score: Int) =
    allergensMap.get(allergen).map(_ <= score).getOrElse(false)

  def unfoldRight[A, B](seed: B)(f: B => Option[(A,B)]): Seq[A] =
    f(seed) match {
      case None => Seq()
      case Some((a,b)) => a +: unfoldRight(b)(f)
    }

  def findAllergenWithScore(score: Int) =
      allergensWithScore
        .find(_._2 <= score)
        .map{ case (a, b) => (a, score%b) }

  def allergies(score: Int) = unfoldRight(score)(findAllergenWithScore).reverse

  def apply() = Allergies
}
