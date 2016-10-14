object Allergen {
  sealed trait Allergen {
    def score:Int
  }

  case object Eggs extends Allergen { val score = math.pow(2, 0).toInt }
  case object Peanuts extends Allergen { val score = math.pow(2, 1).toInt }
  case object Shellfish extends Allergen { val score = math.pow(2, 2).toInt }
  case object Strawberries extends Allergen { val score = math.pow(2, 3).toInt }
  case object Tomatoes extends Allergen { val score = math.pow(2, 4).toInt }
  case object Chocolate extends Allergen { val score = math.pow(2, 5).toInt }
  case object Pollen extends Allergen { val score = math.pow(2, 6).toInt }
  case object Cats extends Allergen { val score = math.pow(2, 7).toInt }

  val values = Seq(Eggs, Peanuts, Shellfish, Strawberries, Tomatoes, Chocolate, Pollen, Cats)
}

object Allergies {
  import Allergen._

  def isAllergicTo(allergen: Allergen, score: Int) =
    values.find(_ == allergen).forall(_.score <= score)

  def allergies(score: Int) =
    values.foldRight((score, Seq.empty[Allergen])){
      case (allergen, (seed, acc)) if allergen.score <= seed =>
        (seed % allergen.score, allergen +: acc)
      case (_, z) => z
    }._2

  def apply() = Allergies
}
