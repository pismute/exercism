class Anagram(s: String) {
  val lower = s.toLowerCase
  val word = lower.sorted

  def matches(dic: Seq[String]) =
    for{
      w <- dic
      l = w.toLowerCase
      if lower != l
      if word == l.sorted
    } yield w
}

