object Allergen {
  sealed trait Allergen {
    def score:Int
  }

  case object Eggs extends Allergen { val score = 1 }
  case object Peanuts extends Allergen { val score = 2 << 0 }
  case object Shellfish extends Allergen { val score = 2 << 1 }
  case object Strawberries extends Allergen { val score = 2 << 2 }
  case object Tomatoes extends Allergen { val score = 2 << 3 }
  case object Chocolate extends Allergen { val score = 2 << 4 }
  case object Pollen extends Allergen { val score = 2 << 5 }
  case object Cats extends Allergen { val score = 2 << 6 }

  val values =
    Seq(
      Eggs,
      Peanuts,
      Shellfish,
      Strawberries,
      Tomatoes,
      Chocolate,
      Pollen,
      Cats)
}

object Allergies {
  import Allergen._

  def isAllergicTo(allergen: Allergen, score: Int) =
    (allergen.score & score) == allergen.score

  def allergies(score: Int) =
    values.filter(isAllergicTo(_: Allergen, score))

  def apply() = Allergies
}
