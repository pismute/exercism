object Series {
  def slices(n: Int, digits: String) : Seq[Seq[Int]] =
    digits.map(_ - '0').sliding(n).toList
}
