object Raindrops {
  val pliaong = Map(3 -> "Pling", 5 -> "Plang", 7 -> "Plong")

  def convert(nr:Long) = {
    pliaong.foldRight(Seq.empty[String]){
      case ((k, v), acc) if nr%k == 0 => v +: acc
      case (_, acc) => acc
    } match {
      case xs if xs.isEmpty => nr.toString
      case xs => xs.mkString
    }
  }

  def apply() = Raindrops
}
