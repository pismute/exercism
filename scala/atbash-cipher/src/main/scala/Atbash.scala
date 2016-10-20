object Atbash {
  val Digits = '0' to '9'
  val Plain = 'a' to 'z'
  val Cipher = Plain.reverse

  val Dic = (Plain zip Cipher).toMap.withDefault(identity)

  def encode(string: String) =
    string.toLowerCase
      .filter(_.isLetterOrDigit)
      .map(Dic)
      .grouped(5)
        .map(_.mkString)
        .mkString(" ")

  def apply() = Atbash
}
