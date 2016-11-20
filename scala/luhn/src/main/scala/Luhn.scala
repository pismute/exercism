object Luhn {
  def apply(value: Long) = new Luhn(value)
}

class Luhn(val value: Long) {
  lazy val checkDigit = value % 10

  lazy val addends =
    Seq.iterate(value, math.log10(value).toInt + 1)(_ / 10)
      .map(_ % 10)
      .zipWithIndex
      .map{ case (d, i) => if(i%2==1) d*2 else d }
      .map(d=> if(d > 9) d - 9 else d)
      .reverse

  lazy val checksum = addends.sum % 10

  lazy val isValid = checksum == 0

  lazy val create =
    (0 to 9)
      .map(value*10 + _)
      .map(Luhn.apply)
      .find(_.isValid)
        .get
          .value
}
