object Plant extends Enumeration {
  type Plant = Value
  val Radishes, Clover, Grass, Violets = Value
}

class Garden(val students: Seq[String], val sillString:String) {
  import Plant._
  import Garden._

  lazy val indexByStudent =
    students.sorted
      .zipWithIndex
      .groupBy(_._1)
        .mapValues(_.head._2 + 1)

  lazy val sills = sillString.split("\n").toList

  def plantsByIndex(i: Int, width: Int): Seq[Plant] =
    sills
      .map(_.slice(i-width, i))
      .flatten
      .map(Plants)

  def getPlants(student: String): Seq[Plant] =
    indexByStudent.get(student)
      .map(_*2)
      .map(plantsByIndex(_:Int, 2))
      .getOrElse(Nil)
}

object Garden {
  val DefaultStudents =
    Seq("Alice", "Bob", "Charlie", "David", "Eve", "Fred", "Ginny", "Harriet", "Ileana", "Joseph", "Kincaid", "Larry")

  val Plants =
    Plant.values
      .groupBy(_.toString.charAt(0))
      .mapValues(_.head)

  def defaultGarden(sill: String) = Garden(DefaultStudents, sill)

  def apply(students: Seq[String], sill: String) = new Garden(students, sill)

}
