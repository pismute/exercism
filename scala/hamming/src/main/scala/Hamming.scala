object Hamming {
  def compute(s1: String, s2:String): Int = {
    require(s1.size == s2.size)

    s1.zip(s2)
      .count { case (l, r) => l != r }
  }
}
