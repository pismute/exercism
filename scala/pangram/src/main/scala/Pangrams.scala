object Pangrams {
  def isPangram(s: String): Boolean =
    ('a' to 'z').forall(s.toLowerCase.toSet)
}
