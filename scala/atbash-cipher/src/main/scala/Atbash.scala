object Atbash {
  val Digits = '0' to '9'
  val Plain = 'a' to 'z'
  val Cipher = Plain.reverse

  val Dic = (Plain zip Cipher).toMap ++ (Digits zip Digits).toMap

  def encode(string: String) =
    string.toLowerCase
      .collect(Dic)
      .grouped(5)
        .map(_.mkString)
        .mkString(" ")

  def apply() = Atbash
}
