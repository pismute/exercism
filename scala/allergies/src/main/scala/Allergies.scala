object Allergen extends Enumeration {
  type Allergen = Value
  val Eggs, Peanuts, Shellfish, Strawberries, Tomatoes, Chocolate, Pollen, Cats = Value
}

object Allergies2 {
  import Allergen._

  val allergensWithScore =
    Allergen.values.toList
      .zipWithIndex
      .map{ case (allergen, i) => (allergen, math.pow(2, i).toInt) }

  val allergensMap = allergensWithScore.toMap

  def isAllergicTo(allergen: Allergen, score: Int) =
    allergensMap.get(allergen).map(_ <= score).getOrElse(false)

  def allergies(score: Int) =
    allergensWithScore.foldRight((score, Seq.empty[Allergen])){
      case ((allergen, score), (seed, acc)) if score <= seed =>
        (seed % score, allergen +: acc)
      case (_, z) => z
    }._2

  def apply() = Allergies
}
