class PhoneNumber(s: String) {
  val PhoneNumberPattern = "1?(\\d{3})(\\d{3})(\\d{4})".r

  lazy val (areaCode, code, num) =
    s.filter(_.isDigit) match {
      case PhoneNumberPattern(areaCode, code, num) => (areaCode, code, num)
      case _ => ("000", "000", "0000")
    }

  lazy val number = areaCode + code + num

  override def toString = s"($areaCode) $code-$num"
}
