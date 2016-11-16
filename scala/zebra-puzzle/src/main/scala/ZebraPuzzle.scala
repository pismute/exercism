object ZebraPuzzle {
  object Color extends Enumeration {
    type Color = Value
    val Red, Green, Ivory, Yellow, Blue = Value
  }

  object Drink extends Enumeration {
    type Drink = Value
    val Coffee, Tea, Milk, OrangeJuice, Water = Value
  }

  object Pet extends Enumeration {
    type Pet = Value
    val Dog, Snails, Fox, Horse, Zebra = Value
  }

  object Cigaret extends Enumeration {
    type Cigaret = Value
    val OldGold, Kools, Chesterfileds, LuckyStrike, Parliaments = Value
  }

  import Color._, Drink._, Pet._, Cigaret._

  sealed trait Resident
  case object Englishman extends Resident
  case object Spaniard extends Resident
  case object Ukrainian extends Resident
  case object Norwegian extends Resident
  case object Japanese extends Resident

  case class Solution(waterDrinker: Resident, zebraOwner: Resident)

  val Residents: Stream[Resident] = Stream(Norwegian, Englishman, Spaniard, Ukrainian, Japanese)

  def equalOrNot2[A, B](a1: A, a2: A, b1: B, b2: B) = (a1 == a2 && b1 == b2) || (a1 != a2 && b1 != b2)

  case class House(resident: Resident, color: Color, drink: Drink, pet: Pet, cigaret: Cigaret) {
    def isClue2 = equalOrNot2(resident, Englishman, color, Red)
    def isClue3 = equalOrNot2(resident, Spaniard, pet, Dog)
    def isClue4 = equalOrNot2(drink, Coffee, color, Green)
    def isClue5 = equalOrNot2(resident, Ukrainian, drink, Tea)
    def isClue7 = equalOrNot2(cigaret, OldGold, pet, Snails)
    def isClue8 = equalOrNot2(cigaret, Kools, color, Yellow)
    def isClue13 = equalOrNot2(cigaret, LuckyStrike, drink, OrangeJuice)
    def isClue14 = equalOrNot2(resident, Japanese, cigaret, Parliaments)

    def isClue =
      isClue2 && isClue3 && isClue4 && isClue5 &&
      isClue7 && isClue8 && isClue13 && isClue14

    def isOrthogonal(that: House) =
      resident == that.resident || color == that.color || drink == that.drink || pet == that.pet || cigaret == that.cigaret
  }

  type Town = Stream[House] // 1

  def isExclusiveIn(house: House, xs: List[House]) = !xs.exists(_.isOrthogonal(house))

  def isClue6(town: Town) =
    town.zip(town.tail).exists{ case (l, r) =>
      l.color == Ivory && r.color == Green
    }

  def isClue11(town: Town) =
    town.zip(town.tail).exists{ case (l, r) =>
      (l.cigaret == Chesterfileds && r.pet == Fox) ||
        (l.pet == Fox && r.cigaret == Chesterfileds)
    }

  def isClue12(town: Town) =
    town.zip(town.tail).exists{ case (l, r) =>
      (l.cigaret == Kools && r.pet == Horse) ||
        (l.pet == Horse && r.cigaret == Kools)
    }

  def isOrderClue(town: Town) =
    isClue6(town) && isClue11(town) && isClue12(town)

  lazy val allHouses =
    (for{
      resident <- Residents
      color <- Color.values
      drink <- Drink.values
      pet <- Pet.values
      cigaret <- Cigaret.values
    } yield House(resident, color, drink, pet, cigaret))
      .filter(_.isClue)

  lazy val allTown: Stream[Town] =
    (for {
      a <- allHouses if a.resident == Norwegian // 10
      b <- allHouses if b.color == Blue && isExclusiveIn(b, List(a)) // 15
      c <- allHouses if c.drink == Milk && isExclusiveIn(c, List(a, b)) // 9
      d <- allHouses if isExclusiveIn(d, List(a, b, c))
      e <- allHouses if isExclusiveIn(e, List(a, b, c, d))
    } yield Stream(a,b,c,d,e))
      .filter(isOrderClue)

  lazy val solve: Solution =
    (for {
      waterHouse <- allTown.head.find(_.drink == Water)
      zebraHouse <- allTown.head.find(_.pet == Zebra)
    } yield Solution(waterHouse.resident, zebraHouse.resident))
      .get
}
