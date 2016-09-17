import scala.collection.immutable.SortedMap

class School {
  private var _db: Map[Int, Seq[String]] =
    SortedMap.empty[Int, Seq[String]]
      .withDefaultValue(Nil)

  def db = _db

  def add(name:String, grade:Int) = {
    _db += grade -> (_db(grade) :+ name)
  }

  def grade = _db

  def sorted = _db.mapValues(_.sorted)
}
