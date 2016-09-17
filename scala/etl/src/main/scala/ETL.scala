object ETL {
  def transform(old: Map[Int, Seq[String]]) =
    old
      .mapValues(_.map(_.toLowerCase()))
      .flatMap{ case (k, v) => v.map((_, k)).toMap }
}
