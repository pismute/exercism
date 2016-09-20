object ETL {
  def transform(old: Map[Int, Seq[String]]) =
    for {
      (k, v) <- old
      s <- v
    } yield (s.toLowerCase(), k)
}
