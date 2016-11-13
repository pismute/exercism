object Matrix {
  def apply(given: String) = new Matrix(given)
}


class Matrix(val given: String) {
  lazy val rows =
    given.split('\n').toIndexedSeq
      .map(_.split(' ').map(_.toInt).toIndexedSeq)

  lazy val cols = rows.transpose

  override def equals(that: Any): Boolean =
    that.isInstanceOf[Matrix] &&  {
      val thatGiven = that.asInstanceOf[Matrix].given
      given.equals(thatGiven)
    }

  override def hashCode(): Int = given.hashCode()
}
